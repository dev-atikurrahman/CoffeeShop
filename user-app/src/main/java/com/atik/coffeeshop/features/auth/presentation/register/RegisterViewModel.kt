package com.atik.coffeeshop.features.auth.presentation.register

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.atik.coffeeshop.core.network.ApiException
import com.atik.coffeeshop.features.auth.domain.repository.AuthRepository
import com.atik.coffeeshop.shared.data.preferences.UserPreferences
import com.atik.coffeeshop.shared.domain.use_case.ValidateEmail
import com.atik.coffeeshop.shared.domain.use_case.ValidateName
import com.atik.coffeeshop.shared.domain.use_case.ValidateRegistrationPassword
import com.atik.coffeeshop.shared.domain.use_case.ValidateRepeatedPassword
import com.atik.coffeeshop.shared.domain.use_case.ValidateTerms
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class RegisterViewModel(
    private val authRepository: AuthRepository,
    private val userPreferences: UserPreferences,
    private val validateName: ValidateName = ValidateName(),
    private val validateEmail: ValidateEmail = ValidateEmail(),
    private val validatePassword: ValidateRegistrationPassword = ValidateRegistrationPassword(),
    private val validateRepeatedPassword: ValidateRepeatedPassword = ValidateRepeatedPassword(),
    private val validateTerms: ValidateTerms = ValidateTerms()
) : ViewModel() {
    companion object {
        const val AUTH_TAG = "AUTH_DEBUG"
    }

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()
    var state by mutableStateOf(RegistrationFormState())
        private set

    private val validationEventChannel = Channel<ValidationEvent>(Channel.BUFFERED)
    val validationEvents = validationEventChannel.receiveAsFlow()

    fun onNameChanged(name: String) {
        state = state.copy(name = name, nameError = null, generalError = null)
    }

    fun onEmailChanged(email: String) {
        state = state.copy(email = email, emailError = null)
    }

    fun onPasswordChanged(password: String) {
        state = state.copy(password = password, passwordError = null)
    }

    fun onRepeatedPasswordChanged(repeatedPassword: String) {
        state = state.copy(repeatedPassword = repeatedPassword, repeatedPasswordError = null)
    }

    fun onTermsAcceptedChanged(accepted: Boolean) {
        state = state.copy(acceptedTerms = accepted, termsError = null)
    }

    fun onRegisterClick() {
        if (_isLoading.value) return

        val nameResult = validateName.execute(state.name)
        val emailResult = validateEmail.execute(state.email)
        val passwordResult = validatePassword.execute(state.password)
        val repeatedPasswordResult = validateRepeatedPassword.execute(
            state.password,
            state.repeatedPassword
        )
        val termsResult = validateTerms.execute(state.acceptedTerms)

        val hasError = listOf(
            nameResult,
            emailResult,
            passwordResult,
            repeatedPasswordResult,
            termsResult
        ).any { !it.successful }

        state = state.copy(
            nameError = nameResult.errorMessage,
            emailError = emailResult.errorMessage,
            passwordError = passwordResult.errorMessage,
            repeatedPasswordError = repeatedPasswordResult.errorMessage,
            termsError = termsResult.errorMessage,
            generalError = null
        )

        if (hasError) return

        viewModelScope.launch {
            _isLoading.value = true
            try {
                authRepository.register(state.name, state.email, state.password).fold(
                    onSuccess = { session ->
                        userPreferences.saveSession(session.token)
                        validationEventChannel.send(ValidationEvent.Success)
                    },
                    onFailure = { e -> handleError(e) }
                )
            } finally {
                _isLoading.value = false
            }

        }
    }

    private suspend fun handleError(e: Throwable) {
        val api = e as? ApiException
        val fields = api?.fieldErrors.orEmpty()
        val message = api?.message ?: "Something went wrong"
        val emailTaken = api?.statusCode == 409

        state = state.copy(
            nameError = fields["name"]?.firstOrNull(),
            emailError = fields["email"]?.firstOrNull() ?: message.takeIf { emailTaken },
            passwordError = fields["password"]?.firstOrNull(),
            //generalError = message.takeIf { fields.isEmpty() && !emailTaken }
        )

        if (fields.isEmpty() && !emailTaken) {
            validationEventChannel.send(ValidationEvent.Error(message))
        }
    }


}

sealed class ValidationEvent {
    data object Success : ValidationEvent()
    data class Error(val message: String) : ValidationEvent()
}
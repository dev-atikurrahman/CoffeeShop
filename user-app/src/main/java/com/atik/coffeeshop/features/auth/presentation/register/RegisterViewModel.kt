package com.atik.coffeeshop.features.auth.presentation.register

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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

    private val validationEventChannel = Channel<ValidationEvent>()
    val validationEvents = validationEventChannel.receiveAsFlow()

    fun onNameChanged(name: String) {
        state = state.copy(name = name)
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
        val nameResult = validateName.execute(state.name)
        val emailResult = validateEmail.execute(state.email)
        val passwordResult = validatePassword.execute(state.password)
        val repeatedPasswordResult = validateRepeatedPassword.execute(
            state.password,
            state.repeatedPassword
        )
        val termsResult = validateTerms.execute(state.acceptedTerms)

        val hasError = listOf(
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
            termsError = termsResult.errorMessage
        )

        if (hasError) return

        viewModelScope.launch {
            _isLoading.value = true

            // TODO: real registration API call এখানে বসবে (repository যখন তৈরি হবে)
            // আপাতত onboarding/session flag persist করা হচ্ছে, LoginViewModel-এর মতোই
            userPreferences.setOnboardingCompleted(true)
            userPreferences.setLoggedIn(true)

            _isLoading.value = false
            validationEventChannel.send(ValidationEvent.Success)
        }
    }


}

sealed class ValidationEvent {
    object Success : ValidationEvent()
}
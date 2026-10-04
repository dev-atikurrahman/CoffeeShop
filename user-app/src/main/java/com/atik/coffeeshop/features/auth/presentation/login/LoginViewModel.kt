package com.atik.coffeeshop.features.auth.presentation.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.atik.coffeeshop.core.ApiException
import com.atik.coffeeshop.features.auth.domain.repository.AuthRepository
import com.atik.coffeeshop.features.auth.presentation.register.ValidationEvent
import com.atik.coffeeshop.shared.data.preferences.UserPreferences
import com.atik.coffeeshop.shared.domain.use_case.ValidateEmail
import com.atik.coffeeshop.shared.domain.use_case.ValidateLoginPassword
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class LoginViewModel(
    private val authRepository: AuthRepository,
    private val userPreferences: UserPreferences,
    private val validateEmail: ValidateEmail = ValidateEmail(),
    private val validatePassword: ValidateLoginPassword = ValidateLoginPassword()
) : ViewModel() {
    companion object {
        const val AUTH_TAG = "AUTH_DEBUG"
    }

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    var state by mutableStateOf(LoginFormState())
        private set

    private val validationEventChannel = Channel<ValidationEvent>()
    val validationEvents = validationEventChannel.receiveAsFlow()

    fun onEmailChanged(email: String) {
        state = state.copy(email = email, emailError = null)
    }

    fun onPasswordChanged(password: String) {
        state = state.copy(password = password, passwordError = null)
    }

    fun onRememberMeChanged(checked: Boolean) {
        state = state.copy(rememberMe = checked)
    }

    fun onLoginClick() {
        if (_isLoading.value) return

        val emailResult = validateEmail.execute(state.email)
        val passwordResult = validatePassword.execute(state.password)

        val hasError = listOf(emailResult, passwordResult).any { !it.successful }

        state = state.copy(
            emailError = emailResult.errorMessage,
            passwordError = passwordResult.errorMessage,
            generalError = null
        )
        if (!emailResult.successful || !passwordResult.successful) return

        viewModelScope.launch {
            _isLoading.value = true
            try {
                authRepository.login(state.email, state.password).fold(
                    onSuccess = { session ->
                        userPreferences.saveSession(session.token, rememberMe = state.rememberMe)
                        validationEventChannel.send(ValidationEvent.Success)
                    },
                    onFailure = { e ->
                        val api = e as? ApiException
                        val fields = api?.fieldErrors.orEmpty()
                        state = state.copy(
                            emailError = fields["email"]?.firstOrNull(),
                            passwordError = fields["password"]?.firstOrNull(),
                        )
                        validationEventChannel.send(
                            ValidationEvent.Error(api?.message ?: "Something went wrong")
                        )
                    }
                )
            } finally {
                _isLoading.value = false
            }
        }
    }
}
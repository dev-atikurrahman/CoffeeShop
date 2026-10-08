package com.atik.coffeeshop.features.auth.presentation.register

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.atik.coffeeshop.R
import com.atik.coffeeshop.core.VerticalSpacer
import com.atik.coffeeshop.features.auth.presentation.components.AppLogoSection
import com.atik.coffeeshop.features.auth.presentation.components.EmailTextField
import com.atik.coffeeshop.features.auth.presentation.components.NameTextField
import com.atik.coffeeshop.features.auth.presentation.components.PasswordTextField
import com.atik.coffeeshop.features.auth.presentation.components.TextSection
import com.atik.coffeeshop.ui.components.AppCheckBox
import com.atik.coffeeshop.ui.components.AppSnackbarHost
import com.atik.coffeeshop.ui.components.AuthButton
import com.atik.coffeeshop.ui.components.LoadingOverlay
import com.atik.coffeeshop.ui.theme.authGradientBackground
import com.atik.coffeeshop.ui.utils.rememberDebouncedOnClick
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@Composable
fun RegisterScreen(
    onRegisterSuccess: () -> Unit,
    onNavigateToLogin: () -> Unit,
    viewModel: RegisterViewModel = koinViewModel()
) {
    val isLoading by viewModel.isLoading.collectAsStateWithLifecycle()
    val state = viewModel.state
    val scrollState = rememberScrollState()
    val snackbarHostState = remember { SnackbarHostState() }
    val focusManager = LocalFocusManager.current

    val submit = {
        focusManager.clearFocus()
        viewModel.onRegisterClick()
    }

    LaunchedEffect(Unit) {
        viewModel.validationEvents.collect { event ->
            when (event) {
                ValidationEvent.Success -> onRegisterSuccess()
                is ValidationEvent.Error -> {
                    launch {
                        snackbarHostState.currentSnackbarData?.dismiss()
                        snackbarHostState.showSnackbar(event.message)
                    }
                }
            }
        }
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .authGradientBackground()
    ) {
        LoadingOverlay(
            isLoading = isLoading,
            message = stringResource(R.string.loading_message)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 24.dp)
                    .verticalScroll(scrollState)
                    .windowInsetsPadding(WindowInsets.safeDrawing),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                AppLogoSection()
                VerticalSpacer(size = 24.dp)

                TextSection(
                    heading = stringResource(R.string.create_account),
                    subtitle = stringResource(R.string.sign_up_to)
                )
                Spacer(modifier = Modifier.height(24.dp))


                NameTextField(
                    value = state.name,
                    onValueChange = viewModel::onNameChanged,
                    errorMessage = state.nameError
                )
                VerticalSpacer(size = 8.dp)

                EmailTextField(
                    value = state.email,
                    onValueChange = viewModel::onEmailChanged,
                    errorMessage = state.emailError
                )
                VerticalSpacer(size = 8.dp)

                PasswordTextField(
                    value = state.password,
                    onValueChange = viewModel::onPasswordChanged,
                    label = stringResource(R.string.password_hint),
                    errorMessage = state.passwordError,
                    imeAction = ImeAction.Next
                )
                VerticalSpacer(size = 8.dp)

                PasswordTextField(
                    value = state.repeatedPassword,
                    onValueChange = viewModel::onRepeatedPasswordChanged,
                    label = stringResource(R.string.repeat_password_hint),
                    errorMessage = state.repeatedPasswordError,
                    imeAction = ImeAction.Done
                )
                VerticalSpacer(size = 8.dp)

                AppCheckBox(
                    checked = state.acceptedTerms,
                    onCheckedChange = viewModel::onTermsAcceptedChanged,
                    label = stringResource(R.string.accept_terms),
                    errorMessage = state.termsError,
                    modifier = Modifier.fillMaxWidth()
                )

                VerticalSpacer(size = 24.dp)
                AuthButton(
                    text = if (isLoading) "..." else stringResource(R.string.register),
                    enabled = !isLoading,
                    onClick = submit,
                    containerColor = colorResource(R.color.green),
                    contentColor = colorResource(R.color.white)
                )


                VerticalSpacer(size = 24.dp)
                TextButton(
                    onClick = rememberDebouncedOnClick { onNavigateToLogin() }
                ) {
                    Text(stringResource(R.string.have_account))
                }
            }
        }

        AppSnackbarHost(
            hostState = snackbarHostState,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .windowInsetsPadding(WindowInsets.safeDrawing)
                .padding(16.dp)
        )

    }

}
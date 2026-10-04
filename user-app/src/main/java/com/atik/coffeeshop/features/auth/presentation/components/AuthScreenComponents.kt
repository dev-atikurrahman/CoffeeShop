package com.atik.coffeeshop.features.auth.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.rounded.AlternateEmail
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.atik.coffeeshop.R
import com.atik.coffeeshop.core.HorizontalSpacer
import com.atik.coffeeshop.ui.components.AppCheckBox
import com.atik.coffeeshop.ui.components.AuthField
import com.atik.coffeeshop.ui.components.AuthHeadingText
import com.atik.coffeeshop.ui.components.AuthText
import com.atik.coffeeshop.ui.components.ButtonText
import com.atik.coffeeshop.ui.components.EndIconMode
import com.atik.coffeeshop.ui.components.HelperText
import com.atik.coffeeshop.ui.components.SocialIconButton


@Composable
fun AppLogoSection() {
    val shape = RoundedCornerShape(24.dp)
    Box(
        modifier = Modifier
            .clip(shape)
            .background(colorResource(R.color.white)),
        contentAlignment = Alignment.Center,
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "Logo",
            modifier = Modifier.size(90.dp)
        )
    }
}

@Composable
fun TextSection(
    heading: String,
    subtitle: String
) {
    AuthHeadingText(text = heading)
    Spacer(modifier = Modifier.height(8.dp))
    AuthText(text = subtitle)
}

val UserIcon = Icons.Rounded.AlternateEmail
val EmailIcon = Icons.Outlined.Email
val PasswordIcon = Icons.Outlined.Lock

@Composable
fun NameTextField(
    value: String,
    onValueChange: (String) -> Unit,
    errorMessage: String? = null,
) {
    AuthField(
        value = value,
        onValueChange = onValueChange,
        label = stringResource(R.string.enter_your_name),
        leadingIcon = UserIcon,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Next
        ),
        endIconMode = EndIconMode.CLEAR_TEXT,
        isError = errorMessage != null,
        supportingText = errorMessage?.let { message ->
            {
                HelperText(text = message)
            }
        },
    )
}

@Composable
fun EmailTextField(
    value: String,
    onValueChange: (String) -> Unit,
    errorMessage: String? = null,
) {
    AuthField(
        value = value,
        onValueChange = onValueChange,
        label = stringResource(R.string.email_hint),
        leadingIcon = EmailIcon,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Email,
            imeAction = ImeAction.Next
        ),
        endIconMode = EndIconMode.CLEAR_TEXT,
        isError = errorMessage != null,
        supportingText = errorMessage?.let { message ->
            { HelperText(text = message) }
        },
    )
}

@Composable
fun PasswordTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String = stringResource(R.string.password_hint),
    errorMessage: String? = null,
    imeAction: ImeAction = ImeAction.Done,
    keyboardActions: KeyboardActions = KeyboardActions.Default
) {
    AuthField(
        value = value,
        onValueChange = onValueChange,
        label = label,
        leadingIcon = PasswordIcon,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Password,
            imeAction = imeAction
        ),
        endIconMode = EndIconMode.PASSWORD_TOGGLE,
        isError = errorMessage != null,
        supportingText = errorMessage?.let { message ->
            {
                HelperText(text = message)
            }
        },
        keyboardActions = keyboardActions
    )
}


@Composable
fun RememberAndForgotSection(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    onForgotPasswordClick: () -> Unit,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        AppCheckBox(
            checked = checked,
            onCheckedChange = onCheckedChange,
            label = stringResource(R.string.remember_me)
        )

        ButtonText(
            text = stringResource(R.string.forgot_password),
            color = colorResource(R.color.black),
            maxLines = 1,
            modifier = Modifier.clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) { onForgotPasswordClick() }
        )
    }
}


@Composable
fun SocialSignInSection(
    onFacebookClick: () -> Unit,
    onGoogleClick: () -> Unit,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        SocialIconButton(
            iconResId = R.drawable.google,
            contentDescriptor = "Sign in with Google",
            onClick = onGoogleClick
        )

        HorizontalSpacer(size = 24.dp)
        SocialIconButton(
            iconResId = R.drawable.facebook,
            contentDescriptor = "Sign in with Facebook",
            onClick = onFacebookClick
        )
    }
}

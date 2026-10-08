package com.atik.coffeeshop.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Clear
import androidx.compose.material.icons.rounded.Visibility
import androidx.compose.material.icons.rounded.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.autofill.ContentType
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.semantics.contentType
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.atik.coffeeshop.ui.R

enum class EndIconMode {
    NONE,
    CLEAR_TEXT,
    PASSWORD_TOGGLE
}

val roundedShape = RoundedCornerShape(12.dp)

@Composable
fun AuthField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    modifier: Modifier = Modifier,
    leadingIcon: ImageVector? = null,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    shape: Shape = roundedShape,
    endIconMode: EndIconMode = EndIconMode.NONE,
    isError: Boolean = false,
    supportingText: (@Composable () -> Unit)? = null,
    keyboardActions: KeyboardActions = KeyboardActions.Default
) {
    var passwordVisibility by remember { mutableStateOf(false) }
    val isPasswordField = endIconMode == EndIconMode.PASSWORD_TOGGLE

    val isNotEmpty = value.isNotEmpty()

    val brownColor = colorResource(R.color.darkBrown)
    val grayColor = colorResource(R.color.gray)
    val black = colorResource(R.color.black)
    val error = colorResource(R.color.errorColor)

    val clearIcon = Icons.Rounded.Clear
    val visibility = Icons.Rounded.Visibility
    val visibilityOff = Icons.Rounded.VisibilityOff

    val effectiveKeyboardOptions = if (isPasswordField) {
        keyboardOptions.copy(keyboardType = KeyboardType.Password)
    } else {
        keyboardOptions
    }

    val autofillModifier = if (isPasswordField) {
        Modifier.semantics { contentType = ContentType.Password }
    } else {
        Modifier
    }

    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { HintText(text = label) },
        modifier = modifier
            .fillMaxWidth()
            .then(autofillModifier),
        leadingIcon = leadingIcon?.let { icon ->
            {
                Icon(
                    imageVector = icon,
                    contentDescription = null
                )
            }
        },
        keyboardOptions = effectiveKeyboardOptions,
        singleLine = true,
        isError = isError,
        supportingText = supportingText,
        keyboardActions = keyboardActions,
        shape = shape,

        visualTransformation = if (endIconMode == EndIconMode.PASSWORD_TOGGLE && !passwordVisibility) {
            PasswordVisualTransformation()
        } else {
            VisualTransformation.None
        },

        trailingIcon = when (endIconMode) {
            EndIconMode.CLEAR_TEXT -> {
                if (isNotEmpty) {
                    {
                        IconButton(onClick = { onValueChange("") }) {
                            Icon(imageVector = clearIcon, contentDescription = "Clear text")
                        }
                    }
                } else null
            }

            EndIconMode.PASSWORD_TOGGLE -> {
                {
                    val icon = if (passwordVisibility) visibility else visibilityOff
                    IconButton(onClick = { passwordVisibility = !passwordVisibility }) {
                        Icon(imageVector = icon, contentDescription = "Toggle password")
                    }
                }
            }

            EndIconMode.NONE -> null
        },

        colors = OutlinedTextFieldDefaults.colors(
            focusedTextColor = black,
            unfocusedTextColor = black,

            focusedBorderColor = brownColor,
            unfocusedBorderColor = grayColor,

            focusedLeadingIconColor = brownColor,
            unfocusedLeadingIconColor = grayColor,

            focusedTrailingIconColor = brownColor,
            unfocusedTrailingIconColor = grayColor,

            focusedLabelColor = brownColor,
            unfocusedLabelColor = if (isNotEmpty) black else grayColor,

            cursorColor = brownColor,

            errorBorderColor = error,
            errorLabelColor = error,
            errorLeadingIconColor = error,
            errorTrailingIconColor = error,
            errorCursorColor = error,

            )
    )
}
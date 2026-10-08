package com.atik.coffeeshop.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.atik.coffeeshop.ui.R

@Composable
fun AppCheckBox(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    label: String,
    modifier: Modifier = Modifier,
    checkedColor: Color = colorResource(R.color.darkBrown),
    uncheckedColor: Color = colorResource(R.color.gray),
    errorMessage: String? = null
) {
    val whiteColor = colorResource(R.color.white)
    val grayColor = colorResource(R.color.gray)
    val errorColor = colorResource(R.color.errorColor)
    Row(
        modifier = modifier.clickable(
            interactionSource = remember { MutableInteractionSource() },
            indication = null
        ) { onCheckedChange(!checked) },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            checked = checked,
            onCheckedChange = onCheckedChange,
            colors = CheckboxDefaults.colors(
                checkedColor = checkedColor,
                uncheckedColor = uncheckedColor,
                checkmarkColor = whiteColor,
            )
        )
        CaptionText(text = label, color = grayColor, maxLines = 1)
    }

    if (errorMessage != null) {
        HelperText(
            text = errorMessage,
            color = errorColor,
            modifier = Modifier.padding(start = 14.dp)
        )
    }
}
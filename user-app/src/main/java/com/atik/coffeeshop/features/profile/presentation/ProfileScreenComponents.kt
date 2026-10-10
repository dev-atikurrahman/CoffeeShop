package com.atik.coffeeshop.features.profile.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.KeyboardArrowRight
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.atik.coffeeshop.R
import com.atik.coffeeshop.core.HorizontalSpacer
import com.atik.coffeeshop.core.VerticalSpacer
import com.atik.coffeeshop.ui.components.AppButton1
import com.atik.coffeeshop.ui.components.CaptionText
import com.atik.coffeeshop.ui.components.MenuText
import com.atik.coffeeshop.ui.components.TitleText
import com.atik.coffeeshop.ui.theme.ThemeMode


@Composable
fun ProfileHeader(
    name: String,
    email: String,
    imageUrl: String?,
    onEditProfileClick: () -> Unit,
    modifier: Modifier = Modifier
) {

    Row(
        modifier = modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        ProfileAvatar(
            imageUrl = imageUrl,
            modifier = Modifier.size(120.dp)
        )
        HorizontalSpacer(size = 12.dp)
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(2.dp),
        ) {

            TitleText(
                text = name,
                color = colorResource(R.color.black),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            CaptionText(
                text = email,
                color = colorResource(R.color.textDark),
                maxLines = 1
            )

            VerticalSpacer(size = 6.dp)
            AppButton1(
                text = stringResource(R.string.edit_profile),
                onClick = onEditProfileClick,
                containerColor = colorResource(R.color.darkBrown),
                modifier = Modifier.width(130.dp)
            )
        }
    }
}

@Composable
fun ProfileAvatar(
    imageUrl: String?,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val fallback = painterResource(R.drawable.profile)
    val request = remember(imageUrl) {
        ImageRequest.Builder(context)
            .data(imageUrl?.takeIf { it.isNotBlank() })
            .crossfade(true)
            .build()
    }
    AsyncImage(
        model = request,
        contentDescription = null,
        placeholder = fallback,
        error = fallback,
        fallback = fallback,
        contentScale = ContentScale.Fit,
        modifier = modifier.clip(CircleShape)
    )
}


@Composable
fun ProfileMenuItem(
    icon: ImageVector,
    title: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    style: ProfileMenuStyle = ProfileMenuStyle.Default
) {
    val isDestructive = style == ProfileMenuStyle.Destructive
    val iconTint = colorResource(if (isDestructive) R.color.errorColor else R.color.darkBrown)
    val textColor = colorResource(if (isDestructive) R.color.errorColor else R.color.textDark)
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable(role = Role.Button, onClick = onClick)
            .heightIn(min = 56.dp)
            .padding(horizontal = 10.dp, vertical = 5.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Box(
            modifier = modifier
                .size(46.dp)
                .clip(CircleShape)
                .background(colorResource(R.color.lightBrownShade100)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = iconTint,
                modifier = modifier.size(24.dp)
            )
        }

        MenuText(
            text = title,
            color = textColor,
            modifier = modifier.weight(1f)
        )
        if (!isDestructive) {
            Icon(
                imageVector = Icons.AutoMirrored.Rounded.KeyboardArrowRight,
                contentDescription = null,
                tint = colorResource(R.color.textGray),
                modifier = modifier.size(20.dp)
            )
        }
    }

}

@Composable
public fun LogoutDialog(onConfirm: () -> Unit, onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(stringResource(R.string.log_out)) },
        text = { Text(stringResource(R.string.are_confirm_to_logged_out)) },
        confirmButton = { TextButton(onClick = onConfirm) { Text(stringResource(R.string.log_out)) } },
        dismissButton = { TextButton(onClick = onDismiss) { Text(stringResource(R.string.cancel)) } }
    )
}

@Composable
fun LanguageDialog(
    selected: String,
    onSelect: (String) -> Unit,
    onDismiss: () -> Unit
) {
    val languages = listOf("bn" to "বাংলা", "en" to "English")
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            TitleText(
                stringResource(R.string.language),
                color = colorResource(R.color.textDark)
            )
        },
        text = {
            Column(Modifier.selectableGroup()) {
                languages.forEach { (code, label) ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .selectable(
                                selected = code == selected,
                                role = Role.RadioButton
                            ) { onSelect(code) }
                            .padding(vertical = 12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(selected = code == selected, onClick = null)
                        HorizontalSpacer(size = 12.dp)
                        Text(label)
                    }
                }
            }
        },
        confirmButton = {},
        dismissButton = { TextButton(onClick = onDismiss) { Text(stringResource(R.string.cancel)) } }
    )
}

@Composable
fun ThemeDialog(
    selected: ThemeMode,
    onSelect: (ThemeMode) -> Unit,
    onDismiss: () -> Unit
) {
    val options = listOf(
        ThemeMode.SYSTEM to "System default",
        ThemeMode.LIGHT to "Light",
        ThemeMode.DARK to "Dark",
    )
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            TitleText(
                stringResource(R.string.theme),
                color = colorResource(R.color.textDark)
            )
        },
        text = {
            Column(Modifier.selectableGroup()) {
                options.forEach { (code, label) ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .selectable(
                                selected = code == selected,
                                role = Role.RadioButton
                            ) { onSelect(code) }
                            .padding(vertical = 12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(selected = code == selected, onClick = null)
                        HorizontalSpacer(size = 12.dp)
                        Text(label)
                    }
                }
            }
        },
        confirmButton = {},
        dismissButton = { TextButton(onClick = onDismiss) { Text(stringResource(R.string.cancel)) } }
    )
}

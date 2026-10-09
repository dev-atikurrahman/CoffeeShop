package com.atik.coffeeshop.features.profile.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.Logout
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.rounded.CreditCard
import androidx.compose.material.icons.rounded.History
import androidx.compose.material.icons.rounded.Language
import androidx.compose.material.icons.rounded.NotificationsNone
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.atik.coffeeshop.R
import com.atik.coffeeshop.ui.components.AppLoadingIndicator
import com.atik.coffeeshop.ui.components.CaptionText
import org.koin.androidx.compose.koinViewModel


private val profileMenuItems = listOf(
    ProfileMenuItemUi(
        ProfileMenuAction.Settings, Icons.Outlined.Settings, R.string.profile_setting
    ),
    ProfileMenuItemUi(
        ProfileMenuAction.Location, Icons.Outlined.LocationOn, R.string.location
    ),
    ProfileMenuItemUi(
        ProfileMenuAction.Payment, Icons.Rounded.CreditCard, R.string.payment_methods
    ),
    ProfileMenuItemUi(
        ProfileMenuAction.History, Icons.Rounded.History, R.string.order_history
    ),
    ProfileMenuItemUi(
        ProfileMenuAction.Language, Icons.Rounded.Language, R.string.language
    ),
    ProfileMenuItemUi(
        ProfileMenuAction.Notification, Icons.Rounded.NotificationsNone, R.string.notification
    ),
    ProfileMenuItemUi(
        ProfileMenuAction.Logout,
        Icons.AutoMirrored.Rounded.Logout,
        R.string.log_out,
        ProfileMenuStyle.Destructive
    ),
)

@Composable
fun ProfileRoot(
    onNavigate: (ProfileDestination) -> Unit,
    onLoggedOut: () -> Unit,
    viewModel: ProfileViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.events.collect { event ->
            when (event) {
                is ProfileEvent.Navigate -> onNavigate(event.destination)
                ProfileEvent.LoggedOut -> onLoggedOut()
            }
        }
    }
    ProfileScreen(state = state, onAction = viewModel::onAction)
}

@Composable
fun ProfileScreen(
    state: ProfileUiState,
    onAction: (ProfileAction) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        when {
            state.isLoading -> Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp),
                contentAlignment = Alignment.Center
            ) {
                AppLoadingIndicator(size = 32.dp)
            }

            state.error != null -> Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CaptionText(text = state.error, color = colorResource(R.color.errorColor))
                TextButton(onClick = { onAction(ProfileAction.Retry) }) { Text("Retry") }
            }

            else -> ProfileHeader(
                name = state.name,
                email = state.email,
                imageUrl = state.imageUrl,
                onEditProfileClick = { onAction(ProfileAction.EditProfile) }
            )
        }



        ProfileMenuCard(
            items = profileMenuItems,
            onItemClick = {
                onAction(ProfileAction.MenuClick(it))
            })
    }

    when (state.dialog) {

        ProfileDialog.Language -> LanguageDialog(
            selected = state.selectedLanguage,
            onSelect = { onAction(ProfileAction.LanguageSelected(it)) },
            onDismiss = { onAction(ProfileAction.DismissDialog) })

        ProfileDialog.LogoutConfirm -> LogoutDialog(
            onConfirm = { onAction(ProfileAction.ConfirmLogout) },
            onDismiss = { onAction(ProfileAction.DismissDialog) })

        null -> Unit
    }
}

@Composable
fun ProfileMenuCard(
    items: List<ProfileMenuItemUi>,
    onItemClick: (ProfileMenuAction) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(colorResource(R.color.profileBg))
    ) {
        items.forEachIndexed { index, item ->
            ProfileMenuItem(
                icon = item.icon,
                title = stringResource(item.titleRes),
                style = item.style,
                onClick = { onItemClick(item.action) })
            if (index < items.lastIndex) {
                HorizontalDivider(color = colorResource(R.color.dividerGray))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ProfileScreenPreview() {
    ProfileScreen(
        state = ProfileUiState(name = "Tina Anderson", email = "tina@gmail.com"), onAction = {})
}
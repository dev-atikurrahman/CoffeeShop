package com.atik.coffeeshop.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider

private val DarkColorScheme = darkColorScheme(
    primary = Purple80,
    secondary = PurpleGrey80,
    tertiary = Pink80
)

private val LightColorScheme = lightColorScheme(
    primary = Purple40,
    secondary = PurpleGrey40,
    tertiary = Pink40
)

@Composable
fun CoffeeShopTheme(
    themeMode: ThemeMode = ThemeMode.SYSTEM,
    content: @Composable () -> Unit
) {
    val darkTheme = when (themeMode) {
        ThemeMode.SYSTEM -> isSystemInDarkTheme()
        ThemeMode.LIGHT -> false
        ThemeMode.DARK -> true
    }

    val appColors = if (darkTheme) DarkAppColors else LightAppColors

    val materialScheme = if (darkTheme) {
        darkColorScheme(
            primary = appColors.primary, onPrimary = appColors.onPrimary,
            background = appColors.background, onBackground = appColors.textPrimary,
            surface = appColors.surface, onSurface = appColors.textPrimary,
            error = appColors.error
        )
    } else {
        lightColorScheme(
            primary = appColors.primary, onPrimary = appColors.onPrimary,
            background = appColors.background, onBackground = appColors.textPrimary,
            surface = appColors.surface, onSurface = appColors.textPrimary,
            error = appColors.error
        )
    }

    CompositionLocalProvider(LocalAppColors provides appColors) {
        MaterialTheme(colorScheme = materialScheme, typography = Typography, content = content)
    }
}
package com.atik.coffeeshop.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

@Immutable
data class AppColors(
    val isDark: Boolean,
    val background: Color,        // appBackground / lightCream
    val screenBackground: Color,  // profile Bg
    val surface: Color,           // white card (profile menu)
    val card: Color,              // cream (chips, search, qty)
    val container: Color,         // lightBrown (coffee item card)
    val iconCircle: Color,        // lightBrownShade100
    val textPrimary: Color,       // black / textDark
    val textSecondary: Color,     // lightGray / textGray
    val divider: Color,           // dividerGray
    val accent: Color,            // darkBrown
    val primary: Color,           // green (buttons)
    val onPrimary: Color,
    val indicator: Color,
    val error: Color,
)

val LightAppColors = AppColors(
    isDark = false,
    background = Color(0xFFFDF6EB),
    screenBackground = Color(0xFFF5F5F5),
    surface = Color(0xFFFFFFFF),
    card = Color(0xFFEADDCB),
    container = Color(0xFFF3CE94),
    iconCircle = Color(0x32F3CE94),
    textPrimary = Color(0xFF1A1A1A),
    textSecondary = Color(0xFF757575),
    divider = Color(0xFFEEEEEE),
    accent = Color(0xFF7E4616),
    primary = Color(0xFF064C3E),
    onPrimary = Color.White,
    indicator = Color(0x26E1C9A2),
    error = Color(0xFFFF0000),
)

val DarkAppColors = AppColors(
    isDark = true,
    background = Color(0xFF1C1613),
    screenBackground = Color(0xFF141110),
    surface = Color(0xFF241C17),
    card = Color(0xFF2B221C),
    container = Color(0xFF3A2C20),
    iconCircle = Color(0x32F3CE94),
    textPrimary = Color(0xFFF2E8DC),
    textSecondary = Color(0xFFB8AA9A),
    divider = Color(0xFF3A322B),
    accent = Color(0xFFD9A066),      // lighter, dark bg te contrast er jonno
    primary = Color(0xFF1F7A64),
    onPrimary = Color.White,
    indicator = Color(0x26E1C9A2),
    error = Color(0xFFFF6B6B),
)


val LocalAppColors = staticCompositionLocalOf { LightAppColors }

object AppTheme {
    val colors: AppColors
        @Composable @ReadOnlyComposable
        get() = LocalAppColors.current
}
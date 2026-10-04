package com.atik.coffeeshop.ui.components

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.sp
import com.atik.coffeeshop.ui.R

val Poppins = FontFamily(
    Font(resId = R.font.poppins_regular, weight = FontWeight.Normal),
    Font(resId = R.font.poppins_medium, weight = FontWeight.Medium),
    Font(resId = R.font.poppins_semibold, weight = FontWeight.SemiBold),
    Font(resId = R.font.poppins_bold, weight = FontWeight.Bold),
)

/* --- Text Style --- */
private object TextStyles {
    // Onboarding hero heading — largest text in the app
    val onboarding = TextStyle(
        fontSize = 36.sp,
        fontWeight = FontWeight.SemiBold,
        fontFamily = Poppins
    )

    // Auth screen heading — "Welcome Back", "Registration"
    val authHeading = TextStyle(
        fontSize = 28.sp,
        fontWeight = FontWeight.Medium,
        fontFamily = Poppins
    )

    // Generic section heading — top bars, screen titles
    val heading = TextStyle(
        fontSize = 22.sp,
        fontWeight = FontWeight.SemiBold
    )

    // Item/section titles — "Coffee Size", "Qty", "Description"
    val title = TextStyle(
        fontSize = 18.sp,
        fontWeight = FontWeight.Medium,
        fontFamily = Poppins
    )

    // Auth body copy — "Sign in to access all the smart features"
    val authBody = TextStyle(
        fontSize = 16.sp,
        fontWeight = FontWeight.Normal,
        fontFamily = Poppins
    )

    // Auth secondary emphasis — "Forgot Password?" style labels
    val authBodyMedium = TextStyle(
        fontSize = 16.sp,
        fontWeight = FontWeight.Medium,
        fontFamily = Poppins
    )

    // Generic body — item titles/descriptions in cards
    val body = TextStyle(
        fontSize = 16.sp,
        fontWeight = FontWeight.SemiBold,
        fontFamily = Poppins
    )

    // Button label
    val button = TextStyle(
        fontSize = 16.sp,
        fontWeight = FontWeight.Normal
    )

    // Price — same scale as title, kept as a distinct semantic style
    val price = TextStyle(
        fontSize = 18.sp,
        fontWeight = FontWeight.Medium,
        fontFamily = Poppins
    )

    // Caption/description — smallest readable body text
    val caption = TextStyle(
        fontSize = 14.sp,
        fontWeight = FontWeight.Normal,
        fontFamily = Poppins
    )

    // Hint/placeholder-adjacent text — same scale as caption
    val hint = TextStyle(
        fontSize = 14.sp,
        fontWeight = FontWeight.Normal,
        fontFamily = Poppins
    )

    // Smallest text — password strength meter, field helper text
    val helper = TextStyle(
        fontSize = 12.sp,
        fontWeight = FontWeight.Normal,
        fontFamily = Poppins
    )
}

@Composable
fun OnboardingHeading(
    text: String,
    modifier: Modifier = Modifier,
    color: Color,
    maxLines: Int = Int.MAX_VALUE,
    overflow: TextOverflow = TextOverflow.Clip
) {
    Text(
        text = text,
        modifier = modifier,
        style = TextStyles.onboarding,
        color = color,
        maxLines = maxLines,
        overflow = overflow
    )
}

/* ============================================================
 * AUTH SCREENS (Login / Register)
 * ============================================================ */

@Composable
fun AuthHeadingText(
    text: String,
    modifier: Modifier = Modifier,
    color: Color = colorResource(R.color.black),
    maxLines: Int = Int.MAX_VALUE,
    overflow: TextOverflow = TextOverflow.Clip
) {
    Text(
        text = text,
        modifier = modifier,
        style = TextStyles.authHeading,
        color = color,
        maxLines = maxLines,
        overflow = overflow
    )
}

@Composable
fun AuthText(
    text: String,
    modifier: Modifier = Modifier,
    textAlign: TextAlign = TextAlign.Center,
    color: Color = colorResource(R.color.lightGray),
    maxLines: Int = Int.MAX_VALUE,
    overflow: TextOverflow = TextOverflow.Clip
) {
    Text(
        text = text,
        modifier = modifier,
        textAlign = textAlign,
        style = TextStyles.authBody,
        color = color,
        maxLines = maxLines,
        overflow = overflow
    )
}

@Composable
fun AuthSectionTitleText(
    text: String,
    modifier: Modifier = Modifier,
    textAlign: TextAlign = TextAlign.Center,
    color: Color = colorResource(R.color.black),
    maxLines: Int = Int.MAX_VALUE,
    overflow: TextOverflow = TextOverflow.Clip
) {
    Text(
        text = text,
        modifier = modifier,
        textAlign = textAlign,
        style = TextStyles.authBodyMedium,
        color = color,
        maxLines = maxLines,
        overflow = overflow
    )
}

/* ============================================================
 * GENERIC SCREEN TEXT
 * ============================================================ */

@Composable
fun HeadingText(
    text: String,
    modifier: Modifier = Modifier,
    color: Color = Color.Black,
    maxLines: Int = Int.MAX_VALUE,
    overflow: TextOverflow = TextOverflow.Clip
) {
    Text(
        text = text,
        modifier = modifier,
        style = TextStyles.heading,
        color = color,
        maxLines = maxLines,
        overflow = overflow
    )
}

@Composable
fun TitleText(
    text: String,
    modifier: Modifier = Modifier,
    color: Color,
    maxLines: Int = Int.MAX_VALUE,
    overflow: TextOverflow = TextOverflow.Clip
) {
    Text(
        text = text,
        modifier = modifier,
        style = TextStyles.title,
        color = color,
        maxLines = maxLines,
        overflow = overflow
    )
}

@Composable
fun BodyText(
    text: String,
    modifier: Modifier = Modifier,
    color: Color,
    maxLines: Int = Int.MAX_VALUE,
    overflow: TextOverflow = TextOverflow.Clip
) {
    Text(
        text = text,
        modifier = modifier,
        style = TextStyles.body,
        color = color,
        maxLines = maxLines,
        overflow = overflow
    )
}

@Composable
fun ButtonText(
    text: String,
    modifier: Modifier = Modifier,
    color: Color,
    maxLines: Int = Int.MAX_VALUE,
    overflow: TextOverflow = TextOverflow.Clip
) {
    Text(
        text = text,
        modifier = modifier,
        style = TextStyles.button,
        color = color,
        maxLines = maxLines,
        overflow = overflow
    )
}

@Composable
fun PriceText(
    text: String,
    modifier: Modifier = Modifier,
    color: Color,
    maxLines: Int = Int.MAX_VALUE,
    overflow: TextOverflow = TextOverflow.Clip
) {
    Text(
        text = text,
        modifier = modifier,
        style = TextStyles.price,
        color = color,
        maxLines = maxLines,
        overflow = overflow
    )
}

@Composable
fun CaptionText(
    text: String,
    modifier: Modifier = Modifier,
    color: Color = colorResource(R.color.lightGray),
    maxLines: Int = Int.MAX_VALUE,
    overflow: TextOverflow = TextOverflow.Ellipsis
) {
    Text(
        text = text,
        modifier = modifier,
        style = TextStyles.caption,
        color = color,
        maxLines = maxLines,
        overflow = overflow
    )
}

@Composable
fun HintText(
    text: String,
    modifier: Modifier = Modifier,
    color: Color = colorResource(R.color.gray),
    maxLines: Int = Int.MAX_VALUE,
    overflow: TextOverflow = TextOverflow.Clip
) {
    Text(
        text = text,
        modifier = modifier,
        style = TextStyles.hint,
        color = color,
        maxLines = maxLines,
        overflow = overflow
    )
}

/* ============================================================
 * FIELD / HELPER TEXT (validation messages, strength meters)
 * ============================================================ */

@Composable
fun HelperText(
    text: String,
    modifier: Modifier = Modifier,
    color: Color = Color.Unspecified,
    maxLines: Int = Int.MAX_VALUE,
    overflow: TextOverflow = TextOverflow.Clip
) {
    Text(
        text = text,
        modifier = modifier,
        style = TextStyles.helper,
        color = color,
        maxLines = maxLines,
        overflow = overflow
    )
}
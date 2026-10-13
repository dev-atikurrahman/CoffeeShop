package com.atik.coffeeshop.ui.components

import android.content.res.Configuration
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.atik.coffeeshop.ui.theme.CoffeeShopTheme

@Composable
private fun ColorRow(name: String, bg: Color, fg: Color) {
    Box(
        Modifier
            .fillMaxWidth()
            .background(bg)
            .padding(12.dp)
    ) { Text(text = name, color = fg) }
}

@Preview(showBackground = true, name = "Light")
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES, name = "Dark")
@Composable
private fun ThemePalettePreview() {
    CoffeeShopTheme {
        val c = MaterialTheme.colorScheme
        Column(
            modifier = Modifier.fillMaxSize()
        ){
            ColorRow("background / onBackground", c.background, c.onBackground)
            ColorRow("primary / onPrimary", c.primary, c.onPrimary)
            ColorRow("primaryContainer / onPrimaryContainer", c.primaryContainer, c.onPrimaryContainer)
            ColorRow("secondaryContainer / onSecondaryContainer", c.secondaryContainer, c.onSecondaryContainer)
            ColorRow("tertiaryContainer / onTertiaryContainer", c.tertiaryContainer, c.onTertiaryContainer)
            ColorRow("surfaceContainer / onSurface", c.surfaceContainer, c.onSurface)
            ColorRow("surfaceContainerHigh / onSurface", c.surfaceContainerHigh, c.onSurface)
            ColorRow("inverseSurface / inverseOnSurface", c.inverseSurface, c.inverseOnSurface)
            ColorRow("error / onError", c.error, c.onError)
        }
    }
}
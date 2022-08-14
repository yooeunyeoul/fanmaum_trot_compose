package com.trotfan.trot.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorPalette = darkColors(
    primary = Primary200,
    primaryVariant = Primary700,
    secondary = Secondary200,
    background = Color.White
)

private val LightColorPalette = lightColors(
    primary = Primary500,
    primaryVariant = Primary700,
    secondary = Secondary200,
    background = Color.White
)

@Composable
fun FanwooriTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        content = content
    )
}
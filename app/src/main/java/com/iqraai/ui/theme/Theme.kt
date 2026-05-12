package com.iqraai.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val IqraColorScheme = lightColorScheme(
    primary = Primary,
    secondary = Secondary,
    background = Background,
    surface = Surface,
    error = Error,
    onPrimary = OffWhite,
    onSecondary = Charcoal,
    onBackground = Charcoal,
    onSurface = Charcoal,
    onError = OffWhite
)

@Composable
fun IqraTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = IqraColorScheme,
        typography = Typography,
        shapes = IqraShapes,
        content = content
    )
}

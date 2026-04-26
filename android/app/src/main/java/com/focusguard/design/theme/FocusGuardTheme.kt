package com.focusguard.design.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val BoneWhite = Color(0xFFF5F2EA)
val Panel = Color(0xFFFCFBF8)
val Charcoal = Color(0xFF1B1B1B)
val Muted = Color(0xFF6C6A63)
val Line = Color(0xFFD8D2C7)
val DeepSage = Color(0xFF496A5A)
val SageSoft = Color(0xFFE5EEE8)
val Clay = Color(0xFFA67C5B)
val MutedRust = Color(0xFF8A5A4A)

private val FocusGuardColors = lightColorScheme(
    primary = Charcoal,
    onPrimary = Color.White,
    secondary = DeepSage,
    background = BoneWhite,
    surface = Panel,
    onBackground = Charcoal,
    onSurface = Charcoal,
    outline = Line,
    error = MutedRust
)

@Composable
fun FocusGuardTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = FocusGuardColors,
        typography = FocusGuardTypography,
        content = content
    )
}

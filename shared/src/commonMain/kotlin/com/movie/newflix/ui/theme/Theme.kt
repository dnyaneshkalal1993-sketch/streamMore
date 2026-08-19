package com.movie.newflix.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val MidnightBlack = Color(0xFF000000)
val PremiumRed = Color(0xFFE53935)
val SurfaceColor = Color(0xFF121212)
val GlassGray = Color(0xFFFFFFFF).copy(alpha = 0.15f)
val GlassBorder = Color.White.copy(alpha = 0.15f)
val TextSecondary = Color(0xFFBDBDBD)

private val DarkColorScheme = darkColorScheme(
    primary = PremiumRed,
    background = MidnightBlack,
    surface = SurfaceColor,
    onPrimary = Color.White,
    onBackground = Color.White,
    onSurface = Color.White
)

private val LightColorScheme = lightColorScheme(
    primary = PremiumRed,
    background = Color.White,
    surface = Color(0xFFF5F5F5),
    onPrimary = Color.White,
    onBackground = Color.Black,
    onSurface = Color.Black
)

@Composable
fun NewFlixTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        content = content
    )
}

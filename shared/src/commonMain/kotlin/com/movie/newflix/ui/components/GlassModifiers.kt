package com.movie.newflix.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.movie.newflix.ui.theme.GlassBorder
import com.movie.newflix.ui.theme. GlassGray

fun Modifier.glassBackground(
    shape: Shape = RoundedCornerShape(12.dp),
    borderWidth: Dp = 1.dp,
    borderColor: Color = GlassBorder,
    backgroundColor: Color = GlassGray
): Modifier = this
    .clip(shape)
    .background(backgroundColor)
    .border(borderWidth, borderColor, shape)

fun Modifier.neonGlow(
    color: Color,
    radius: Dp = 8.dp
): Modifier = this.then(
    Modifier.blur(radius) // Note: This is a simplified glow for commonMain
)

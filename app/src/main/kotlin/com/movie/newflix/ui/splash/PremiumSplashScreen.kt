package com.movie.newflix.ui.splash

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.movie.newflix.R
import kotlinx.coroutines.delay
import kotlin.math.abs
import kotlin.random.Random
import kotlin.time.Duration.Companion.milliseconds

@Composable
fun PremiumSplashScreen(
    onAnimationFinished: () -> Unit,
    onComposed: () -> Unit
) {
    val progress = remember { Animatable(0f) }
    val infiniteTransition = rememberInfiniteTransition()
    
    // Signal readiness immediately on composition
    SideEffect {
        onComposed()
    }

    val particleProgress by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(12000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        )
    )

    LaunchedEffect(Unit) {
        progress.animateTo(
            targetValue = 1f,
            animationSpec = tween(3000, easing = LinearEasing)
        )
        delay(200.milliseconds)
        onAnimationFinished()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF010101)), // Deep near-black
        contentAlignment = Alignment.Center
    ) {
        val p = progress.value

        // Scene 4: Background Poster Collage (Reveal 1.8 - 2.5s -> p: 0.6 - 0.83)
        if (p > 0.6f) {
            val collageAlpha = ((p - 0.6f) / 0.23f).coerceIn(0f, 0.12f)
            PosterCollage(alpha = collageAlpha)
        }

        // Background Effects
        Canvas(modifier = Modifier.fillMaxSize()) {
            drawVignette()
            drawParticles(particleProgress)
            
            // Scene 1: Central Red Glow - START PARTIALLY VISIBLE
            val glowAlpha = (0.2f + p * 5f).coerceIn(0f, 1f)
            drawRadialGlow(glowAlpha)

            // Scene 4: Bottom Red Light
            if (p > 0.6f) {
                val bottomGlowAlpha = ((p - 0.6f) / 0.23f).coerceIn(0f, 1f)
                drawBottomGlow(bottomGlowAlpha)
            }
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Scene 2: Logo Reveal (Starts much earlier -> p: 0.05)
            if (p > 0.05f) {
                val logoProgress = ((p - 0.05f) / 0.35f).coerceIn(0f, 1f)
                val logoAlpha = logoProgress
                val logoScale = 0.95f + (logoProgress * 0.05f)
                
                Box(contentAlignment = Alignment.Center) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_streammore_logo),
                        contentDescription = null,
                        modifier = Modifier
                            .size(140.dp)
                            .scale(logoScale)
                            .alpha(logoAlpha)
                    )
                    
                    // Light Sweep
                    if (logoProgress in 0.1f..0.9f) {
                        val sweepP = (logoProgress - 0.1f) / 0.8f
                        Canvas(modifier = Modifier.size(140.dp)) {
                            drawRect(
                                brush = Brush.linearGradient(
                                    colors = listOf(Color.Transparent, Color.White.copy(0.3f), Color.Transparent),
                                    start = Offset(size.width * sweepP * 2f - size.width, 0f),
                                    end = Offset(size.width * sweepP * 2f, size.height)
                                ),
                                blendMode = BlendMode.Screen
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Scene 3: App Name and Tagline
            if (p > 0.35f) {
                val nameProgress = ((p - 0.35f) / 0.25f).coerceIn(0f, 1f)
                val nameOffsetY = (12 * (1f - nameProgress)).dp
                
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .offset { IntOffset(0, nameOffsetY.roundToPx()) }
                        .alpha(nameProgress)
                ) {
                    Text(
                        text = "StreamMore",
                        color = Color.White,
                        fontSize = 48.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 2.sp
                    )
                    
                    Spacer(modifier = Modifier.height(8.dp))
                    
                    if (p > 0.42f) {
                        val tagProgress = ((p - 0.42f) / 0.18f).coerceIn(0f, 1f)
                        val tagOffsetY = (8 * (1f - tagProgress)).dp
                        Text(
                            text = "Unlimited entertainment,\none destination.",
                            color = Color(0xFFBDBDBD),
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium,
                            modifier = Modifier
                                .offset { IntOffset(0, tagOffsetY.roundToPx()) }
                                .alpha(tagProgress),
                            lineHeight = 20.sp,
                            textAlign = androidx.compose.ui.text.style.TextAlign.Center
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun PosterCollage(alpha: Float) {
    Box(modifier = Modifier.fillMaxSize().alpha(alpha)) {
        val columns = 4
        val rows = 4
        val random = Random(123)
        
        Canvas(modifier = Modifier.fillMaxSize()) {
            val cellWidth = size.width / columns
            val cellHeight = size.height / rows
            
            for (r in 0 until rows) {
                for (c in 0 until columns) {
                    val rotation = random.nextInt(-10, 11).toFloat()
                    val x = c * cellWidth + random.nextInt(10, 40)
                    val y = r * cellHeight + random.nextInt(10, 40)
                    val w = cellWidth * 0.8f
                    val h = cellHeight * 1.2f
                    
                    rotate(rotation, Offset(x + w/2, y + h/2)) {
                        drawRect(
                            color = Color.Gray.copy(alpha = 0.5f),
                            topLeft = Offset(x, y),
                            size = Size(w, h)
                        )
                        drawRect(
                            color = Color.White.copy(alpha = 0.1f),
                            topLeft = Offset(x, y),
                            size = Size(w, h),
                            style = androidx.compose.ui.graphics.drawscope.Stroke(width = 1.dp.toPx())
                        )
                    }
                }
            }
        }
        
        Box(modifier = Modifier.fillMaxSize().background(
            Brush.verticalGradient(listOf(Color.Black, Color.Transparent, Color.Black))
        ))
        Box(modifier = Modifier.fillMaxSize().background(Color.Black.copy(0.4f)))
    }
}

private fun DrawScope.drawVignette() {
    drawRect(
        brush = Brush.radialGradient(
            colors = listOf(Color.Transparent, Color.Black),
            center = center,
            radius = size.width
        )
    )
}

private fun DrawScope.drawRadialGlow(alpha: Float) {
    drawCircle(
        brush = Brush.radialGradient(
            colors = listOf(Color(0xFFE53935).copy(alpha = 0.25f * alpha), Color.Transparent),
            center = center,
            radius = size.width * 0.7f
        )
    )
}

private fun DrawScope.drawBottomGlow(alpha: Float) {
    val glowWidth = size.width * 0.85f
    drawRect(
        brush = Brush.horizontalGradient(
            colors = listOf(Color.Transparent, Color(0xFFFF4B4B).copy(alpha = 0.45f * alpha), Color.Transparent),
            startX = center.x - glowWidth / 2,
            endX = center.x + glowWidth / 2
        ),
        topLeft = Offset(0f, size.height - 3.dp.toPx()),
        size = Size(size.width, 3.dp.toPx())
    )
}

private fun DrawScope.drawParticles(particleProgress: Float) {
    val random = Random(42)
    for (i in 0 until 30) {
        val startX = random.nextFloat() * size.width
        val startY = random.nextFloat() * size.height
        val t = (particleProgress + random.nextFloat()) % 1f
        val x = startX + (t * 60 * (if (i % 2 == 0) 1 else -1))
        val y = startY - (t * 120)
        
        // BASELINE ALPHA 0.05
        val pAlpha = 0.05f + (0.1f * (1f - abs(t - 0.5f) * 2))
        drawCircle(
            color = Color.White,
            radius = 1.dp.toPx(),
            center = Offset(x % size.width, (y + size.height) % size.height),
            alpha = pAlpha.coerceIn(0f, 1f)
        )
    }
}

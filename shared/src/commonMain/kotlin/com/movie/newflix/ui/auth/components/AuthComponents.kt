package com.movie.newflix.ui.auth.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.movie.newflix.ui.theme.GlassBorder
import com.movie.newflix.ui.theme.GlassGray
import com.movie.newflix.ui.theme.PremiumRed
import com.movie.newflix.ui.theme.TextSecondary
import kotlin.random.Random
import kotlin.math.abs

@Composable
fun CinematicBackground(content: @Composable BoxScope.() -> Unit) {
    val infiniteTransition = rememberInfiniteTransition()
    
    // Glow animation
    val glowAlpha by infiniteTransition.animateFloat(
        initialValue = 0.1f,
        targetValue = 0.3f,
        animationSpec = infiniteRepeatable(
            animation = tween(4000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        )
    )

    // Particle animation progress
    val particleProgress by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(15000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        )
    )

    val particles = remember { List(25) { Particle() } }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        // Optimized Single Canvas for all background effects
        Canvas(modifier = Modifier.fillMaxSize()) {
            // 1. Subtle Red Ambient Glow
            drawCircle(
                brush = Brush.radialGradient(
                    colors = listOf(PremiumRed.copy(alpha = 0.15f * glowAlpha), Color.Transparent),
                    center = center,
                    radius = size.width * 0.9f
                )
            )

            // 2. Cinematic Particles
            particles.forEach { particle ->
                val t = (particleProgress + particle.randomOffset) % 1f
                val x = (particle.startX + (particle.endX - particle.startX) * t) * size.width
                val y = (particle.startY + (particle.endY - particle.startY) * t) * size.height
                
                // Fade particles at edges of their path
                val pAlpha = (0.05f + Random(particle.hashCode()).nextFloat() * 0.1f) * 
                             (1f - (abs(t - 0.5f) * 2f))

                drawCircle(
                    color = Color.White,
                    radius = particle.size.dp.toPx(),
                    center = Offset(x, y),
                    alpha = pAlpha.coerceIn(0f, 1f)
                )
            }
        }

        content()
    }
}

class Particle {
    val startX = Random.nextFloat()
    val startY = Random.nextFloat()
    val endX = Random.nextFloat()
    val endY = Random.nextFloat()
    val size = Random.nextFloat() * 1.5f + 0.5f
    val randomOffset = Random.nextFloat()
}

@Composable
fun GlassmorphismCard(
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(24.dp))
    ) {
        // Background blur layer (Separate to avoid blurring children)
        Box(
            modifier = Modifier
                .matchParentSize()
                .background(GlassGray)
                .blur(24.dp)
                .border(1.dp, GlassBorder, RoundedCornerShape(24.dp))
        )
        
        Column(
            modifier = Modifier.padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            content()
        }
    }
}

@Composable
fun PremiumTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    leadingIcon: ImageVector,
    isPassword: Boolean = false,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default
) {
    var passwordVisible by remember { mutableStateOf(false) }
    var isFocused by remember { mutableStateOf(false) }

    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = Modifier
            .fillMaxWidth()
            .drawWithContent {
                drawContent()
                if (isFocused) {
                    drawRect(
                        brush = Brush.radialGradient(
                            colors = listOf(PremiumRed.copy(alpha = 0.1f), Color.Transparent),
                            center = Offset(size.width / 2, size.height),
                            radius = size.width / 2
                        )
                    )
                }
            },
        label = { Text(label, color = TextSecondary) },
        leadingIcon = { Icon(leadingIcon, contentDescription = null, tint = Color.White.copy(0.6f)) },
        trailingIcon = {
            if (isPassword) {
                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(
                        imageVector = if (passwordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                        contentDescription = null,
                        tint = Color.White.copy(0.6f)
                    )
                }
            }
        },
        visualTransformation = if (isPassword && !passwordVisible) PasswordVisualTransformation() else VisualTransformation.None,
        keyboardOptions = keyboardOptions,
        shape = RoundedCornerShape(50),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = PremiumRed,
            unfocusedBorderColor = Color.White.copy(alpha = 0.15f),
            focusedTextColor = Color.White,
            unfocusedTextColor = Color.White
        ),
        singleLine = true
    )
}

@Composable
fun PremiumButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    isLoading: Boolean = false
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp),
        shape = RoundedCornerShape(50),
        colors = ButtonDefaults.buttonColors(containerColor = PremiumRed),
        enabled = !isLoading
    ) {
        if (isLoading) {
            CircularProgressIndicator(color = Color.White, modifier = Modifier.size(24.dp), strokeWidth = 2.dp)
        } else {
            Text(text, fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color.White)
        }
    }
}

@Composable
fun SocialAuthButtons() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        SocialButton("G")
        Spacer(modifier = Modifier.width(16.dp))
        SocialButton("A")
        Spacer(modifier = Modifier.width(16.dp))
        SocialButton("F")
    }
}

@Composable
private fun SocialButton(label: String) {
    Box(
        modifier = Modifier
            .size(56.dp)
            .clip(CircleShape)
            .background(Color.White.copy(alpha = 0.1f))
            .clickable { },
        contentAlignment = Alignment.Center
    ) {
        Text(label, color = Color.White, fontWeight = FontWeight.Bold)
    }
}

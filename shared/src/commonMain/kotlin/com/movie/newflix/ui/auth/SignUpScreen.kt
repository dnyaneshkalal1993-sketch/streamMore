package com.movie.newflix.ui.auth

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.movie.newflix.ui.auth.components.CinematicBackground
import com.movie.newflix.ui.auth.components.PremiumButton
import com.movie.newflix.ui.auth.components.PremiumTextField
import com.movie.newflix.ui.theme.PremiumRed
import com.movie.newflix.ui.theme.TextSecondary

@Composable
fun SignUpScreen(
    onSignUpSuccess: () -> Unit,
    onNavigateToLogin: () -> Unit
) {
    var fullName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var agreeToTerms by remember { mutableStateOf(false) }
    var isLoading by remember { mutableStateOf(false) }

    CinematicBackground {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(48.dp))

            Text(
                text = "Create Your Account",
                style = MaterialTheme.typography.headlineMedium,
                color = Color.White,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = "Start streaming unlimited entertainment.",
                style = MaterialTheme.typography.bodyMedium,
                color = TextSecondary,
                modifier = Modifier.padding(top = 8.dp)
            )

            Spacer(modifier = Modifier.height(48.dp))

            PremiumTextField(
                value = fullName,
                onValueChange = { fullName = it },
                label = "Full Name",
                leadingIcon = Icons.Default.Person
            )

            Spacer(modifier = Modifier.height(16.dp))

            PremiumTextField(
                value = email,
                onValueChange = { email = it },
                label = "Email Address",
                leadingIcon = Icons.Default.Email
            )

            Spacer(modifier = Modifier.height(16.dp))

            PremiumTextField(
                value = password,
                onValueChange = { password = it },
                label = "Password",
                leadingIcon = Icons.Default.Lock,
                isPassword = true
            )

            // Password Strength Indicator
            PasswordStrengthBar(password)

            Spacer(modifier = Modifier.height(16.dp))

            PremiumTextField(
                value = confirmPassword,
                onValueChange = { confirmPassword = it },
                label = "Confirm Password",
                leadingIcon = Icons.Default.Lock,
                isPassword = true
            )

            Spacer(modifier = Modifier.height(24.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Checkbox(
                    checked = agreeToTerms,
                    onCheckedChange = { agreeToTerms = it },
                    colors = CheckboxDefaults.colors(
                        checkedColor = PremiumRed,
                        uncheckedColor = Color.White.copy(0.3f),
                        checkmarkColor = Color.White
                    )
                )
                Text(
                    text = "I agree to the Terms & Privacy Policy",
                    color = TextSecondary,
                    fontSize = 14.sp
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            PremiumButton(
                text = "Create Account",
                onClick = {
                    isLoading = true
                    onSignUpSuccess()
                },
                isLoading = isLoading
            )

            Spacer(modifier = Modifier.height(48.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("Already have an account?", color = TextSecondary)
                TextButton(onClick = onNavigateToLogin) {
                    Text("Sign In", color = PremiumRed, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

@Composable
private fun PasswordStrengthBar(password: String) {
    val strength = when {
        password.isEmpty() -> 0f
        password.length < 6 -> 0.3f
        password.length < 10 -> 0.6f
        else -> 1f
    }
    
    val color by animateColorAsState(
        when {
            strength < 0.4f -> Color.Red
            strength < 0.7f -> Color.Yellow
            else -> Color.Green
        }
    )
    
    val widthProgress by animateFloatAsState(targetValue = strength)

    Column(modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 8.dp)) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(4.dp)
                .background(Color.White.copy(alpha = 0.1f), RoundedCornerShape(2.dp))
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(widthProgress)
                    .fillMaxHeight()
                    .background(color, RoundedCornerShape(2.dp))
            )
        }
        Text(
            text = when {
                strength < 0.4f && password.isNotEmpty() -> "Weak"
                strength < 0.7f -> "Medium"
                strength >= 0.7f -> "Strong"
                else -> ""
            },
            color = color,
            fontSize = 12.sp,
            modifier = Modifier.padding(top = 4.dp)
        )
    }
}

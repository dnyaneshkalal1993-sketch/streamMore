package com.movie.newflix.ui.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.movie.newflix.ui.auth.components.*
import com.movie.newflix.ui.theme.PremiumRed
import com.movie.newflix.ui.theme.TextSecondary

@Composable
fun LoginScreen(
    onLoginSuccess: () -> Unit,
    onNavigateToSignUp: () -> Unit
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }

    CinematicBackground {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Logo / Title
            Text(
                text = "NEWFLIX",
                color = PremiumRed,
                fontSize = 42.sp,
                fontWeight = FontWeight.Black,
                letterSpacing = 4.sp
            )
            
            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = "Welcome Back",
                style = MaterialTheme.typography.headlineMedium,
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
            
            Text(
                text = "Continue watching where you left off.",
                style = MaterialTheme.typography.bodyMedium,
                color = TextSecondary,
                modifier = Modifier.padding(top = 8.dp)
            )

            Spacer(modifier = Modifier.height(48.dp))

            // Auth Card
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
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

                Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.CenterEnd) {
                    TextButton(onClick = { /* Forgot Password */ }) {
                        Text("Forgot Password?", color = TextSecondary, fontSize = 14.sp)
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                PremiumButton(
                    text = "Sign In",
                    onClick = {
                        isLoading = true
                        // Simulated Login
                        onLoginSuccess()
                    },
                    isLoading = isLoading
                )

                Spacer(modifier = Modifier.height(32.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(modifier = Modifier.weight(1f).height(1.dp).background(Color.White.copy(0.1f)))
                    Text("  OR  ", color = Color.White.copy(0.3f), fontSize = 12.sp)
                    Box(modifier = Modifier.weight(1f).height(1.dp).background(Color.White.copy(0.1f)))
                }

                Spacer(modifier = Modifier.height(32.dp))

                SocialAuthButtons()

                Spacer(modifier = Modifier.height(48.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("Don't have an account?", color = TextSecondary)
                    TextButton(onClick = onNavigateToSignUp) {
                        Text("Create Account", color = PremiumRed, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}

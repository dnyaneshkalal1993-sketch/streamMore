package com.movie.newflix

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.movie.newflix.MainView
import com.movie.newflix.ui.splash.PremiumSplashScreen
import com.movie.newflix.ui.theme.NewFlixTheme
import com.movie.newflix.ui.theme.MidnightBlack

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        // Install system splash screen
        val splashScreen = installSplashScreen()
        
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        
        setContent {
            var isSplashFinished by remember { mutableStateOf(false) }
            var isComposeReady by remember { mutableStateOf(false) }

            // Keep system splash ONLY until Compose is ready to take over
            splashScreen.setKeepOnScreenCondition { !isComposeReady }

            NewFlixTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MidnightBlack
                ) {
                    if (!isSplashFinished) {
                        PremiumSplashScreen(
                            onAnimationFinished = {
                                isSplashFinished = true
                            },
                            onComposed = {
                                isComposeReady = true
                            }
                        )
                    } else {
                        MainView()
                    }
                }
            }
        }
    }
}

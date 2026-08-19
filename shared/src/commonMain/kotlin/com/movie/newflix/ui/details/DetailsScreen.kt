package com.movie.newflix.ui.details

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.movie.newflix.ui.components.glassBackground
import com.movie.newflix.ui.theme.PremiumRed

@Composable
fun DetailsScreen(
    viewModel: DetailsViewModel,
    onBackClick: () -> Unit
) {
    val state by viewModel.state.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        if (state.isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center), color = PremiumRed)
        } else if (state.error != null) {
            Column(
                modifier = Modifier.align(Alignment.Center),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("Error: ${state.error}", color = Color.White)
                Button(onClick = { viewModel.handleIntent(DetailsIntent.Retry) }) {
                    Text("Retry")
                }
            }
        } else {
            state.movie?.let { movie ->
                // Full Screen Backdrop
                AsyncImage(
                    model = movie.posterPath,
                    contentDescription = null,
                    modifier = Modifier.fillMaxWidth().height(600.dp),
                    contentScale = ContentScale.Crop
                )

                // Bottom Content with Glass Panel
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                ) {
                    Spacer(modifier = Modifier.height(450.dp))
                    
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .glassBackground(
                                shape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp),
                                borderColor = Color.White.copy(0.1f)
                            )
                            .padding(24.dp)
                    ) {
                        Text(
                            text = movie.title,
                            style = MaterialTheme.typography.headlineMedium,
                            color = Color.White,
                            fontWeight = FontWeight.Black
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.Star, contentDescription = null, tint = Color(0xFFFFD700), modifier = Modifier.size(20.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = movie.voteAverage.toString().take(3),
                                color = Color.White,
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = Modifier.width(20.dp))
                            Text(
                                text = movie.releaseDate.take(4),
                                color = Color.White.copy(0.6f),
                                style = MaterialTheme.typography.bodyLarge
                            )
                        }

                        Spacer(modifier = Modifier.height(32.dp))

                        Button(
                            onClick = { /* TODO: Play */ },
                            modifier = Modifier.fillMaxWidth().height(56.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Icon(Icons.Default.PlayArrow, contentDescription = null, tint = Color.Black)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("WATCH NOW", color = Color.Black, fontWeight = FontWeight.Black)
                        }

                        Spacer(modifier = Modifier.height(32.dp))

                        Text(
                            text = "STORYLINE",
                            style = MaterialTheme.typography.labelLarge,
                            color = Color.White.copy(0.5f),
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 2.sp
                        )
                        
                        Spacer(modifier = Modifier.height(12.dp))

                        Text(
                            text = movie.overview,
                            style = MaterialTheme.typography.bodyLarge,
                            color = Color.White.copy(0.8f),
                            lineHeight = 28.sp
                        )

                        Spacer(modifier = Modifier.height(100.dp)) // Extra space for floating nav
                    }
                }
            }
        }

        // Action Buttons Overlay
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .statusBarsPadding()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            IconButton(
                onClick = onBackClick,
                modifier = Modifier.glassBackground(shape = CircleShape)
            ) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = Color.White)
            }

            IconButton(
                onClick = { viewModel.handleIntent(DetailsIntent.ToggleFavorite) },
                modifier = Modifier.glassBackground(shape = CircleShape)
            ) {
                Icon(
                    imageVector = if (state.isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                    contentDescription = "Favorite",
                    tint = if (state.isFavorite) PremiumRed else Color.White
                )
            }
        }
    }
}

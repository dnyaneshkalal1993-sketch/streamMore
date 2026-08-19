package com.movie.newflix.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.movie.newflix.domain.model.Movie
import com.movie.newflix.ui.components.MovieCard
import com.movie.newflix.ui.components.glassBackground

@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    onMovieClick: (Movie) -> Unit
) {
    val state by viewModel.state.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF080808)) // Deep Midnight
    ) {
        if (state.isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center), 
                color = Color(0xFFFF1A1A)
            )
        } else if (state.error != null) {
            Text(
                text = "Error: ${state.error}",
                modifier = Modifier.align(Alignment.Center),
                color = MaterialTheme.colorScheme.error
            )
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(40.dp),
                contentPadding = PaddingValues(bottom = 100.dp)
            ) {
                item {
                    state.trendingMovies.firstOrNull()?.let { featured ->
                        FeaturedMovie(
                            movie = featured,
                            onPlayClick = { onMovieClick(featured) }
                        )
                    }
                }
                
                item {
                    MovieSection("TRENDING NOW", state.trendingMovies, onMovieClick)
                }
                item {
                    MovieSection("POPULAR ON CINE SPHERE", state.popularMovies, onMovieClick)
                }
                item {
                    MovieSection("TOP RATED", state.topRatedMovies, onMovieClick)
                }
                item {
                    MovieSection("UPCOMING RELEASES", state.upcomingMovies, onMovieClick)
                }
            }
            
            // Floating Glass Top Bar
            HomeTopBar(
                modifier = Modifier
                    .fillMaxWidth()
                    .statusBarsPadding()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            )
        }
    }
}

@Composable
fun HomeTopBar(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .glassBackground(borderColor = Color.White.copy(0.1f))
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = "STREAMMORE",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Black,
            color = Color(0xFFE53935)
        )
        
        Row(horizontalArrangement = Arrangement.spacedBy(20.dp)) {
             Text("TV Shows", color = Color.White, style = MaterialTheme.typography.labelLarge)
             Text("Movies", color = Color.White, style = MaterialTheme.typography.labelLarge)
        }
    }
}

@Composable
fun MovieSection(
    title: String,
    movies: List<Movie>,
    onMovieClick: (Movie) -> Unit
) {
    Column {
        Text(
            text = title,
            style = MaterialTheme.typography.labelLarge,
            fontWeight = FontWeight.Bold,
            color = Color.White.copy(alpha = 0.7f),
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
            letterSpacing = 2.sp
        )
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(horizontal = 16.dp)
        ) {
            items(movies) { movie ->
                MovieCard(movie = movie, onClick = { onMovieClick(movie) })
            }
        }
    }
}

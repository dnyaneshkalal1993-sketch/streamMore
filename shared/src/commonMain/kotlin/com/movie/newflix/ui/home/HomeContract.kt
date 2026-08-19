package com.movie.newflix.ui.home

import com.movie.newflix.domain.model.Movie

data class HomeState(
    val isLoading: Boolean = false,
    val trendingMovies: List<Movie> = emptyList(),
    val popularMovies: List<Movie> = emptyList(),
    val topRatedMovies: List<Movie> = emptyList(),
    val upcomingMovies: List<Movie> = emptyList(),
    val nowPlayingMovies: List<Movie> = emptyList(),
    val error: String? = null
)

sealed interface HomeIntent {
    data object LoadHomeData : HomeIntent
    data class MovieClicked(val movie: Movie) : HomeIntent
}

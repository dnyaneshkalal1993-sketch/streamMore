package com.movie.newflix.domain.repository

import com.movie.newflix.domain.model.Movie
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    fun getTrendingMovies(): Flow<List<Movie>>
    fun getPopularMovies(): Flow<List<Movie>>
    fun getTopRatedMovies(): Flow<List<Movie>>
    fun getUpcomingMovies(): Flow<List<Movie>>
    fun getNowPlayingMovies(): Flow<List<Movie>>
    
    suspend fun getMovieDetails(movieId: Int): Movie
    suspend fun searchMovies(query: String): List<Movie>
    
    fun getFavoriteMovies(): Flow<List<Movie>>
    suspend fun toggleFavorite(movie: Movie)
    suspend fun isFavorite(movieId: Int): Boolean
}

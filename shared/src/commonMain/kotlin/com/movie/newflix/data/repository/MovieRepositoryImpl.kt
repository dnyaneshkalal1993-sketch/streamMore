package com.movie.newflix.data.repository

import com.movie.newflix.data.local.MovieDao
import com.movie.newflix.data.mapper.toDomain
import com.movie.newflix.data.mapper.toEntity
import com.movie.newflix.data.remote.TmdbApiService
import com.movie.newflix.domain.model.Movie
import com.movie.newflix.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class MovieRepositoryImpl(
    private val apiService: TmdbApiService,
    private val movieDao: MovieDao
) : MovieRepository {

    override fun getTrendingMovies(): Flow<List<Movie>> = flow {
        val response = apiService.getTrendingMovies()
        emit(response.results.map { it.toDomain() })
    }

    override fun getPopularMovies(): Flow<List<Movie>> = flow {
        val response = apiService.getPopularMovies()
        emit(response.results.map { it.toDomain() })
    }

    override fun getTopRatedMovies(): Flow<List<Movie>> = flow {
        val response = apiService.getTopRatedMovies()
        emit(response.results.map { it.toDomain() })
    }

    override fun getUpcomingMovies(): Flow<List<Movie>> = flow {
        val response = apiService.getUpcomingMovies()
        emit(response.results.map { it.toDomain() })
    }

    override fun getNowPlayingMovies(): Flow<List<Movie>> = flow {
        val response = apiService.getNowPlayingMovies()
        emit(response.results.map { it.toDomain() })
    }

    override suspend fun getMovieDetails(movieId: Int): Movie {
        return apiService.getMovieDetails(movieId).toDomain()
    }

    override suspend fun searchMovies(query: String): List<Movie> {
        return apiService.searchMovies(query).results.map { it.toDomain() }
    }

    override fun getFavoriteMovies(): Flow<List<Movie>> {
        return movieDao.getAllMovies().map { entities -> entities.map { it.toDomain() } }
    }

    override suspend fun toggleFavorite(movie: Movie) {
        if (movieDao.getMovieById(movie.id) == null) {
            movieDao.upsertMovie(movie.toEntity())
        } else {
            movieDao.deleteMovie(movie.toEntity())
        }
    }

    override suspend fun isFavorite(movieId: Int): Boolean {
        return movieDao.getMovieById(movieId) != null
    }
}

package com.movie.newflix.data.remote

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter

class TmdbApiService(private val client: HttpClient) {
    suspend fun getTrendingMovies() = client.get("trending/movie/day").body<MovieResponse>()
    suspend fun getPopularMovies() = client.get("movie/popular").body<MovieResponse>()
    suspend fun getTopRatedMovies() = client.get("movie/top_rated").body<MovieResponse>()
    suspend fun getUpcomingMovies() = client.get("movie/upcoming").body<MovieResponse>()
    suspend fun getNowPlayingMovies() = client.get("movie/now_playing").body<MovieResponse>()
    
    suspend fun getMovieDetails(movieId: Int) = client.get("movie/$movieId").body<MovieDto>()
    
    suspend fun searchMovies(query: String) = client.get("search/movie") {
        parameter("query", query)
    }.body<MovieResponse>()
}

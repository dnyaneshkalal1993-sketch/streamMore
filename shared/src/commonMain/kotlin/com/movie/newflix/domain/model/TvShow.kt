package com.movie.newflix.domain.model

data class TvShow(
    val id: Int,
    val name: String,
    val overview: String,
    val posterPath: String?,
    val backdropPath: String?,
    val voteAverage: Double,
    val firstAirDate: String
)

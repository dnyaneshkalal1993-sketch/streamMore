package com.movie.newflix.data.mapper

import com.movie.newflix.data.local.MovieEntity
import com.movie.newflix.data.remote.MovieDto
import com.movie.newflix.domain.model.Movie

fun MovieDto.toDomain(): Movie {
    return Movie(
        id = id,
        title = title,
        overview = overview,
        posterPath = "https://image.tmdb.org/t/p/w500$posterPath",
        backdropPath = "https://image.tmdb.org/t/p/original$backdropPath",
        voteAverage = voteAverage,
        releaseDate = releaseDate ?: ""
    )
}

fun Movie.toEntity(): MovieEntity {
    return MovieEntity(
        id = id,
        title = title,
        overview = overview,
        posterPath = posterPath,
        backdropPath = backdropPath,
        voteAverage = voteAverage,
        releaseDate = releaseDate
    )
}

fun MovieEntity.toDomain(): Movie {
    return Movie(
        id = id,
        title = title,
        overview = overview,
        posterPath = posterPath,
        backdropPath = backdropPath,
        voteAverage = voteAverage,
        releaseDate = releaseDate
    )
}

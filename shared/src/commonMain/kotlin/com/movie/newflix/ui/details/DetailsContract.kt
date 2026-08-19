package com.movie.newflix.ui.details

import com.movie.newflix.domain.model.Movie

data class DetailsState(
    val isLoading: Boolean = false,
    val movie: Movie? = null,
    val isFavorite: Boolean = false,
    val error: String? = null
)

sealed interface DetailsIntent {
    data object ToggleFavorite : DetailsIntent
    data object Retry : DetailsIntent
}

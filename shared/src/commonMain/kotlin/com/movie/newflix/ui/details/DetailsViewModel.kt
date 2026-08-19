package com.movie.newflix.ui.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.movie.newflix.domain.repository.MovieRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class DetailsViewModel(
    private val movieId: Int,
    private val repository: MovieRepository
) : ViewModel() {

    private val _state = MutableStateFlow(DetailsState())
    val state = _state.asStateFlow()

    init {
        loadMovieDetails()
    }

    fun handleIntent(intent: DetailsIntent) {
        when (intent) {
            DetailsIntent.ToggleFavorite -> toggleFavorite()
            DetailsIntent.Retry -> loadMovieDetails()
        }
    }

    private fun loadMovieDetails() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }
            try {
                val movie = repository.getMovieDetails(movieId)
                val isFavorite = repository.isFavorite(movieId)
                _state.update { 
                    it.copy(
                        isLoading = false,
                        movie = movie,
                        isFavorite = isFavorite
                    ) 
                }
            } catch (e: Exception) {
                _state.update { it.copy(isLoading = false, error = e.message) }
            }
        }
    }

    private fun toggleFavorite() {
        val movie = _state.value.movie ?: return
        viewModelScope.launch {
            repository.toggleFavorite(movie)
            val isFavorite = repository.isFavorite(movieId)
            _state.update { it.copy(isFavorite = isFavorite) }
        }
    }
}

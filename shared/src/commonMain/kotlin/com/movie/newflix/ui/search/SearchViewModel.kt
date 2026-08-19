package com.movie.newflix.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.movie.newflix.domain.model.Movie
import com.movie.newflix.domain.repository.MovieRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class SearchState(
    val query: String = "",
    val results: List<Movie> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)

class SearchViewModel(
    private val repository: MovieRepository
) : ViewModel() {

    private val _state = MutableStateFlow(SearchState())
    val state = _state.asStateFlow()

    fun onQueryChange(newQuery: String) {
        _state.update { it.copy(query = newQuery) }
        if (newQuery.length > 2) {
            searchMovies(newQuery)
        } else {
            _state.update { it.copy(results = emptyList()) }
        }
    }

    private fun searchMovies(query: String) {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            try {
                val results = repository.searchMovies(query)
                _state.update { it.copy(isLoading = false, results = results) }
            } catch (e: Exception) {
                _state.update { it.copy(isLoading = false, error = e.message) }
            }
        }
    }
}

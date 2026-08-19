package com.movie.newflix.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.movie.newflix.domain.repository.MovieRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel(
    private val repository: MovieRepository
) : ViewModel() {

    private val _state = MutableStateFlow(HomeState())
    val state = _state.asStateFlow()

    init {
        handleIntent(HomeIntent.LoadHomeData)
    }

    fun handleIntent(intent: HomeIntent) {
        when (intent) {
            HomeIntent.LoadHomeData -> loadHomeData()
            is HomeIntent.MovieClicked -> { /* Navigate to details */ }
        }
    }

    private fun loadHomeData() {
        viewModelScope.launch {
            combine(
                repository.getTrendingMovies(),
                repository.getPopularMovies(),
                repository.getTopRatedMovies(),
                repository.getUpcomingMovies(),
                repository.getNowPlayingMovies()
            ) { trending, popular, topRated, upcoming, nowPlaying ->
                HomeState(
                    trendingMovies = trending,
                    popularMovies = popular,
                    topRatedMovies = topRated,
                    upcomingMovies = upcoming,
                    nowPlayingMovies = nowPlaying,
                    isLoading = false
                )
            }.onStart {
                _state.update { it.copy(isLoading = true) }
            }.catch { e ->
                _state.update { it.copy(isLoading = false, error = e.message) }
            }.collect { newState ->
                _state.value = newState
            }
        }
    }
}

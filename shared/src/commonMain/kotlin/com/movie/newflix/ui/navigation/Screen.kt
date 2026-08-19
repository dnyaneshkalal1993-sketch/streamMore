package com.movie.newflix.ui.navigation

import kotlinx.serialization.Serializable

sealed interface Screen {
    @Serializable
    data object Login : Screen

    @Serializable
    data object SignUp : Screen

    @Serializable
    data object Home : Screen

    @Serializable
    data class Details(val movieId: Int) : Screen

    @Serializable
    data object Search : Screen

    @Serializable
    data object Favorites : Screen
}

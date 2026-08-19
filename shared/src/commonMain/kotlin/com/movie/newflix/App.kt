package com.movie.newflix

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.*
import androidx.navigation.toRoute
import com.movie.newflix.ui.components.glassBackground
import com.movie.newflix.ui.auth.LoginScreen
import com.movie.newflix.ui.auth.SignUpScreen
import com.movie.newflix.ui.details.DetailsScreen
import com.movie.newflix.ui.details.DetailsViewModel
import com.movie.newflix.ui.favorites.FavoritesScreen
import com.movie.newflix.ui.favorites.FavoritesViewModel
import com.movie.newflix.ui.home.HomeScreen
import com.movie.newflix.ui.home.HomeViewModel
import com.movie.newflix.ui.navigation.Screen
import com.movie.newflix.ui.search.SearchScreen
import com.movie.newflix.ui.search.SearchViewModel
import com.movie.newflix.ui.theme.NewFlixTheme
import com.movie.newflix.ui.theme.PremiumRed
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun App() {
    NewFlixTheme {
        val navController = rememberNavController()
        
        Box(modifier = Modifier.fillMaxSize().background(com.movie.newflix.ui.theme.MidnightBlack)) {
            NavHost(
                navController = navController,
                startDestination = Screen.Login,
                modifier = Modifier.fillMaxSize()
            ) {
                composable<Screen.Login> {
                    LoginScreen(
                        onLoginSuccess = {
                            navController.navigate(Screen.Home) {
                                popUpTo(Screen.Login) { inclusive = true }
                            }
                        },
                        onNavigateToSignUp = {
                            navController.navigate(Screen.SignUp)
                        }
                    )
                }

                composable<Screen.SignUp> {
                    SignUpScreen(
                        onSignUpSuccess = {
                            navController.navigate(Screen.Home) {
                                popUpTo(Screen.Login) { inclusive = true }
                            }
                        },
                        onNavigateToLogin = {
                            navController.popBackStack()
                        }
                    )
                }

                composable<Screen.Home> {
                    HomeScreen(
                        viewModel = koinViewModel(),
                        onMovieClick = { movie ->
                            navController.navigate(Screen.Details(movie.id))
                        }
                    )
                }
                
                composable<Screen.Details> { backStackEntry ->
                    val route: Screen.Details = backStackEntry.toRoute()
                    val viewModel: DetailsViewModel = koinViewModel { parametersOf(route.movieId) }
                    DetailsScreen(
                        viewModel = viewModel,
                        onBackClick = { navController.popBackStack() }
                    )
                }
                
                composable<Screen.Search> {
                    SearchScreen(
                        viewModel = koinViewModel(),
                        onMovieClick = { movie ->
                            navController.navigate(Screen.Details(movie.id))
                        }
                    )
                }
                
                composable<Screen.Favorites> {
                    FavoritesScreen(
                        viewModel = koinViewModel(),
                        onMovieClick = { movie ->
                            navController.navigate(Screen.Details(movie.id))
                        }
                    )
                }
            }

            // Floating Glass Bottom Navigation
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentDestination = navBackStackEntry?.destination
            val showBottomNav = currentDestination?.hierarchy?.any { 
                it.hasRoute(Screen.Home::class) || 
                it.hasRoute(Screen.Search::class) || 
                it.hasRoute(Screen.Favorites::class) 
            } == true

            if (showBottomNav) {
                Box(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(horizontal = 24.dp, vertical = 32.dp)
                        .fillMaxWidth()
                        .height(64.dp)
                        .glassBackground(shape = CircleShape, borderColor = Color.White.copy(0.1f))
                ) {
                    Row(
                        modifier = Modifier.fillMaxSize(),
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        val items = listOf(
                            Triple("Home", Screen.Home, Icons.Default.Home),
                            Triple("Search", Screen.Search, Icons.Default.Search),
                            Triple("My List", Screen.Favorites, Icons.Default.Favorite)
                        )
                        
                        items.forEach { (label, screen, icon) ->
                            val isSelected = currentDestination?.hierarchy?.any { 
                                it.hasRoute(screen::class) 
                            } == true
                            
                            IconButton(
                                onClick = {
                                    println("App: BottomNav Clicked - $label")
                                    if (!isSelected) {
                                        navController.navigate(screen) {
                                            popUpTo<Screen.Home> { saveState = true }
                                            launchSingleTop = true
                                            restoreState = true
                                        }
                                    }
                                }
                            ) {
                                Icon(
                                    imageVector = icon,
                                    contentDescription = label,
                                    tint = if (isSelected) PremiumRed else Color.White.copy(alpha = 0.5f),
                                    modifier = Modifier.size(28.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

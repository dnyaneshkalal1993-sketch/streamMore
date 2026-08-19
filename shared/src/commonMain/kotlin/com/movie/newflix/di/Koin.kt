package com.movie.newflix.di

import com.movie.newflix.data.local.MovieDatabase
import com.movie.newflix.data.local.getDatabaseBuilder
import com.movie.newflix.data.remote.TmdbApiService
import com.movie.newflix.data.remote.createHttpClient
import com.movie.newflix.data.repository.MovieRepositoryImpl
import com.movie.newflix.domain.repository.MovieRepository
import com.movie.newflix.ui.home.HomeViewModel
import com.movie.newflix.ui.details.DetailsViewModel
import com.movie.newflix.ui.search.SearchViewModel
import com.movie.newflix.ui.favorites.FavoritesViewModel
import org.koin.core.context.startKoin
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module

val appModule = module {
    single { createHttpClient() }
    single { TmdbApiService(get()) }
    single<MovieRepository> { MovieRepositoryImpl(get(), get()) }
    
    single { 
        getDatabaseBuilder()
            .fallbackToDestructiveMigration(true)
            .build() 
    }
    single { get<MovieDatabase>().movieDao() }

    viewModel { HomeViewModel(get()) }
    viewModel { params -> DetailsViewModel(movieId = params.get(), repository = get()) }
    viewModel { SearchViewModel(get()) }
    viewModel { FavoritesViewModel(get()) }
}

fun initKoin(appDeclaration: KoinAppDeclaration = {}) =
    startKoin {
        appDeclaration()
        modules(appModule)
    }

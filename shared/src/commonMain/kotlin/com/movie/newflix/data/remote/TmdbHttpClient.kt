package com.movie.newflix.data.remote

import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.header
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

const val TMDB_API_KEY = "0c7f28762bb2e57ef993356e64413ae1"
const val BASE_URL = "https://api.themoviedb.org/3/"

fun createHttpClient(): HttpClient {
    return HttpClient {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
                prettyPrint = true
                isLenient = true
            })
        }
        install(Logging) {
            level = LogLevel.ALL
        }
        defaultRequest {
            url(BASE_URL)
            url.parameters.append("api_key", TMDB_API_KEY)
            header("accept", "application/json")
        }
    }
}

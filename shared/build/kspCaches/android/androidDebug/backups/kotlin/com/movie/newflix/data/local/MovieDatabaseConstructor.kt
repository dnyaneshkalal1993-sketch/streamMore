package com.movie.newflix.`data`.local

import androidx.room.RoomDatabaseConstructor

public actual object MovieDatabaseConstructor : RoomDatabaseConstructor<MovieDatabase> {
  actual override fun initialize(): MovieDatabase =
      com.movie.newflix.`data`.local.MovieDatabase_Impl()
}

package com.movie.newflix.data.local

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import org.koin.core.context.GlobalContext

actual fun getDatabaseBuilder(): RoomDatabase.Builder<MovieDatabase> {
    val context = GlobalContext.get().get<Context>()
    val dbFile = context.getDatabasePath("movie.db")
    return Room.databaseBuilder<MovieDatabase>(
        context = context,
        name = dbFile.absolutePath
    )
}

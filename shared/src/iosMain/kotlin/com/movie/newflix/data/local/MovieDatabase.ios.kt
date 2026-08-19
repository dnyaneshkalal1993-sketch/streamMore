package com.movie.newflix.data.local

import androidx.room.Room
import androidx.room.RoomDatabase
import platform.Foundation.NSHomeDirectory
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSUserDomainMask

actual fun getDatabaseBuilder(): RoomDatabase.Builder<MovieDatabase> {
    val dbFilePath = NSHomeDirectory() + "/movie.db"
    return Room.databaseBuilder<MovieDatabase>(
        name = dbFilePath,
        factory =  { MovieDatabaseConstructor.initialize() }
    )
}

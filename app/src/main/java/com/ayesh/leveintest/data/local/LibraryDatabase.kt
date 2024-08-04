package com.ayesh.leveintest.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [BookEntity::class],
    version = 1,
)
abstract class LibraryDatabase : RoomDatabase() {
    abstract val dao: BookDao
}

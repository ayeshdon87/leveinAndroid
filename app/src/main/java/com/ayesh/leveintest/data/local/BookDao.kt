package com.ayesh.leveintest.data.local

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert

@Dao
interface BookDao {
    @Upsert
    suspend fun upsertAll(beers: List<BookEntity>)

    @Query("SELECT * FROM booksTable")
    fun pagingSource(): PagingSource<Int, BookEntity>

    @Query("DELETE FROM booksTable")
    suspend fun clearAll()
}

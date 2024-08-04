package com.ayesh.leveintest.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "booksTable")
data class BookEntity(
    val name: String,
    val isbn: String,
    @PrimaryKey
    val id: String,
    val authorId: String,
    val authorFirstName: String,
    val authorLastName: String,
)

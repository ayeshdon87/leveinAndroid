package com.ayesh.leveintest.data.models

import com.ayesh.leveintest.domain.models.BookItem

data class BookData(
    val name: String,
    val isbn: String,
    val ID: String,
    val author: AuthorData,
)

fun BookData.toBookItem(): BookItem {
    return BookItem(
        id = ID,
        name = name,
        isbn = isbn,
        authorId = author?.let { it.ID } ?: "",
        authorFirstName = author?.let { it.first_name } ?: "",
        authorLastName = author?.let { it.last_name } ?: "",
    )
}

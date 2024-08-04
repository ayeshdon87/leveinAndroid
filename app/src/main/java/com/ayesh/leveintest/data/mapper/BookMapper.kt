package com.ayesh.leveintest.data.mapper

import com.ayesh.leveintest.data.local.BookEntity
import com.ayesh.leveintest.data.models.BookData
import com.ayesh.leveintest.domain.models.BookItem

fun BookItem.toBookEntity(): BookEntity {
    return BookEntity(
        id = id,
        name = name,
        isbn = isbn,
        authorId = authorId,
        authorFirstName = authorFirstName,
        authorLastName = authorLastName,
    )
}

fun BookEntity.toBookItem(): BookItem {
    return BookItem(
        id = id,
        name = name,
        isbn = isbn,
        authorId = authorId,
        authorFirstName = authorFirstName,
        authorLastName = authorLastName,
    )
}

fun BookData.toBookEntity(): BookEntity {
    return BookEntity(
        id = ID,
        name = name,
        isbn = isbn,
        authorId = author?.let { it.ID } ?: "",
        authorFirstName = author?.let { it.first_name } ?: "",
        authorLastName = author?.let { it.last_name } ?: "",
    )
}

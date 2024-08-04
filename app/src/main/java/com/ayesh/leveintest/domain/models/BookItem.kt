package com.ayesh.leveintest.domain.models

data class BookItem(
    val name: String,
    val isbn: String,
    val id: String,
    val authorId: String,
    val authorFirstName: String,
    val authorLastName: String,
)

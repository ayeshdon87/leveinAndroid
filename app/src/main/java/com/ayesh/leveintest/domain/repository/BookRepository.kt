package com.ayesh.leveintest.domain.repository

import com.ayesh.leveintest.data.models.BookResponse

interface BookRepository {
    suspend fun getBooList(page: Int): BookResponse
}

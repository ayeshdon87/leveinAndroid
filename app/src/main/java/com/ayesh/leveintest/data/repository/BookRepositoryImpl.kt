package com.ayesh.leveintest.data.repository

import com.ayesh.leveintest.data.models.BookResponse
import com.ayesh.leveintest.data.remote.APIClass
import com.ayesh.leveintest.domain.repository.BookRepository
import javax.inject.Inject

class BookRepositoryImpl
    @Inject
    constructor(private var apiClass: APIClass) : BookRepository {
        override suspend fun getBooList(page: Int): BookResponse {
            return apiClass.getBookList(page)
        }
    }

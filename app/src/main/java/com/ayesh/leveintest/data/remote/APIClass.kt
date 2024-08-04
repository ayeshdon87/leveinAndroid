package com.ayesh.leveintest.data.remote

import com.ayesh.leveintest.data.models.AuthorData
import com.ayesh.leveintest.data.models.BookResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface APIClass {
    @GET("v1/authors")
    suspend fun getAuthorList(): List<AuthorData>

    @GET("v1/books/{id}")
    suspend fun getBookList(
        @Path("id") id: Int,
    ): BookResponse
}

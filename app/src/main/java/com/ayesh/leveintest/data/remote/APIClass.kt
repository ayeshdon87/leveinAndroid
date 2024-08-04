package com.ayesh.leveintest.data.remote

import com.ayesh.leveintest.data.models.AddAuthorModel
import com.ayesh.leveintest.data.models.AuthorData
import com.ayesh.leveintest.data.models.BookResponse
import com.ayesh.leveintest.data.models.SuccessResponse
import com.ayesh.leveintest.data.models.UpdateAuthorModel
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface APIClass {
    @GET("v1/authors")
    suspend fun getAuthorList(): List<AuthorData>

    @GET("v1/books/{id}")
    suspend fun getBookList(
        @Path("id") id: Int,
    ): BookResponse

    @POST("v1/author/add")
    suspend fun addAuthor(
        @Body author: AddAuthorModel,
    ): SuccessResponse

    @PUT("v1/author/update")
    suspend fun updateAuthor(
        @Body author: UpdateAuthorModel,
    ): SuccessResponse
}

package com.ayesh.leveintest.data.remote

import com.ayesh.leveintest.data.models.AuthorData
import retrofit2.http.GET

interface APIClass {
    @GET("v1/authors")
    suspend fun getAuthorList(): List<AuthorData>
}
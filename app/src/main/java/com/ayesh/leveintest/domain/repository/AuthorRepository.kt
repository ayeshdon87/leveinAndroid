package com.ayesh.leveintest.domain.repository

import com.ayesh.leveintest.data.models.AddAuthorModel
import com.ayesh.leveintest.data.models.AuthorData
import com.ayesh.leveintest.data.models.SuccessResponse
import com.ayesh.leveintest.data.models.UpdateAuthorModel

interface AuthorRepository {
    suspend fun getAuthors(): List<AuthorData>

    suspend fun addAuthor(author: AddAuthorModel): SuccessResponse

    suspend fun updateAuthor(author: UpdateAuthorModel): SuccessResponse
}

package com.ayesh.leveintest.data.repository

import com.ayesh.leveintest.data.models.AddAuthorModel
import com.ayesh.leveintest.data.models.AuthorData
import com.ayesh.leveintest.data.models.SuccessResponse
import com.ayesh.leveintest.data.models.UpdateAuthorModel
import com.ayesh.leveintest.data.remote.APIClass
import com.ayesh.leveintest.domain.repository.AuthorRepository
import javax.inject.Inject

class AuthorRepositoryImpl
    @Inject
    constructor(private var apiClass: APIClass) : AuthorRepository {
        override suspend fun getAuthors(): List<AuthorData> {
            return apiClass.getAuthorList()
        }

        override suspend fun addAuthor(author: AddAuthorModel): SuccessResponse {
            return apiClass.addAuthor(author)
        }

        override suspend fun updateAuthor(author: UpdateAuthorModel): SuccessResponse {
            return apiClass.updateAuthor(author)
        }
    }

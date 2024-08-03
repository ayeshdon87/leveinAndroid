package com.ayesh.leveintest.data.repository

import com.ayesh.leveintest.data.models.AuthorData
import com.ayesh.leveintest.data.remote.APIClass
import com.ayesh.leveintest.domain.repository.AuthorRepository
import javax.inject.Inject

class AuthorRepositoryImpl @Inject constructor(private var apiClass: APIClass) : AuthorRepository {
    override suspend fun getAuthors(): List<AuthorData> {
        return apiClass.getAuthorList()
    }
}
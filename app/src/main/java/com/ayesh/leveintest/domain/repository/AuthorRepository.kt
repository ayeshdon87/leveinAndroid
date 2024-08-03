package com.ayesh.leveintest.domain.repository

import com.ayesh.leveintest.data.models.AuthorData

interface AuthorRepository {

    suspend fun getAuthors(): List<AuthorData>
}
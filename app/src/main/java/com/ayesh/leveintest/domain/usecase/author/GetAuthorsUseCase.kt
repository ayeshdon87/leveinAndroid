package com.ayesh.leveintest.domain.usecase.author

import android.net.http.HttpException
import com.ayesh.leveintest.data.models.AuthorData
import com.ayesh.leveintest.domain.repository.AuthorRepository
import com.ayesh.leveintest.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import javax.inject.Inject

class GetAuthorsUseCase
    @Inject
    constructor(
        private val repository: AuthorRepository,
    ) {
        operator fun invoke(): Flow<Resource<List<AuthorData>>> =
            flow {
                try {
                    emit(Resource.Loading())
                    val response = repository.getAuthors()
                    emit(Resource.Success(response))
                } catch (e: HttpException) {
                    emit(Resource.Error(e.localizedMessage ?: " An unexpected error occurred"))
                } catch (e: IOException) {
                    emit(Resource.Error("Couldn't reach server"))
                }
            }
    }

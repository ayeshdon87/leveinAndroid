package com.ayesh.leveintest.domain.usecase.author

import android.net.http.HttpException
import com.ayesh.leveintest.data.models.AddAuthorModel
import com.ayesh.leveintest.data.models.SuccessResponse
import com.ayesh.leveintest.domain.repository.AuthorRepository
import com.ayesh.leveintest.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import javax.inject.Inject

class AddAuthorUseCase
    @Inject
    constructor(
        private val repository: AuthorRepository,
    ) {
        operator fun invoke(model: AddAuthorModel): Flow<Resource<SuccessResponse>> =
            flow {
                try {
                    emit(Resource.Loading())
                    val response = repository.addAuthor(model)
                    emit(Resource.Success(response))
                } catch (e: HttpException) {
                    emit(Resource.Error(e.localizedMessage ?: " An unexpected error occurred"))
                } catch (e: IOException) {
                    emit(Resource.Error("Couldn't reach server"))
                }
            }
    }

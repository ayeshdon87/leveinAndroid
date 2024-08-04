package com.ayesh.leveintest.domain.usecase.book

import android.net.http.HttpException
import com.ayesh.leveintest.data.models.BookResponse
import com.ayesh.leveintest.domain.repository.BookRepository
import com.ayesh.leveintest.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import javax.inject.Inject

class GetBooksUseCase
    @Inject
    constructor(
        private val repository: BookRepository,
    ) {
        operator fun invoke(page: Int): Flow<Resource<BookResponse>> =
            flow {
                try {
                    emit(Resource.Loading())
                    val response = repository.getBooList(page)
                    emit(Resource.Success(response))
                } catch (e: HttpException) {
                    emit(Resource.Error(e.localizedMessage ?: " An unexpected error occurred"))
                } catch (e: IOException) {
                    emit(Resource.Error("Couldn't reach server"))
                }
            }
    }

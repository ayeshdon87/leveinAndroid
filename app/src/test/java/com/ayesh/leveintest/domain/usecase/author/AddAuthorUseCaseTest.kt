package com.ayesh.leveintest.domain.usecase.author

import com.ayesh.leveintest.data.models.AddAuthorModel
import com.ayesh.leveintest.data.models.SuccessResponse
import com.ayesh.leveintest.data.repository.AuthorRepositoryImpl
import com.ayesh.leveintest.utils.Resource
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import java.io.IOException

class AddAuthorUseCaseTest {
    private lateinit var useCase: AddAuthorUseCase

    @MockK
    private lateinit var repository: AuthorRepositoryImpl

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxed = true)
        useCase = AddAuthorUseCase(repository)
    }

    @Test
    fun `invoke returns success when repository returns data`() =
        runBlocking {
            var mockSuccessResponse = SuccessResponse(success = true, message = "Success")
            var request = AddAuthorModel(first_name = "Ayesh", last_name = "Nanayakkara")
            coEvery { repository.addAuthor(request) } returns mockSuccessResponse

            val flow = useCase(request)
            val flowResults = mutableListOf<Resource<SuccessResponse>>()
            flow.collect { result -> flowResults.add(result) }

            Assert.assertEquals(2, flowResults.size)
            Assert.assertTrue(flowResults[0] is Resource.Loading)
            Assert.assertTrue(flowResults[1] is Resource.Success)
            Assert.assertEquals(mockSuccessResponse, (flowResults[1] as Resource.Success).data)
        }

    @Test
    fun `invoke returns error when IOException is thrown`() =
        runBlocking {
            val errorMessage = "Couldn't reach server"
            var request = AddAuthorModel(first_name = "Ayesh", last_name = "Nanayakkara")
            coEvery { repository.addAuthor(request) } throws
                IOException()

            val flow = useCase(request)
            val flowResults = mutableListOf<Resource<SuccessResponse>>()
            flow.collect { result -> flowResults.add(result) }

            Assert.assertEquals(2, flowResults.size)
            Assert.assertTrue(flowResults[0] is Resource.Loading)
            Assert.assertTrue(flowResults[1] is Resource.Error)
            Assert.assertEquals(errorMessage, (flowResults[1] as Resource.Error).message)
        }
}

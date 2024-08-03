package com.ayesh.leveintest.domain.usecase.author

import com.ayesh.leveintest.data.models.AuthorData
import com.ayesh.leveintest.data.repository.AuthorRepositoryImpl
import com.ayesh.leveintest.utils.Resource
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.runBlocking
import okio.IOException
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class GetAuthorsUseCaseTest {
    private lateinit var useCase: GetAuthorsUseCase

    @MockK
    private lateinit var repository: AuthorRepositoryImpl

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxed = true)
        useCase = GetAuthorsUseCase(repository)
    }

    @Test
    fun `invoke returns success when repository returns data`() =
        runBlocking {
            val mockAuthorList =
                listOf(
                    AuthorData(first_name = "Ayesh", last_name = "Nanayakkara"),
                    AuthorData(first_name = "Sanath", last_name = "Jayasuriya"),
                    AuthorData(first_name = "Kumar", last_name = "Sangakkara"),
                )
            coEvery { repository.getAuthors() } returns mockAuthorList

            val flow = useCase()
            val flowResults = mutableListOf<Resource<List<AuthorData>>>()
            flow.collect { result -> flowResults.add(result) }

            Assert.assertEquals(2, flowResults.size)
            Assert.assertTrue(flowResults[0] is Resource.Loading)
            Assert.assertTrue(flowResults[1] is Resource.Success)
            Assert.assertEquals(mockAuthorList, (flowResults[1] as Resource.Success).data)
        }

    @Test
    fun `invoke returns error when IOException is thrown`() =
        runBlocking {
            val errorMessage = "Couldn't reach server"
            coEvery { repository.getAuthors() } throws
                IOException()

            val flow = useCase()
            val flowResults = mutableListOf<Resource<List<AuthorData>>>()
            flow.collect { result -> flowResults.add(result) }

            Assert.assertEquals(2, flowResults.size)
            Assert.assertTrue(flowResults[0] is Resource.Loading)
            Assert.assertTrue(flowResults[1] is Resource.Error)
            Assert.assertEquals(errorMessage, (flowResults[1] as Resource.Error).message)
        }
}

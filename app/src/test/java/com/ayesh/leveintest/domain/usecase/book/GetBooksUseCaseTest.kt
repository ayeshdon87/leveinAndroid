package com.ayesh.leveintest.domain.usecase.book

import com.ayesh.leveintest.data.models.AuthorData
import com.ayesh.leveintest.data.models.BookData
import com.ayesh.leveintest.data.models.BookResponse
import com.ayesh.leveintest.data.repository.BookRepositoryImpl
import com.ayesh.leveintest.utils.Resource
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.any
import java.io.IOException

class GetBooksUseCaseTest {
    private lateinit var useCase: GetBooksUseCase

    @MockK
    private lateinit var repository: BookRepositoryImpl

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxed = true)
        useCase = GetBooksUseCase(repository)
    }

    @Test
    fun `invoke returns success when repository returns data`() =
        runBlocking {
            val mockBookList =
                listOf(
                    BookData(
                        name = "Madol Duwa",
                        isbn = "1212321-3434-324",
                        ID = "867635234",
                        author =
                            AuthorData(
                                first_name = "Ayesh",
                                last_name = "Nanayakkara",
                                ID = "1234",
                            ),
                    ),
                    BookData(
                        name = "Madol Duwa2",
                        isbn = "1212321-3434-3241",
                        ID = "8676352342",
                        author =
                            AuthorData(
                                first_name = "Ayesh3",
                                last_name = "Nanayakkara3",
                                ID = "12344",
                            ),
                    ),
                )
            val response = BookResponse(auther = mockBookList, current_page = 1, next_page = 2)
            coEvery { repository.getBooList(any()) } returns response

            val flow = useCase(any())
            val flowResults = mutableListOf<Resource<BookResponse>>()
            flow.collect { result -> flowResults.add(result) }

            Assert.assertEquals(2, flowResults.size)
            Assert.assertTrue(flowResults[0] is Resource.Loading)
            Assert.assertTrue(flowResults[1] is Resource.Success)
            Assert.assertEquals(response, (flowResults[1] as Resource.Success).data)
        }

    @Test
    fun `invoke returns error when IOException is thrown`() =
        runBlocking {
            val errorMessage = "Couldn't reach server"
            coEvery { repository.getBooList(any()) } throws
                IOException()

            val flow = useCase(any())
            val flowResults = mutableListOf<Resource<BookResponse>>()
            flow.collect { result -> flowResults.add(result) }

            Assert.assertEquals(2, flowResults.size)
            Assert.assertTrue(flowResults[0] is Resource.Loading)
            Assert.assertTrue(flowResults[1] is Resource.Error)
            Assert.assertEquals(errorMessage, (flowResults[1] as Resource.Error).message)
        }
}

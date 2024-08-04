package com.ayesh.leveintest.data.repository

import com.ayesh.leveintest.data.models.AuthorData
import com.ayesh.leveintest.data.models.BookData
import com.ayesh.leveintest.data.models.BookResponse
import com.ayesh.leveintest.data.remote.APIClass
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.times

class BookRepositoryImplTest {
    private var apiClass: APIClass = mock()
    private lateinit var bookRepository: BookRepositoryImpl

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        bookRepository = BookRepositoryImpl(apiClass)
    }

    @Test
    fun `book list get returns expected data`(): Unit =
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

            `when`(apiClass.getBookList(any())).thenReturn(response)

            val result = bookRepository.getBooList(any())

            Assert.assertEquals(response, result)
            verify(apiClass, times(1)).getBookList(any())
        }
}

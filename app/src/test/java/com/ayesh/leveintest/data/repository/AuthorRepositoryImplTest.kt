package com.ayesh.leveintest.data.repository

import com.ayesh.leveintest.data.models.AddAuthorModel
import com.ayesh.leveintest.data.models.AuthorData
import com.ayesh.leveintest.data.models.SuccessResponse
import com.ayesh.leveintest.data.remote.APIClass
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.mock
import org.mockito.kotlin.times

class AuthorRepositoryImplTest {
    private var apiClass: APIClass = mock()
    private lateinit var authorRepository: AuthorRepositoryImpl

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        authorRepository = AuthorRepositoryImpl(apiClass)
    }

    @Test
    fun `author list get returns expected data`(): Unit =
        runBlocking {
            val mockAuthorList =
                listOf(
                    AuthorData(first_name = "Ayesh", last_name = "Nanayakkara", ID = "1234"),
                    AuthorData(first_name = "Sanath", last_name = "Jayasuriya", ID = "12345"),
                    AuthorData(first_name = "Kumar", last_name = "Sangakkara", ID = "123456"),
                )
            `when`(apiClass.getAuthorList()).thenReturn(mockAuthorList)

            val result = authorRepository.getAuthors()

            Assert.assertEquals(mockAuthorList, result)
            verify(apiClass, times(1)).getAuthorList()
        }

    @Test
    fun `author add returns expected data`(): Unit =
        runBlocking {
            var mockSuccessResponse = SuccessResponse(success = true, message = "Success")
            var request = AddAuthorModel(first_name = "Ayesh", last_name = "Nanayakkara")

            `when`(apiClass.addAuthor(request)).thenReturn(mockSuccessResponse)

            val result = authorRepository.addAuthor(request)

            Assert.assertEquals(mockSuccessResponse, result)
            verify(apiClass, times(1)).addAuthor(request)
        }
}

package com.ayesh.leveintest.data.repository

import com.ayesh.leveintest.data.models.AuthorData
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
                    AuthorData(first_name = "Ayesh", last_name = "Nanayakkara"),
                    AuthorData(first_name = "Sanath", last_name = "Jayasuriya"),
                    AuthorData(first_name = "Kumar", last_name = "Sangakkara"),
                )
            `when`(apiClass.getAuthorList()).thenReturn(mockAuthorList)

            val result = authorRepository.getAuthors()

            Assert.assertEquals(mockAuthorList, result)
            verify(apiClass, times(1)).getAuthorList()
        }
}

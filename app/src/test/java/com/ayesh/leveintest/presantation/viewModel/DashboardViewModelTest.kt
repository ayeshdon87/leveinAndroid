package com.ayesh.leveintest.presantation.viewModel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.ayesh.leveintest.data.models.AuthorData
import com.ayesh.leveintest.domain.usecase.author.GetAuthorsUseCase
import com.ayesh.leveintest.presantation.states.BaseState
import com.ayesh.leveintest.utils.Resource
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

class DashboardViewModelTest {
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = TestCoroutineDispatcher()
    private val testScope = TestCoroutineScope(testDispatcher)

    private lateinit var viewModel: DashboardViewModel
    private val getAuthorsUseCase: GetAuthorsUseCase = mockk()

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxed = true)
        Dispatchers.setMain(testDispatcher)
        viewModel = DashboardViewModel(getAuthorsUseCase)
    }

    @Test
    fun `test getAuthorsList emits loading state`() =
        testScope.runBlockingTest {
            val observer = mock<Observer<BaseState<List<AuthorData>>>>()
            viewModel.authors.observeForever(observer)

            coEvery { getAuthorsUseCase.invoke() } returns
                flow {
                    emit(Resource.Loading())
                }
            viewModel.onEvent(DashboardEvent.GetAuthors)
            testDispatcher.scheduler.advanceUntilIdle()

            verify(observer).onChanged(BaseState(true, null, ""))
        }

    @Test
    fun `test getAuthorsList emits success state`() =
        testScope.runBlockingTest {
            val authorList =
                listOf(
                    AuthorData(first_name = "Ayesh", last_name = "Nanayakkara"),
                    AuthorData(first_name = "Sanath", last_name = "Jayasuriya"),
                    AuthorData(first_name = "Kumar", last_name = "Sangakkara"),
                )
            val observer = mock<Observer<BaseState<List<AuthorData>>>>()
            viewModel.authors.observeForever(observer)

            coEvery { getAuthorsUseCase.invoke() } returns
                flow {
                    emit(Resource.Success(authorList))
                }

            viewModel.onEvent(DashboardEvent.GetAuthors)

            testDispatcher.scheduler.advanceUntilIdle()

            verify(observer).onChanged(BaseState(false, authorList, null))
        }

    @Test
    fun `test getAuthorsList emits error state`() =
        testScope.runBlockingTest {
            val observer = mock<Observer<BaseState<List<AuthorData>>>>()
            viewModel.authors.observeForever(observer)

            coEvery { getAuthorsUseCase.invoke() } returns
                flow {
                    emit(Resource.Error("Error fetching authors"))
                }

            viewModel.onEvent(DashboardEvent.GetAuthors)

            testDispatcher.scheduler.advanceUntilIdle()
            verify(observer).onChanged(BaseState(false, null, "Error fetching authors"))
        }
}

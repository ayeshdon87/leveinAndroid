package com.ayesh.leveintest.presantation.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ayesh.leveintest.data.models.AuthorData
import com.ayesh.leveintest.data.models.toBookItem
import com.ayesh.leveintest.domain.models.BookItem
import com.ayesh.leveintest.domain.usecase.author.GetAuthorsUseCase
import com.ayesh.leveintest.domain.usecase.book.GetBooksUseCase
import com.ayesh.leveintest.presantation.states.BaseState
import com.ayesh.leveintest.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel
    @Inject
    constructor(
        val getAuthorsUseCase: GetAuthorsUseCase,
        val getBooksUseCase: GetBooksUseCase,
    ) :
    ViewModel() {
        private val _authors = MutableLiveData<BaseState<List<AuthorData>>>()
        val authors: LiveData<BaseState<List<AuthorData>>> = _authors
        private val _books = MutableLiveData<BaseState<List<BookItem>>>()
        val books: LiveData<BaseState<List<BookItem>>> = _books

        fun onEvent(events: DashboardEvent) {
            when (events) {
                is DashboardEvent.GetAuthors -> {
                    getAuthorsList()
                }

                is DashboardEvent.GetBooks -> {
                    getBookList(events.page)
                }
            }
        }

        private fun getBookList(page: Int) {
            getBooksUseCase(page).onEach {
                when (it) {
                    is Resource.Loading -> {
                        _books.value = BaseState(data = null, error = "", isLoading = true)
                    }

                    is Resource.Error -> {
                        _books.value = BaseState(data = null, error = it.message, isLoading = false)
                    }

                    is Resource.Success -> {
                        _books.value =
                            BaseState(
                                data = it.data?.auther?.map { data -> data.toBookItem() },
                                error = null,
                                isLoading = false,
                            )
                    }
                }
            }.launchIn(viewModelScope)
        }

        private fun getAuthorsList() {
            getAuthorsUseCase().onEach {
                when (it) {
                    is Resource.Loading -> {
                        _authors.value = BaseState(data = null, error = "", isLoading = true)
                    }

                    is Resource.Error -> {
                        _authors.value = BaseState(data = null, error = it.message, isLoading = false)
                    }

                    is Resource.Success -> {
                        _authors.value = BaseState(data = it.data, error = null, isLoading = false)
                    }
                }
            }.launchIn(viewModelScope)
        }
    }

sealed class DashboardEvent {
    data object GetAuthors : DashboardEvent()

    data class GetBooks(var page: Int) : DashboardEvent()
}

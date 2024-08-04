package com.ayesh.leveintest.presantation.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.cachedIn
import androidx.paging.map
import com.ayesh.leveintest.data.local.BookEntity
import com.ayesh.leveintest.data.mapper.toBookItem
import com.ayesh.leveintest.data.models.toBookItem
import com.ayesh.leveintest.domain.models.BookItem
import com.ayesh.leveintest.domain.usecase.book.GetBooksUseCase
import com.ayesh.leveintest.presantation.states.BaseState
import com.ayesh.leveintest.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class BookViewModel
    @Inject
    constructor(
        val getBooksUseCase: GetBooksUseCase,
        pager: Pager<Int, BookEntity>,
    ) : ViewModel() {
        private val _books = MutableLiveData<BaseState<List<BookItem>>>()
        val books: LiveData<BaseState<List<BookItem>>> = _books

        fun onEvent(events: BookEvent) {
            when (events) {
                is BookEvent.GetBooks -> {
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

        val bookPagingFlow =
            pager
                .flow
                .map { pagingData ->
                    pagingData.map { it.toBookItem() }
                }
                .cachedIn(viewModelScope)
    }

sealed class BookEvent {
    data class GetBooks(var page: Int) : BookEvent()
}

package com.ayesh.leveintest.data.remote

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.ayesh.leveintest.data.local.BookEntity
import com.ayesh.leveintest.data.local.LibraryDatabase
import com.ayesh.leveintest.data.mapper.toBookEntity
import com.ayesh.leveintest.utils.Constant
import okio.IOException
import retrofit2.HttpException

@OptIn(ExperimentalPagingApi::class)
class BookRemoteMediator(
    private val libraryDb: LibraryDatabase,
    private val apiClass: APIClass,
) : RemoteMediator<Int, BookEntity>() {
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, BookEntity>,
    ): MediatorResult {
        return try {
            val pageKey =
                when (loadType) {
                    LoadType.REFRESH -> 1
                    LoadType.PREPEND ->
                        return MediatorResult.Success(
                            endOfPaginationReached = true,
                        )

                    LoadType.APPEND -> {
                        val lastItem = state.lastItemOrNull()
//                        lastItem?.let { it.nextPage } ?: return MediatorResult.Success(endOfPaginationReached = true)
                        if (lastItem == null) {
                            1
                        } else {
                            Constant.currentPage++
                        }
                    }
                }

            val bookCall = apiClass.getBookList(pageKey)

            libraryDb.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    libraryDb.dao.clearAll()
                }
                bookCall.auther?.let { data ->
                    val bookList = data.map { it.toBookEntity() }
                    libraryDb.dao.upsertAll(bookList)
                }
            }
            MediatorResult.Success(
                endOfPaginationReached = bookCall.auther == null,
            )
        } catch (e: IOException) {
            MediatorResult.Error(e)
        } catch (e: HttpException) {
            MediatorResult.Error(e)
        }
    }
}

package com.ayesh.leveintest.presantation.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.ayesh.leveintest.R
import com.ayesh.leveintest.domain.models.BookItem
import com.ayesh.leveintest.presantation.component.LoadingDialog
import com.ayesh.leveintest.presantation.component.bookItemView
import com.ayesh.leveintest.presantation.ui.theme.bg
import com.ayesh.leveintest.presantation.ui.theme.white
import com.ayesh.leveintest.presantation.viewModel.BookEvent

@Composable
fun bookListScreen(
    navController: NavController,
    bookListState: LazyPagingItems<BookItem>,
    onEvent: (BookEvent) -> Unit,
) {
    val context = LocalContext.current
    // val bookState by bookListState.observeAsState()

    LoadingDialog(isLoading = (bookListState.loadState.refresh is LoadState.Loading))
//
//    LaunchedEffect(Unit) {
//        onEvent(BookEvent.GetBooks(1))
//    }

    LaunchedEffect(key1 = bookListState.loadState) {
        if (bookListState.loadState.refresh is LoadState.Error) {
            Toast.makeText(
                context,
                "Error: " + (bookListState.loadState.refresh as LoadState.Error).error.message,
                Toast.LENGTH_LONG,
            ).show()
        }
    }

    Scaffold(
        topBar = {
            appActionBar(title = stringResource(id = R.string.book_list))
        },
    ) {
        Box(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .padding(it)
                    .background(color = bg),
        ) {
            Column(
                Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .shadow(4.dp, shape = RoundedCornerShape(10.dp))
                    .background(white, shape = RoundedCornerShape(10.dp))
                    .padding(16.dp),
            ) {
//                bookListState?.?.let {

                LazyVerticalGrid(
                    columns = GridCells.Fixed(2), // Specifies 2 items per row
                    modifier = Modifier.fillMaxSize().padding(16.dp),
                ) {
                    items(bookListState.itemCount) { book ->
                        bookItemView(bookItm = bookListState[book]!!, onBookClick = {})
                    }
                    item {
                        if (bookListState.loadState.append is LoadState.Loading) {
                            CircularProgressIndicator()
                        }
                    }
                }

//                LazyColumn {
//                    // Iterate over the LazyPagingItems
//                    items(bookListState) { book ->
//                        // Render each book item
//                        book?.let {
//                            Text(text = it.name)
//                        }
//                    }

//                }
            }
        }
    }
}

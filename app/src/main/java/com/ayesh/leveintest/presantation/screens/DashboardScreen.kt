package com.ayesh.leveintest.presantation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.ayesh.leveintest.R
import com.ayesh.leveintest.data.models.AuthorData
import com.ayesh.leveintest.domain.models.BookItem
import com.ayesh.leveintest.presantation.component.LoadingDialog
import com.ayesh.leveintest.presantation.component.bookItemView
import com.ayesh.leveintest.presantation.component.tagView
import com.ayesh.leveintest.presantation.navigation.Screens
import com.ayesh.leveintest.presantation.states.BaseState
import com.ayesh.leveintest.presantation.ui.theme.authorButton
import com.ayesh.leveintest.presantation.ui.theme.bg
import com.ayesh.leveintest.presantation.ui.theme.bookButton
import com.ayesh.leveintest.presantation.ui.theme.darkText
import com.ayesh.leveintest.presantation.ui.theme.lightGreyText
import com.ayesh.leveintest.presantation.ui.theme.typoLocal
import com.ayesh.leveintest.presantation.ui.theme.white
import com.ayesh.leveintest.presantation.viewModel.DashboardEvent
import com.ayesh.leveintest.utils.capitalizeFirstLetter
import com.ayesh.leveintest.utils.orEmptyIfNull

@Composable
fun dashboardScreen(
    navController: NavController,
    onEvent: (DashboardEvent) -> Unit,
    authorListState: LiveData<BaseState<List<AuthorData>>>,
    bookListState: LiveData<BaseState<List<BookItem>>>,
) {
    val authorState by authorListState.observeAsState()
    val bookState by bookListState.observeAsState()

    LaunchedEffect(Unit) {
        onEvent(DashboardEvent.GetAuthors)
        onEvent(DashboardEvent.GetBooks(1))
    }

    LoadingDialog(isLoading = (authorState?.isLoading == true || bookState?.isLoading == true))

    Scaffold(
        topBar = {
            appActionBar(title = stringResource(id = R.string.dashboard_title))
        },
    ) {
        Column(
            Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(it)
                .background(color = bg),
        ) {
            Row(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
            ) {
                Card(
                    modifier =
                        Modifier
                            .weight(1f)
                            .clickable {
                                navController?.navigate(Screens.AuthorAddScreen)
                            }
                            .height(100.dp),
                    colors = CardDefaults.cardColors(containerColor = authorButton),
                    elevation =
                        CardDefaults.cardElevation(
                            defaultElevation = 10.dp,
                        ),
                    shape = RoundedCornerShape(8.dp),
                ) {
                    Box(
                        modifier =
                            Modifier
                                .fillMaxSize()
                                .padding(16.dp),
                        contentAlignment = Alignment.Center,
                    ) {
                        Text(
                            text = stringResource(id = R.string.add_author),
                            color = white,
                            style = typoLocal.titleLarge,
                        )
                    }
                }
                Spacer(modifier = Modifier.width(16.dp))

                Card(
                    modifier =
                        Modifier
                            .weight(1f)
                            .height(100.dp),
                    colors = CardDefaults.cardColors(containerColor = bookButton),
                    elevation =
                        CardDefaults.cardElevation(
                            defaultElevation = 10.dp,
                        ),
                    shape = RoundedCornerShape(8.dp),
                ) {
                    Box(
                        modifier =
                            Modifier
                                .fillMaxSize()
                                .padding(16.dp),
                        contentAlignment = Alignment.Center,
                    ) {
                        Text(
                            text = stringResource(id = R.string.add_book),
                            color = white,
                            style = typoLocal.titleLarge,
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
            ) {
                Text(
                    text = stringResource(id = R.string.latest_author),
                    color = darkText,
                    style = typoLocal.bodyLarge,
                )
            }
            authorState?.data?.let { data ->
                LazyHorizontalGrid(
                    rows = GridCells.Fixed(1),
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .height(80.dp)
                            .padding(16.dp),
                ) {
                    items(data.size) { tag ->
                        tagView(
                            tag =
                                "${data[tag].first_name.orEmptyIfNull().capitalizeFirstLetter()} " +
                                    "${
                                        data[tag].last_name.orEmptyIfNull().capitalizeFirstLetter()
                                    }",
                            onTagClick = {
                                navController?.navigate(
                                    Screens.UpdateAuthorScreen(
                                        firstName = data[tag].first_name,
                                        lastName = data[tag].last_name,
                                        id = data[tag].ID,
                                    ),
                                )
                            },
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
            ) {
                Text(
                    text = stringResource(id = R.string.new_arrival),
                    color = darkText,
                    style = typoLocal.bodyLarge,
                )
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = stringResource(id = R.string.view_all),
                    color = lightGreyText,
                    style = typoLocal.bodyUnderLine,
                    modifier =
                        Modifier.clickable {
                            navController?.navigate(Screens.BookListScreen)
                        },
                )
            }
            bookState?.data?.let {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2), // Specifies 2 items per row
                    modifier = Modifier.fillMaxSize().padding(16.dp),
                ) {
                    items(it.size) { book ->
                        bookItemView(bookItm = it[book], onBookClick = {})
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun dashboardScreenPreview() {
    val mockNavController = rememberNavController()
    val _authors = MutableLiveData<BaseState<List<AuthorData>>>()
    val authors: LiveData<BaseState<List<AuthorData>>> = _authors
    val _books = MutableLiveData<BaseState<List<BookItem>>>()
    val books: LiveData<BaseState<List<BookItem>>> = _books
    dashboardScreen(
        navController = mockNavController,
        onEvent = {},
        authorListState = authors,
        bookListState = books,
    )
}

package com.ayesh.leveintest.presantation.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import androidx.paging.compose.collectAsLazyPagingItems
import com.ayesh.leveintest.presantation.screens.addAuthorScreen
import com.ayesh.leveintest.presantation.screens.bookDetailsScreen
import com.ayesh.leveintest.presantation.screens.bookListScreen
import com.ayesh.leveintest.presantation.screens.dashboardScreen
import com.ayesh.leveintest.presantation.screens.updateAuthorScreen
import com.ayesh.leveintest.presantation.viewModel.AuthorViewModel
import com.ayesh.leveintest.presantation.viewModel.BookViewModel
import com.ayesh.leveintest.presantation.viewModel.DashboardViewModel

@Composable
fun appNavigation() {
    val naviController = rememberNavController()
    NavHost(
        navController = naviController,
        startDestination = Screens.DashboardScreen,
    ) {
        composable<Screens.DashboardScreen> {
            var viewModel: DashboardViewModel = hiltViewModel()
            dashboardScreen(
                navController = naviController,
                onEvent = viewModel::onEvent,
                authorListState = viewModel.authors,
                bookListState = viewModel.books,
            )
        }
        composable<Screens.BookDetailsScreen> {
            val arg = it.toRoute<Screens.BookDetailsScreen>()
            bookDetailsScreen(navController = naviController, arg.bookId)
        }

        composable<Screens.AuthorAddScreen> {
            var viewModel: AuthorViewModel = hiltViewModel()
            addAuthorScreen(
                navController = naviController,
                onEvent = viewModel::onEvent,
                validationState = viewModel.validationState,
                addStateObserver = viewModel.addAuthorState,
            )
        }
        composable<Screens.UpdateAuthorScreen> {
            val arg = it.toRoute<Screens.UpdateAuthorScreen>()
            var viewModel: AuthorViewModel = hiltViewModel()
            updateAuthorScreen(
                navController = naviController,
                onEvent = viewModel::onEvent,
                validationState =
                    viewModel.updateValidationState,
                updateStateObserver = viewModel.updateAuthorState,
                firstName = arg.firstName,
                lastName = arg.lastName,
                id = arg.id,
            )
        }

        composable<Screens.BookListScreen> {
            var viewModel: BookViewModel = hiltViewModel()
            val book = viewModel.bookPagingFlow.collectAsLazyPagingItems()
            // beers: LazyPagingItems<Beer>
            bookListScreen(
                navController = naviController,
                onEvent = viewModel::onEvent,
                bookListState = book,
            )
        }
    }
}

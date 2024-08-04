package com.ayesh.leveintest.presantation.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.ayesh.leveintest.presantation.screens.bookDetailsScreen
import com.ayesh.leveintest.presantation.screens.dashboardScreen
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
    }
}

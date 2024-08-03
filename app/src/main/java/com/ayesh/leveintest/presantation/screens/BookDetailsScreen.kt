package com.ayesh.leveintest.presantation.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.ayesh.leveintest.presantation.navigation.Screens


@Composable
fun bookDetailsScreen( navController: NavController, bookId: String){

    Box(modifier = Modifier.fillMaxSize()) {
        Text(
            text = "Book id $bookId",
            modifier = Modifier.clickable
            { navController?.navigate(Screens.BookDetailsScreen("1234")) })
    }


}
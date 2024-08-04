package com.ayesh.leveintest.presantation.navigation

import kotlinx.serialization.Serializable

sealed class Screens {
    @Serializable
    data object DashboardScreen : Screens()

    @Serializable
    data object BookListScreen : Screens()

    @Serializable
    data object AuthorListScreen : Screens()

    @Serializable
    data object BookAddScreen : Screens()

    @Serializable
    data class BookDetailsScreen(val bookId: String) : Screens()

    @Serializable
    data class UpdateAuthorScreen(val firstName: String, val lastName: String, val id: String) :
        Screens()

    @Serializable
    data object AuthorAddScreen : Screens()
}

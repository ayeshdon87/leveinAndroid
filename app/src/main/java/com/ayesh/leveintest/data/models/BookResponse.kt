package com.ayesh.leveintest.data.models

data class BookResponse(
    val auther: List<BookData>?,
    val current_page: Int,
    val next_page: Int,
)

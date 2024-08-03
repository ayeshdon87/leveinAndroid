package com.ayesh.leveintest.presantation.states

data class BaseState<T>(
    val isLoading: Boolean = false,
    val data: T? = null,
    val error: String? = null
)
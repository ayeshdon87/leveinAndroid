package com.ayesh.leveintest.domain.core.validator

import com.ayesh.leveintest.utils.UiText

data class ValidationOutput(
    val successful: Boolean,
    val errorMessage: UiText? = null,
)

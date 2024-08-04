package com.ayesh.leveintest.domain.usecase.author

import com.ayesh.leveintest.R
import com.ayesh.leveintest.domain.core.validator.ValidationOutput
import com.ayesh.leveintest.domain.usecase.BaseUseCase
import com.ayesh.leveintest.utils.UiText

class ValidateLastNameUseCase : BaseUseCase<String, ValidationOutput> {
    override fun execute(input: String): ValidationOutput {
        if (input.isBlank()) {
            return ValidationOutput(
                successful = false,
                errorMessage = UiText.StringResource(resId = R.string.author_name_invalid),
            )
        }
        return ValidationOutput(
            successful = true,
            errorMessage = null,
        )
    }
}

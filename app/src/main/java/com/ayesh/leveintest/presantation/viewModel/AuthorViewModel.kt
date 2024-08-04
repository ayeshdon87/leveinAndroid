package com.ayesh.leveintest.presantation.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ayesh.leveintest.data.models.AddAuthorModel
import com.ayesh.leveintest.data.models.SuccessResponse
import com.ayesh.leveintest.data.models.UpdateAuthorModel
import com.ayesh.leveintest.domain.usecase.author.AddAuthorUseCase
import com.ayesh.leveintest.domain.usecase.author.UpdateAuthorUseCase
import com.ayesh.leveintest.domain.usecase.author.ValidateFirstNameUseCase
import com.ayesh.leveintest.domain.usecase.author.ValidateLastNameUseCase
import com.ayesh.leveintest.presantation.states.BaseState
import com.ayesh.leveintest.presantation.states.BaseValidationState
import com.ayesh.leveintest.utils.Resource
import com.ayesh.leveintest.utils.UiText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class AuthorViewModel
    @Inject
    constructor(
        private var firstNameValidate: ValidateFirstNameUseCase,
        private var lastNameValidate: ValidateLastNameUseCase,
        private var addAuthorUseCase: AddAuthorUseCase,
        private var updateAuthorUseCase: UpdateAuthorUseCase,
    ) : ViewModel() {
        var validationState by mutableStateOf(AuthorAddValidationState())
        var updateValidationState by mutableStateOf(AuthorAddValidationState())
        private val _addAuthorState = MutableLiveData<BaseState<SuccessResponse>>()
        val addAuthorState: LiveData<BaseState<SuccessResponse>> = _addAuthorState

        private val _updateAuthorState = MutableLiveData<BaseState<SuccessResponse>>()
        val updateAuthorState: LiveData<BaseState<SuccessResponse>> = _updateAuthorState

        fun onEvent(events: AuthorAddScreenEvent) {
            when (events) {
                is AuthorAddScreenEvent.FirstNameChanged -> {
                    validationState = validationState.copy(firstName = events.firstName)
                    validateFirstName()
                }

                is AuthorAddScreenEvent.LastNameChanged -> {
                    validationState = validationState.copy(lastName = events.lastName)
                    validateLastName()
                }

                is AuthorAddScreenEvent.FirstNameChangedUpdate -> {
                    updateValidationState = updateValidationState.copy(firstName = events.firstName)
                }

                is AuthorAddScreenEvent.LastNameChangedUpdate -> {
                    updateValidationState = updateValidationState.copy(lastName = events.lastName)
                }

                is AuthorAddScreenEvent.Submit -> {
                    if (validateFirstName() && validateLastName()) {
                        val model = AddAuthorModel(validationState.firstName, validationState.lastName)
                        addAuthor(model)
                    }
                }

                is AuthorAddScreenEvent.Update -> {
                    updateAuthor(
                        UpdateAuthorModel(
                            first_name = updateValidationState.firstName,
                            last_name = updateValidationState.lastName,
                            user_id = events.id,
                        ),
                    )
                }

                AuthorAddScreenEvent.GetAuthorDetails -> {
                }
            }
        }

        private fun updateAuthor(model: UpdateAuthorModel) {
            updateAuthorUseCase(model).onEach {
                when (it) {
                    is Resource.Loading -> {
                        _updateAuthorState.value = BaseState(data = null, error = "", isLoading = true)
                    }

                    is Resource.Error -> {
                        _updateAuthorState.value =
                            BaseState(data = null, error = it.message, isLoading = false)
                    }

                    is Resource.Success -> {
                        _updateAuthorState.value =
                            BaseState(
                                data = it.data,
                                error = null,
                                isLoading = false,
                            )
                    }
                }
            }.launchIn(viewModelScope)
        }

        private fun addAuthor(model: AddAuthorModel) {
            addAuthorUseCase(model).onEach {
                when (it) {
                    is Resource.Loading -> {
                        _addAuthorState.value = BaseState(data = null, error = "", isLoading = true)
                    }

                    is Resource.Error -> {
                        _addAuthorState.value =
                            BaseState(data = null, error = it.message, isLoading = false)
                    }

                    is Resource.Success -> {
                        _addAuthorState.value =
                            BaseState(
                                data = it.data,
                                error = null,
                                isLoading = false,
                            )
                    }
                }
            }.launchIn(viewModelScope)
        }

        private fun validateFirstName(): Boolean {
            val validateResult = firstNameValidate.execute(validationState.firstName)
            validationState =
                validationState.copy(
                    firstError = validateResult.errorMessage,
                )
            return validateResult.successful
        }

        private fun validateLastName(): Boolean {
            val validateResult = lastNameValidate.execute(validationState.lastName)
            validationState = validationState.copy(lastError = validateResult.errorMessage)
            return validateResult.successful
        }
    }

sealed interface AuthorAddScreenEvent {
    data object Submit : AuthorAddScreenEvent

    data class Update(val id: String) : AuthorAddScreenEvent

    data object GetAuthorDetails : AuthorAddScreenEvent

    data class FirstNameChanged(val firstName: String) : AuthorAddScreenEvent

    data class FirstNameChangedUpdate(val firstName: String) : AuthorAddScreenEvent

    data class LastNameChanged(val lastName: String) : AuthorAddScreenEvent

    data class LastNameChangedUpdate(val lastName: String) : AuthorAddScreenEvent
}

data class AuthorAddValidationState(
    val firstName: String = "",
    val firstError: UiText? = null,
    val lastName: String = "",
    val lastError: UiText? = null,
) : BaseValidationState

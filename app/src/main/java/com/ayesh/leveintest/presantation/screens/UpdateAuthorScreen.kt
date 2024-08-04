package com.ayesh.leveintest.presantation.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LiveData
import androidx.navigation.NavController
import com.ayesh.leveintest.R
import com.ayesh.leveintest.data.models.SuccessResponse
import com.ayesh.leveintest.presantation.component.LoadingDialog
import com.ayesh.leveintest.presantation.component.appTextField
import com.ayesh.leveintest.presantation.states.BaseState
import com.ayesh.leveintest.presantation.ui.theme.authorButton
import com.ayesh.leveintest.presantation.ui.theme.bg
import com.ayesh.leveintest.presantation.ui.theme.typoLocal
import com.ayesh.leveintest.presantation.ui.theme.white
import com.ayesh.leveintest.presantation.viewModel.AuthorAddScreenEvent
import com.ayesh.leveintest.presantation.viewModel.AuthorAddValidationState

@Composable
fun updateAuthorScreen(
    navController: NavController,
    firstName: String,
    lastName: String,
    id: String,
    validationState: AuthorAddValidationState,
    updateStateObserver: LiveData<BaseState<SuccessResponse>>,
    onEvent: (AuthorAddScreenEvent) -> Unit,
) {
    val updateState by updateStateObserver.observeAsState()
    val context = LocalContext.current
    LaunchedEffect(Unit) {
        onEvent(AuthorAddScreenEvent.FirstNameChangedUpdate(firstName))
        onEvent(AuthorAddScreenEvent.LastNameChangedUpdate(lastName))
    }
    LoadingDialog(isLoading = (updateState?.isLoading == true))
    LaunchedEffect(key1 = updateState?.data) {
        updateState?.data?.let { data ->
            Toast.makeText(context, data.message, Toast.LENGTH_SHORT).show()
            if (data.success) {
                navController.popBackStack()
            }
        }
    }

    Scaffold(
        topBar = {
            appActionBar(title = stringResource(id = R.string.update_author))
        },
    ) {
        Box(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .padding(it)
                    .background(color = bg),
        ) {
            Column(
                Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .shadow(4.dp, shape = RoundedCornerShape(10.dp))
                    .background(white, shape = RoundedCornerShape(10.dp))
                    .padding(16.dp),
            ) {
                Spacer(modifier = Modifier.height(40.dp))
                appTextField(
                    placeholder = stringResource(id = R.string.author_first_name),
                    value = validationState.firstName,
                    onValueChange = { onEvent(AuthorAddScreenEvent.FirstNameChangedUpdate(it)) },
                    action = ImeAction.Next,
                    showError = validationState.firstError != null,
                    error = validationState.firstError?.asString(),
                )

                Spacer(modifier = Modifier.height(20.dp))
                appTextField(
                    placeholder = stringResource(id = R.string.author_last_name),
                    value = validationState.lastName,
                    onValueChange = { onEvent(AuthorAddScreenEvent.LastNameChangedUpdate(it)) },
                    action = ImeAction.Next,
                    showError = validationState.lastError != null,
                    error = validationState.lastError?.asString(),
                )
                Spacer(modifier = Modifier.height(40.dp))

                Button(
                    shape = RoundedCornerShape(5.dp),
                    onClick = {
                        onEvent(AuthorAddScreenEvent.Update(id))
                    },
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .height(50.dp),
                    colors =
                        ButtonDefaults.buttonColors(
                            containerColor = authorButton,
                        ),
                ) {
                    Text(
                        stringResource(id = R.string.update_author),
                        fontWeight = FontWeight.Normal,
                        color = white,
                        style = typoLocal.titleMedium,
                    )
                }
                Spacer(modifier = Modifier.height(40.dp))
            }
        }
    }
}

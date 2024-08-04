package com.ayesh.leveintest.presantation.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import com.ayesh.leveintest.presantation.ui.theme.darkText
import com.ayesh.leveintest.presantation.ui.theme.errorText
import com.ayesh.leveintest.presantation.ui.theme.lightGreyText

@Composable
fun appTextField(
    placeholder: String,
    value: String,
    icon:
        @Composable()
        (() -> Unit)? = null,
    modifier: Modifier = Modifier,
    isReadOnly: Boolean = false,
    onValueChange: (String) -> Unit,
    trailingIcon:
        @Composable()
        (() -> Unit)? = null,
    keyboardType: KeyboardType = KeyboardType.Text,
    showError: Boolean = false,
    error: String? = null,
    textColor: Color = darkText,
    action: ImeAction,
) {
    OutlinedTextField(
        modifier =
            modifier
                .fillMaxWidth(),
        label = {
            Text(
                "$placeholder",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Normal,
                color = lightGreyText,
            )
        },
        value = value,
        readOnly = isReadOnly,
        singleLine = true,
        onValueChange = onValueChange,
        keyboardOptions = KeyboardOptions(imeAction = action),
        placeholder = {
            Text(
                placeholder,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Normal,
                color = lightGreyText,
            )
        },
        trailingIcon = {
            if (showError) {
                Icon(Icons.Filled.Info, "error", tint = MaterialTheme.colorScheme.error)
            }
        },
        supportingText = {
            Column {
                if (error != null) {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = error,
                        color = MaterialTheme.colorScheme.error,
                    )
                }
            }
        },
        isError = showError,
        textStyle = MaterialTheme.typography.titleMedium,
        colors =
            OutlinedTextFieldDefaults.colors(
                focusedBorderColor = darkText,
                unfocusedBorderColor = lightGreyText,
                errorLabelColor = errorText,
                focusedTextColor = textColor,
            ),
    )
}

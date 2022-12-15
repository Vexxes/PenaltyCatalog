package de.vexxes.penaltycatalog.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Cancel
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import de.vexxes.penaltycatalog.R
import de.vexxes.penaltycatalog.ui.theme.Typography

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InputOutlinedField(
    modifier: Modifier = Modifier,
    readOnly: Boolean = false,
    text: String,
    onTextChanged: (String) -> Unit,
    trailingIcon: @Composable (() -> Unit)? = null,
    isError: Boolean = false,
    label: String,
    required: Boolean = false,
    keyboardOptions: KeyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Words)
) {
    var focused by remember { mutableStateOf(false) }

    val locTrailingIcon: @Composable () -> Unit = if (trailingIcon == null) {
        {
            if (text.isNotBlank() && focused)
                CancelIcon(
                    onClick = { onTextChanged("") }
                )
        }
    } else {
        trailingIcon
    }

    OutlinedTextField(
        modifier = modifier
            .fillMaxWidth()
            .onFocusChanged { focusState ->
                focused = focusState.isFocused
            },
        readOnly = readOnly,
        value = text,
        onValueChange = {
            onTextChanged(it)
        },
        label = {
            Text(
                text = if(required) "$label*" else label,
                softWrap = false,
                overflow = TextOverflow.Visible
            )
        },
        placeholder = {
            Text(
                text = if(required) "$label*" else label,
                softWrap = false,
                overflow = TextOverflow.Visible
            )
        },
        trailingIcon = locTrailingIcon,
        supportingText = {
            if(required && !isError) {
                Text(
                    text = stringResource(id = R.string.Required),
                    style = Typography.labelSmall
                )
            }
            if(isError) {
                Text(
                    text = stringResource(id = R.string.FieldEmpty),
                    style = Typography.labelLarge
                )
            }
        },
        isError = isError,
        keyboardOptions = keyboardOptions
    )
}

@Composable
private fun CancelIcon(
    onClick: () -> Unit
) {
    IconButton(
        onClick = {
            onClick()
        }
    ) {
        Icon(
            imageVector = Icons.Outlined.Cancel,
            contentDescription = stringResource(id = R.string.Cancel)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun InputOutlinedFieldPreview1() {
    InputOutlinedField(
        text = "Test",
        onTextChanged = { },
        label = "Test input"
    )
}

@Preview(showBackground = true)
@Composable
private fun InputOutlinedFieldPreview2() {
    InputOutlinedField(
        text = "Test",
        onTextChanged = { },
        label = "Test input",
        isError = true
    )
}

@Preview(showBackground = true)
@Composable
private fun InputOutlinedFieldPreview3() {
    InputOutlinedField(
        text = "Test",
        onTextChanged = { },
        label = "Test input",
        required = true
    )
}
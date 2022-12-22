package de.vexxes.penaltycatalog.component

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import de.vexxes.penaltycatalog.R

@Composable
fun DeleteAlertDialog(
    title: String,
    text: String,
    onDismissRequest: () -> Unit,
    onConfirmClicked: () -> Unit,
    onDismissButton: () -> Unit
) {
    AlertDialog(
        onDismissRequest = { onDismissRequest() },
        confirmButton = {
            TextButton(onClick = { onConfirmClicked() }) {
                Text(text = stringResource(id = R.string.ConfirmCapital))
            }
        },
        dismissButton = {
            TextButton(onClick = { onDismissButton() }) {
                Text(text = stringResource(id = R.string.CancelCapital))
            }
        },
        icon = {
            Icon(imageVector = Icons.Default.Warning, contentDescription = "")
        },
        title = { Text(text = title) },
        text = { Text(text = text) }
    )
}

@Preview(showBackground = true)
@Composable
private fun DeleteAlertDialogPreview() {
    DeleteAlertDialog(
        title = "Permanently delete?",
        text = "Penalty will be permanently deleted and can't be restored!",
        onDismissRequest = { },
        onConfirmClicked = { },
        onDismissButton = { }
    )
}
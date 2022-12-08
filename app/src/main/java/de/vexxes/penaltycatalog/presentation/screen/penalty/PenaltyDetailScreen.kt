package de.vexxes.penaltycatalog.presentation.screen.penalty

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import de.vexxes.penaltycatalog.R
import de.vexxes.penaltycatalog.component.BackDeleteEditTopBar
import de.vexxes.penaltycatalog.domain.model.Penalty
import de.vexxes.penaltycatalog.viewmodels.PenaltyViewModel

@Composable
fun PenaltyDetailScreen(
    penaltyViewModel: PenaltyViewModel,
    onBackClicked: () -> Unit,
    onDeleteClicked: (String) -> Unit,
    onEditClicked: (String) -> Unit,
) {

    val penalty by penaltyViewModel.penalty
    var showAlertDialog by remember { mutableStateOf(false) }

    BackHandler {
        onBackClicked()
    }

    if(showAlertDialog) {
        DeleteAlertDialog(
            onDismissRequest = {
                showAlertDialog = false
            },
            onConfirmClicked = {
                showAlertDialog = false
                onDeleteClicked(penalty._id)
            },
            onDismissButton = {
                showAlertDialog = false
            }
        )
    }

    PenaltyDetailScreen(
        penalty = penalty,
        onBackClicked = onBackClicked,
        onDeleteClicked = { showAlertDialog = true },
        onEditClicked = { onEditClicked(penalty._id) }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun PenaltyDetailScreen(
    penalty: Penalty,
    onBackClicked: () -> Unit,
    onDeleteClicked: () -> Unit,
    onEditClicked: () -> Unit
) {
    Scaffold(
        topBar = {
            BackDeleteEditTopBar(
                onBackClicked = onBackClicked,
                onDeleteClicked = onDeleteClicked,
                onEditClicked = onEditClicked
            )
        },

        content = {
            Box(
                modifier = Modifier.padding(it)
            ) {
                PenaltyDetailContent(penalty = penalty)
            }
        }
    )
}

@Composable
private fun DeleteAlertDialog(
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
        title = { Text(text = "Permanently delete?") },
        text = { Text(text = "Penalty will be deleted permanently and can't be restored") }
    )
}

@Preview(showBackground = true)
@Composable
private fun PenaltyDetailScreenPreview() {
    val penalty = Penalty(
        _id = "63717e8314ab74703f0ab5cb",
        name = "Getränke zur Besprechung",
        nameOfCategory = "Sonstiges",
        description = "Mitzubringen in alphabetischer Reihenfolge nach dem Freitagstraining",
        isBeer = true,
        value = 1
    )

    PenaltyDetailScreen(
        penalty = penalty,
        onBackClicked = { },
        onDeleteClicked = { },
        onEditClicked = { }
    )
}

@Preview(showBackground = true)
@Composable
private fun DeleteAlertDialogPreview() {
    DeleteAlertDialog(
        onDismissRequest = { },
        onConfirmClicked = { },
        onDismissButton = { }
    )
}
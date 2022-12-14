package de.vexxes.penaltycatalog.presentation.screen.penalty

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import de.vexxes.penaltycatalog.component.BackDeleteEditTopBar
import de.vexxes.penaltycatalog.component.DeleteAlertDialog
import de.vexxes.penaltycatalog.domain.uistate.PenaltyUiState
import de.vexxes.penaltycatalog.viewmodels.PenaltyViewModel

@Composable
fun PenaltyDetailScreen(
    penaltyViewModel: PenaltyViewModel,
    onBackClicked: () -> Unit,
    onDeleteClicked: (String) -> Unit,
    onEditClicked: (String) -> Unit,
) {
    val penaltyUiState by penaltyViewModel.penaltyUiState
    var showAlertDialog by remember { mutableStateOf(false) }

    BackHandler {
        onBackClicked()
    }

    if(showAlertDialog) {
        DeleteAlertDialog(
            title = "Permanently delete?",
            text = "Penalty will be deleted permanently and can't be restored",
            onDismissRequest = { showAlertDialog = false },
            onConfirmClicked = {
                showAlertDialog = false
                onDeleteClicked(penaltyUiState.id)
            },
            onDismissButton = {
                showAlertDialog = false
            }
        )
    }

    PenaltyDetailScreen(
        penaltyUiState = penaltyUiState,
        onBackClicked = onBackClicked,
        onDeleteClicked = { showAlertDialog = true },
        onEditClicked = { onEditClicked(penaltyUiState.id) }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun PenaltyDetailScreen(
    penaltyUiState: PenaltyUiState,
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
                PenaltyDetailContent(penaltyUiState = penaltyUiState)
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
private fun PenaltyDetailScreenPreview() {
    val penaltyUiState = PenaltyUiState(
        id = "63717e8314ab74703f0ab5cb",
        name = "Getr??nke zur Besprechung",
        categoryName = "Sonstiges",
        description = "Mitzubringen in alphabetischer Reihenfolge nach dem Freitagstraining",
        isBeer = true,
        value = "1"
    )

    PenaltyDetailScreen(
        penaltyUiState = penaltyUiState,
        onBackClicked = { },
        onDeleteClicked = { },
        onEditClicked = { }
    )
}
package de.vexxes.penaltycatalog.presentation.screen.penaltyReceived

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
import de.vexxes.penaltycatalog.domain.uievent.PenaltyReceivedUiEvent
import de.vexxes.penaltycatalog.domain.uistate.PenaltyReceivedUiState
import de.vexxes.penaltycatalog.domain.uistate.penaltyReceivedUiStateExample1
import de.vexxes.penaltycatalog.viewmodels.PenaltyReceivedViewModel
import kotlinx.datetime.LocalDate

@Composable
fun PenaltyReceivedDetailScreen(
    penaltyReceivedViewModel: PenaltyReceivedViewModel,
    onBackClicked: () -> Unit,
    onDeleteClicked: (String) -> Unit,
    onEditClicked: (String) -> Unit
) {
    val penaltyReceivedUiState by penaltyReceivedViewModel.penaltyReceivedUiState
    var showAlertDialog by remember { mutableStateOf(false) }

    BackHandler {
        onBackClicked()
    }

    if (showAlertDialog) {
        DeleteAlertDialog(
            title = "Permanently delete?",
            text = "Penalty Received will be deleted permanently and can't be restored",
            onDismissRequest = { showAlertDialog = false },
            onConfirmClicked = {
                showAlertDialog = false
                onDeleteClicked(penaltyReceivedUiState.id)
            },
            onDismissButton = { showAlertDialog = false }
        )
    }

    PenaltyReceivedDetailScreen(
        penaltyReceivedUiState = penaltyReceivedUiState,
        onBackClicked = onBackClicked,
        onDeleteClicked = { showAlertDialog = true },
        onEditClicked = { onEditClicked(penaltyReceivedUiState.id) },
        onPaidStateChanged = { penaltyReceivedViewModel.onPenaltyReceivedUiEvent(PenaltyReceivedUiEvent.PenaltyPaidChanged(it)) }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun PenaltyReceivedDetailScreen(
    penaltyReceivedUiState: PenaltyReceivedUiState,
    onBackClicked: () -> Unit,
    onDeleteClicked: () -> Unit,
    onEditClicked: () -> Unit,
    onPaidStateChanged: (LocalDate?) -> Unit
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
            Box(modifier = Modifier.padding(it)) {
                PenaltyReceivedDetailContent(
                    penaltyReceivedUiState = penaltyReceivedUiState,
                    onPaidStateChanged = onPaidStateChanged
                )
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
private fun PenaltyReceivedDetailScreenPreview() {
    PenaltyReceivedDetailScreen(
        penaltyReceivedUiState = penaltyReceivedUiStateExample1(),
        onBackClicked = { },
        onDeleteClicked = { },
        onEditClicked = { },
        onPaidStateChanged = { }
    )
}
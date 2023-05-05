package de.vexxes.penaltycatalog.presentation.screen.penaltyType

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
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
import de.vexxes.penaltycatalog.domain.uistate.PenaltyTypeUiState
import de.vexxes.penaltycatalog.domain.uistate.penaltyTypeUiStateExample1
import de.vexxes.penaltycatalog.ui.theme.PenaltyCatalogTheme
import de.vexxes.penaltycatalog.viewmodels.PenaltyTypeViewModel

@Composable
fun PenaltyTypeDetailScreen(
    penaltyTypeViewModel: PenaltyTypeViewModel,
    onBackClicked: () -> Unit,
    onDeleteClicked: (String) -> Unit,
    onEditClicked: (String) -> Unit,
) {
    val penaltyUiState by penaltyTypeViewModel.penaltyTypeUiState
    var showAlertDialog by remember { mutableStateOf(false) }

    if (showAlertDialog) {
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

    PenaltyTypeDetailScreen(
        penaltyTypeUiState = penaltyUiState,
        onBackClicked = onBackClicked,
        onDeleteClicked = { showAlertDialog = true },
        onEditClicked = { onEditClicked(penaltyUiState.id) }
    )
}

@Composable
private fun PenaltyTypeDetailScreen(
    penaltyTypeUiState: PenaltyTypeUiState,
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
                PenaltyTypeDetailContent(penaltyTypeUiState = penaltyTypeUiState)
            }
        }
    )
}

@Preview(name = "Light Theme", showBackground = true)
@Preview(name = "Dark Theme", showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
private fun PenaltyTypeDetailScreenPreview() {
    PenaltyCatalogTheme {
        PenaltyTypeDetailScreen(
            penaltyTypeUiState = penaltyTypeUiStateExample1(),
            onBackClicked = { },
            onDeleteClicked = { },
            onEditClicked = { }
        )
    }
}
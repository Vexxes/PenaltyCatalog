package de.vexxes.penaltycatalog.presentation.screen.cancel

import android.content.res.Configuration
import androidx.activity.compose.BackHandler
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
import de.vexxes.penaltycatalog.domain.uistate.CancellationUiState
import de.vexxes.penaltycatalog.domain.uistate.cancellationUiStateExample1
import de.vexxes.penaltycatalog.ui.theme.PenaltyCatalogTheme
import de.vexxes.penaltycatalog.viewmodels.CancellationViewModel

@Composable
fun CancellationDetailScreen(
    cancellationViewModel: CancellationViewModel,
    onBackClicked: () -> Unit,
    onDeleteClicked: (String) -> Unit,
    onEditClicked: (String) -> Unit
) {
    val cancellationUiState by cancellationViewModel.cancellationUiState
    var showAlertDialog by remember { mutableStateOf(false) }

    BackHandler {
        onBackClicked()
    }

    if (showAlertDialog) {
        DeleteAlertDialog(
            title = "Permanently delete?",
            text = "Cancellation will be deleted permanently and can't be restored",
            onDismissRequest = { showAlertDialog = false },
            onConfirmClicked = {
                showAlertDialog = false
                onDeleteClicked(cancellationUiState.id)
            },
            onDismissButton = { showAlertDialog = false}
        )
    }

    CancellationDetailScreen(
        cancellationUiState = cancellationUiState,
        onBackClicked = onBackClicked,
        onDeleteClicked = { showAlertDialog = true },
        onEditClicked = { onEditClicked(cancellationUiState.id) }
    )

}

@Composable
private fun CancellationDetailScreen(
    cancellationUiState: CancellationUiState,
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
            Box(modifier = Modifier.padding(it)) {
                CancellationDetailContent(
                    cancellationUiState = cancellationUiState
                )
            }
        }
    )
}

@Composable
@Preview(name = "Light Theme", showBackground = true)
@Preview(name = "Dark Theme", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
private fun EventDetailScreenPreview() {
    PenaltyCatalogTheme {
        CancellationDetailScreen(
            cancellationUiState = cancellationUiStateExample1(),
            onBackClicked = { },
            onDeleteClicked = { },
            onEditClicked = { }
        )
    }
}
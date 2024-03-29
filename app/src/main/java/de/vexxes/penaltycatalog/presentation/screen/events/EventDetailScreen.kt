package de.vexxes.penaltycatalog.presentation.screen.events

import android.content.Intent
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.net.Uri
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import de.vexxes.penaltycatalog.component.BackDeleteEditTopBar
import de.vexxes.penaltycatalog.component.DeleteAlertDialog
import de.vexxes.penaltycatalog.domain.uistate.EventUiState
import de.vexxes.penaltycatalog.domain.uistate.eventUiStateExample1
import de.vexxes.penaltycatalog.ui.theme.PenaltyCatalogTheme
import de.vexxes.penaltycatalog.viewmodels.EventViewModel

@Composable
fun EventDetailScreen(
    eventViewModel: EventViewModel,
    onBackClicked: () -> Unit,
    onDeleteClicked: (String) -> Unit,
    onEditClicked: (String) -> Unit
) {
    val eventUiState by eventViewModel.eventUiState
    var showAlertDialog by remember { mutableStateOf(false) }
    val context = LocalContext.current

    BackHandler {
        onBackClicked()
    }

    if (showAlertDialog) {
        DeleteAlertDialog(
            title = "Permanently delete?",
            text = "Event will be deleted permanently and can't be restored",
            onDismissRequest = { showAlertDialog = false },
            onConfirmClicked = {
                showAlertDialog = false
                onDeleteClicked(eventUiState.id)
            },
            onDismissButton = { showAlertDialog = false}
        )
    }

    EventDetailScreen(
        eventUiState = eventUiState,
        onBackClicked = onBackClicked,
        onDeleteClicked = { showAlertDialog = true },
        onEditClicked = { onEditClicked(eventUiState.id) },
        onMapsClicked = {
            val gmmIntentUri = Uri.parse("geo:0,0?q=" + Uri.encode(eventUiState.address))
            val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
            mapIntent.setPackage("com.google.android.apps.maps")
            context.startActivity(mapIntent)
        },
        onAvailabilityChanged = { eventViewModel.changePlayerAvailability(it) }
    )
}

@Composable
private fun EventDetailScreen(
    eventUiState: EventUiState,
    onBackClicked: () -> Unit,
    onDeleteClicked: () -> Unit,
    onEditClicked: () -> Unit,
    onMapsClicked: () -> Unit,
    onAvailabilityChanged: (String) -> Unit
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
                EventDetailContent(
                    eventUiState = eventUiState,
                    onMapsClicked = onMapsClicked,
                    onAvailabilityChanged = onAvailabilityChanged
                )
            }
        }
    )
}

@Composable
@Preview(name = "Light Theme", showBackground = true)
@Preview(name = "Dark Theme", showBackground = true, uiMode = UI_MODE_NIGHT_YES)
private fun EventDetailScreenPreview() {
    PenaltyCatalogTheme {
        EventDetailScreen(
            eventUiState = eventUiStateExample1(),
            onBackClicked = { },
            onDeleteClicked = { },
            onEditClicked = { },
            onMapsClicked = { },
            onAvailabilityChanged = {  }
        )
    }
}
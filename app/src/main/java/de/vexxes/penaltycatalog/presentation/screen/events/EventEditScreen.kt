package de.vexxes.penaltycatalog.presentation.screen.events

import android.content.res.Configuration
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import de.vexxes.penaltycatalog.component.BackSaveTopBar
import de.vexxes.penaltycatalog.domain.enums.EventType
import de.vexxes.penaltycatalog.domain.uievent.EventUiEvent
import de.vexxes.penaltycatalog.domain.uistate.EventUiState
import de.vexxes.penaltycatalog.domain.uistate.eventUiStateExample1
import de.vexxes.penaltycatalog.ui.theme.PenaltyCatalogTheme
import de.vexxes.penaltycatalog.viewmodels.EventViewModel
import kotlinx.datetime.LocalDateTime

@Composable
fun EventEditScreen(
    eventViewModel: EventViewModel,
    onBackClicked: () -> Unit,
    onSaveClicked: (String?) -> Unit
) {
    val eventUiState by eventViewModel.eventUiState

    BackHandler {
        onBackClicked()
    }

    EventEditScaffold(
        eventUiState = eventUiState,
        onEventTitleChanged = { eventViewModel.onEventUiEvent(EventUiEvent.TitleChanged(it)) },
        onEventTypeChanged = { eventViewModel.onEventUiEvent(EventUiEvent.TypeChanged(it)) },
        onStartOfEventChanged = { eventViewModel.onEventUiEvent(EventUiEvent.StartOfEventChanged(it)) },
        onStartOfMeetingChanged = { eventViewModel.onEventUiEvent(EventUiEvent.StartOfMeetingChanged(it)) },
        onAddressChanged = { eventViewModel.onEventUiEvent(EventUiEvent.AddressChanged(it)) },
        onDescriptionChanged = { eventViewModel.onEventUiEvent(EventUiEvent.DescriptionChanged(it)) },
        onBackClicked = onBackClicked,
        onSaveClicked = onSaveClicked
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun EventEditScaffold(
    eventUiState: EventUiState,
    onEventTitleChanged: (String) -> Unit,
    onEventTypeChanged: (EventType) -> Unit,
    onStartOfEventChanged: (LocalDateTime) -> Unit,
    onStartOfMeetingChanged: (LocalDateTime) -> Unit,
    onAddressChanged: (String) -> Unit,
    onDescriptionChanged: (String) -> Unit,
    onBackClicked: () -> Unit,
    onSaveClicked: (String?) -> Unit
) {

    Scaffold(
        topBar = {
            BackSaveTopBar(
                onBackClicked = onBackClicked,
                onSaveClicked = { onSaveClicked(eventUiState.id) }
            )
        },

        content = { paddingValues ->
            Box(modifier = Modifier.padding(paddingValues)) {
                EventEditContent(
                    eventUiState = eventUiState,
                    onEventTitleChanged = onEventTitleChanged,
                    onEventTypeChanged = onEventTypeChanged,
                    onStartOfEventChanged = onStartOfEventChanged,
                    onStartOfMeetingChanged = onStartOfMeetingChanged,
                    onAddressChanged = onAddressChanged,
                    onDescriptionChanged = onDescriptionChanged
                )
            }
        }
    )
}

@Composable
@Preview(name = "Light Theme", showBackground = true)
@Preview(name = "Dark Theme", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
private fun EventEditScreenPreview() {
    PenaltyCatalogTheme {
        EventEditScaffold(
            eventUiState = eventUiStateExample1(),
            onEventTitleChanged = { },
            onEventTypeChanged = { },
            onStartOfEventChanged = { },
            onStartOfMeetingChanged = { },
            onAddressChanged = { },
            onDescriptionChanged = { },
            onBackClicked = { },
            onSaveClicked = { }
        )
    }
}
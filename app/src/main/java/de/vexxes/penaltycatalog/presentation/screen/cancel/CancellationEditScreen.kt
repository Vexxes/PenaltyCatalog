package de.vexxes.penaltycatalog.presentation.screen.cancel

import android.content.res.Configuration
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import de.vexxes.penaltycatalog.component.BackSaveTopBar
import de.vexxes.penaltycatalog.domain.model.Event
import de.vexxes.penaltycatalog.domain.model.Player
import de.vexxes.penaltycatalog.domain.model.eventExample1
import de.vexxes.penaltycatalog.domain.model.eventExample2
import de.vexxes.penaltycatalog.domain.model.eventExample3
import de.vexxes.penaltycatalog.domain.model.playerExample
import de.vexxes.penaltycatalog.domain.uievent.CancellationUiEvent
import de.vexxes.penaltycatalog.domain.uistate.CancellationUiState
import de.vexxes.penaltycatalog.domain.uistate.cancellationUiStateExample1
import de.vexxes.penaltycatalog.ui.theme.PenaltyCatalogTheme
import de.vexxes.penaltycatalog.viewmodels.CancellationViewModel
import kotlinx.datetime.LocalDateTime

@Composable
fun CancellationEditScreen(
    cancellationViewModel: CancellationViewModel,
    onBackClicked: () -> Unit,
    onSaveClicked: (String?) -> Unit
) {
    val cancellationUiState by cancellationViewModel.cancellationUiState
    val players by cancellationViewModel.players
    val events by cancellationViewModel.events

    BackHandler {
        onBackClicked()
    }

    CancellationEditScaffold(
        cancellationUiState = cancellationUiState,
        players = players,
        events = events,
        onPlayerIdChanged = { cancellationViewModel.onCancellationUiEvent(CancellationUiEvent.PlayerIdChanged(it)) },
        onTimeOfCancellationChanged = { cancellationViewModel.onCancellationUiEvent(CancellationUiEvent.TimeOfCancellationChanged(it)) },
        onEventIdChanged = { cancellationViewModel.onCancellationUiEvent(CancellationUiEvent.EventIdChanged(it)) },
        onBackClicked = onBackClicked,
        onSaveClicked = onSaveClicked
    )
}

@Composable
private fun CancellationEditScaffold(
    cancellationUiState: CancellationUiState,
    players: List<Player>,
    events: List<Event>,
    onPlayerIdChanged: (String) -> Unit,
    onTimeOfCancellationChanged: (LocalDateTime) -> Unit,
    onEventIdChanged: (String) -> Unit,
    onBackClicked: () -> Unit,
    onSaveClicked: (String?) -> Unit
) {
    Scaffold(
        topBar = {
            BackSaveTopBar(
                onBackClicked = onBackClicked,
                onSaveClicked = { onSaveClicked(cancellationUiState.id) }
            )
        },

        content = { paddingValues ->
            Box(modifier = Modifier.padding(paddingValues)) {
                CancellationEditContent(
                    cancellationUiState = cancellationUiState,
                    players = players,
                    events = events,
                    onPlayerIdChanged = onPlayerIdChanged,
                    onTimeOfCancellationChanged = onTimeOfCancellationChanged,
                    onEventIdChanged = onEventIdChanged
                )
            }
        }
    )
}

@Composable
@Preview(name = "Light Theme", showBackground = true)
@Preview(name = "Dark Theme", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
private fun CancellationEditScreenPreview() {
    val players = listOf(
        playerExample(),
        playerExample(),
        playerExample()
    )

    val events = listOf(
        eventExample1(),
        eventExample2(),
        eventExample3()
    )

    PenaltyCatalogTheme {
        CancellationEditScaffold(
            cancellationUiState = cancellationUiStateExample1(),
            players = players,
            events = events,
            onPlayerIdChanged = { },
            onTimeOfCancellationChanged = { },
            onEventIdChanged = { },
            onBackClicked = { },
            onSaveClicked = { }
        )
    }
}
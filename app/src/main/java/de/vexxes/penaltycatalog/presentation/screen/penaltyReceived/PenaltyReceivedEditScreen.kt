package de.vexxes.penaltycatalog.presentation.screen.penaltyReceived

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
import de.vexxes.penaltycatalog.domain.model.PenaltyType
import de.vexxes.penaltycatalog.domain.model.penaltyExample1
import de.vexxes.penaltycatalog.domain.model.penaltyExample2
import de.vexxes.penaltycatalog.domain.model.penaltyExample3
import de.vexxes.penaltycatalog.domain.model.Player
import de.vexxes.penaltycatalog.domain.model.playerExample
import de.vexxes.penaltycatalog.domain.uievent.PenaltyReceivedUiEvent
import de.vexxes.penaltycatalog.domain.uistate.PenaltyReceivedUiState
import de.vexxes.penaltycatalog.domain.uistate.penaltyReceivedUiStateExample1
import de.vexxes.penaltycatalog.ui.theme.PenaltyCatalogTheme
import de.vexxes.penaltycatalog.viewmodels.PenaltyReceivedViewModel
import kotlinx.datetime.LocalDate

@Composable
fun PenaltyReceivedEditScreen(
    penaltyReceivedViewModel: PenaltyReceivedViewModel,
    onBackClicked: () -> Unit,
    onSaveClicked: (String?) -> Unit
) {
    val penaltyReceivedUiState by penaltyReceivedViewModel.penaltyReceivedUiState
    val penalties by penaltyReceivedViewModel.penalties
    val players by penaltyReceivedViewModel.players

    BackHandler {
        onBackClicked()
    }

    PenaltyReceivedEditScaffold(
        penaltyReceivedUiState = penaltyReceivedUiState,
        penalties = penalties,
        players = players,
        onPenaltyIdChanged = {
            penaltyReceivedViewModel.onPenaltyReceivedUiEvent(PenaltyReceivedUiEvent.PenaltyIdChanged(it))
        },
        onPlayerIdChanged = {
            penaltyReceivedViewModel.onPenaltyReceivedUiEvent(PenaltyReceivedUiEvent.PlayerIdChanged(it))
        },
        onTimeOfPenaltyChanged = {
            penaltyReceivedViewModel.onPenaltyReceivedUiEvent(PenaltyReceivedUiEvent.TimeOfPenaltyChanged(it))
        },
        onBackClicked = onBackClicked,
        onSaveClicked = onSaveClicked
    )
}

@Composable
private fun PenaltyReceivedEditScaffold(
    penaltyReceivedUiState: PenaltyReceivedUiState,
    penalties: List<PenaltyType>,
    players: List<Player>,
    onPenaltyIdChanged: (String) -> Unit,
    onPlayerIdChanged: (String) -> Unit,
    onTimeOfPenaltyChanged: (LocalDate) -> Unit,
    onBackClicked: () -> Unit,
    onSaveClicked: (String?) -> Unit
) {
    Scaffold(
        topBar = {
            BackSaveTopBar(
                onBackClicked = onBackClicked,
                onSaveClicked = { onSaveClicked(penaltyReceivedUiState.id) }
            )
        },

        content = { paddingValues ->
            Box(modifier = Modifier.padding(paddingValues)) {
                PenaltyReceivedEditContent(
                    penaltyReceivedUiState = penaltyReceivedUiState,
                    penaltyList = penalties,
                    playerList = players,
                    onPenaltyIdChanged = onPenaltyIdChanged,
                    onPlayerIdChanged = onPlayerIdChanged,
                    onTimeOfPenaltyChanged = onTimeOfPenaltyChanged
                )
            }
        },

    )
}

@Composable
@Preview(name = "Light Theme", showBackground = true)
@Preview(name = "Dark Theme", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
private fun PenaltyReceivedEditScreenPreview() {
    val penalties = listOf(
        penaltyExample1(),
        penaltyExample2(),
        penaltyExample3()
    )

    val players = listOf(
        playerExample(),
        playerExample(),
        playerExample()
    )

    PenaltyCatalogTheme {
        PenaltyReceivedEditScaffold(
            penaltyReceivedUiState = penaltyReceivedUiStateExample1(),
            penalties = penalties,
            players = players,
            onPenaltyIdChanged = { },
            onPlayerIdChanged = { },
            onTimeOfPenaltyChanged = { },
            onBackClicked = { },
            onSaveClicked = { }
        )
    }
}
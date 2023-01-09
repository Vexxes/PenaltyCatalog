package de.vexxes.penaltycatalog.presentation.screen.penaltyHistory

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
import de.vexxes.penaltycatalog.domain.model.Penalty
import de.vexxes.penaltycatalog.domain.model.penaltyExample1
import de.vexxes.penaltycatalog.domain.model.penaltyExample2
import de.vexxes.penaltycatalog.domain.model.penaltyExample3
import de.vexxes.penaltycatalog.domain.model.Player
import de.vexxes.penaltycatalog.domain.model.playerExample
import de.vexxes.penaltycatalog.domain.uievent.PenaltyHistoryUiEvent
import de.vexxes.penaltycatalog.domain.uistate.PenaltyHistoryUiState
import de.vexxes.penaltycatalog.domain.uistate.penaltyHistoryUiStateExample1
import de.vexxes.penaltycatalog.viewmodels.PenaltyHistoryViewModel
import java.time.LocalDate

@Composable
fun PenaltyHistoryEditScreen(
    penaltyHistoryViewModel: PenaltyHistoryViewModel,
    onBackClicked: () -> Unit,
    onSaveClicked: (String?) -> Unit
) {
    val penaltyHistoryUiState by penaltyHistoryViewModel.penaltyHistoryUiState
    val penalties by penaltyHistoryViewModel.penalties
    val players by penaltyHistoryViewModel.players

    println(players.toString())

    BackHandler {
        onBackClicked()
    }

    PenaltyHistoryEditScaffold(
        penaltyHistoryUiState = penaltyHistoryUiState,
        penalties = penalties,
        players = players,
        onPenaltyChanged = {
            penaltyHistoryViewModel.onPenaltyHistoryUiEvent(PenaltyHistoryUiEvent.PenaltyIdChanged(it))
        },
        onPlayerChanged = {
            penaltyHistoryViewModel.onPenaltyHistoryUiEvent(PenaltyHistoryUiEvent.PlayerIdChanged(it))
        },
        onTimeOfPenaltyChanged = {
            penaltyHistoryViewModel.onPenaltyHistoryUiEvent(PenaltyHistoryUiEvent.TimeOfPenaltyChanged(it))
        },
        onBackClicked = onBackClicked,
        onSaveClicked = onSaveClicked
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun PenaltyHistoryEditScaffold(
    penaltyHistoryUiState: PenaltyHistoryUiState,
    penalties: List<Penalty>,
    players: List<Player>,
    onPenaltyChanged: (String) -> Unit,
    onPlayerChanged: (String) -> Unit,
    onTimeOfPenaltyChanged: (LocalDate) -> Unit,
    onBackClicked: () -> Unit,
    onSaveClicked: (String?) -> Unit
) {
    Scaffold(
        topBar = {
            BackSaveTopBar(
                onBackClicked = onBackClicked,
                onSaveClicked = { onSaveClicked(penaltyHistoryUiState.id) }
            )
        },

        content = { paddingValues ->
            Box(modifier = Modifier.padding(paddingValues)) {
                PenaltyHistoryEditContent(
                    penaltyHistoryUiState = penaltyHistoryUiState,
                    penaltyList = penalties,
                    playerList = players,
                    onPenaltyChanged = onPenaltyChanged,
                    onPlayerChanged = onPlayerChanged,
                    onTimeOfPenaltyChanged = onTimeOfPenaltyChanged
                )
            }
        },

    )
}

@Composable
@Preview(showBackground = true)
private fun PenaltyHistoryEditScreenPreview() {
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

    PenaltyHistoryEditScaffold(
        penaltyHistoryUiState = penaltyHistoryUiStateExample1(),
        penalties = penalties,
        players = players,
        onPenaltyChanged = { },
        onPlayerChanged = { },
        onTimeOfPenaltyChanged = { },
        onBackClicked = { },
        onSaveClicked = { }
    )
}
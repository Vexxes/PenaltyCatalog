package de.vexxes.penaltycatalog.presentation.screen.history

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
import de.vexxes.penaltycatalog.domain.model.Player
import de.vexxes.penaltycatalog.domain.uievent.PenaltyHistoryUiEvent
import de.vexxes.penaltycatalog.domain.uistate.PenaltyHistoryUiState
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
        onPenaltyChanged = { id, name ->
            penaltyHistoryViewModel.onPenaltyHistoryUiEvent(PenaltyHistoryUiEvent.PenaltyNameChanged(id, name))
        },
        onPlayerChanged = {
            penaltyHistoryViewModel.onPenaltyHistoryUiEvent(PenaltyHistoryUiEvent.PlayerNameChanged(it))
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
    onPenaltyChanged: (String, String) -> Unit,
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

    val penaltyUiState = PenaltyHistoryUiState(
        id = "",
        playerName = "Thomas Schneider",
        penaltyName = "Verspätete Zahlung des Monatsbeitrag",
        penaltyValue = "5",
        penaltyIsBeer = false,
        timeOfPenalty = LocalDate.now(),
        penaltyPaid = true
    )

    val penalties = listOf(
        Penalty(
            _id = "",
            name = "Monatsbeitrag",
            categoryName = "Monatsbeitrag",
            description = "",
            isBeer = false,
            value = "5"
        ),
        Penalty(
            _id = "",
            name = "Verspätete Zahlung des Monatsbeitrag",
            categoryName = "Monatsbeitrag",
            description = "zzgl. pro Monat",
            isBeer = false,
            value = "5"
        ),
        Penalty(
            _id = "",
            name = "Getränke zur Besprechung",
            categoryName = "Sonstiges",
            description = "Mitzubringen in alphabetischer Reihenfolge nach dem Freitagstraining",
            isBeer = true,
            value = "1"
        )
    )

    val players = listOf(
        Player.generateFaker(),
        Player.generateFaker(),
        Player.generateFaker()
    )


    PenaltyHistoryEditScaffold(
        penaltyHistoryUiState = penaltyUiState,
        penalties = penalties,
        players = players,
        onPenaltyChanged = { _, _ -> },
        onPlayerChanged = { },
        onTimeOfPenaltyChanged = { },
        onBackClicked = { },
        onSaveClicked = { }
    )
}
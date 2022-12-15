package de.vexxes.penaltycatalog.presentation.screen.player

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import de.vexxes.penaltycatalog.component.BackSaveTopBar
import de.vexxes.penaltycatalog.domain.model.Player
import de.vexxes.penaltycatalog.domain.uievent.PlayerUiEvent
import de.vexxes.penaltycatalog.domain.uistate.PlayerUiState
import de.vexxes.penaltycatalog.viewmodels.PlayerViewModel

@Composable
fun PlayerEditScreen(
    playerViewModel: PlayerViewModel,
    onBackClicked: () -> Unit,
    onSaveClicked: (String?) -> Unit
) {
    val playerUiState by playerViewModel.playerUiState
    val lastResponse by playerViewModel.lastResponse

    // Go back after lastResponse message is true
    LaunchedEffect(key1 = lastResponse) {
        if (lastResponse.success) {
            onBackClicked()
        }
    }

    BackHandler {
        onBackClicked()
    }

    PlayerEditScaffold(
        playerUiState = playerUiState,
        onNumberChanged = { playerViewModel.onEvent(PlayerUiEvent.NumberChanged(it)) },
        onFirstNameChanged = { playerViewModel.onEvent(PlayerUiEvent.FirstNameChanged(it)) },
        onLastNameChanged = { playerViewModel.onEvent(PlayerUiEvent.LastNameChanged(it)) },
        onBirthdayChanged = { playerViewModel.onEvent(PlayerUiEvent.BirthdayChanged(it)) },
        onStreetChanged = { playerViewModel.onEvent(PlayerUiEvent.StreetChanged(it)) },
        onZipcodeChanged = { playerViewModel.onEvent(PlayerUiEvent.ZipcodeChanged(it)) },
        onCityChanged = { playerViewModel.onEvent(PlayerUiEvent.CityChanged(it)) },
        onPlayedGamesChanged = { playerViewModel.onEvent(PlayerUiEvent.PlayedGamesChanged(it)) },
        onGoalsChanged = { playerViewModel.onEvent(PlayerUiEvent.GoalsChanged(it)) },
        onYellowCardsChanged = { playerViewModel.onEvent(PlayerUiEvent.YellowCardsChanged(it)) },
        onTwoMinutesChanged = { playerViewModel.onEvent(PlayerUiEvent.TwoMinutesChanged(it)) },
        onRedCardsChanged = { playerViewModel.onEvent(PlayerUiEvent.RedCardsChanged(it)) },
        onBackClicked = onBackClicked,
        onSaveClicked = onSaveClicked
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun PlayerEditScaffold(
    playerUiState: PlayerUiState,
    onNumberChanged: (String) -> Unit,
    onFirstNameChanged: (String) -> Unit,
    onLastNameChanged: (String) -> Unit,
    onBirthdayChanged: (String) -> Unit,
    onStreetChanged: (String) -> Unit,
    onZipcodeChanged: (String) -> Unit,
    onCityChanged: (String) -> Unit,
    onPlayedGamesChanged: (String) -> Unit,
    onGoalsChanged: (String) -> Unit,
    onYellowCardsChanged: (String) -> Unit,
    onTwoMinutesChanged: (String) -> Unit,
    onRedCardsChanged: (String) -> Unit,
    onBackClicked: () -> Unit,
    onSaveClicked: (String?) -> Unit,
) {
    Scaffold(
        topBar = {
            BackSaveTopBar(
                onBackClicked = onBackClicked,
                onSaveClicked = { onSaveClicked(playerUiState.id) }
            )
        },

        content = { paddingValues ->
            Box(
                modifier = Modifier.padding(paddingValues)
            ) {
                PlayerEditContent(
                    playerUiState = playerUiState,
                    onNumberChanged = onNumberChanged,
                    onFirstNameChanged = onFirstNameChanged,
                    onLastNameChanged = onLastNameChanged,
                    onBirthdayChanged = onBirthdayChanged,
                    onStreetChanged = onStreetChanged,
                    onZipcodeChanged = onZipcodeChanged,
                    onCityChanged = onCityChanged,
                    onPlayedGamesChanged = onPlayedGamesChanged,
                    onGoalsChanged = onGoalsChanged,
                    onYellowCardsChanged = onYellowCardsChanged,
                    onTwoMinutesChanged = onTwoMinutesChanged,
                    onRedCardsChanged = onRedCardsChanged,
                )
            }
        },
    )
}

@Preview(showBackground = true)
@Composable
private fun PlayerEditScreenPreview() {

    val player = Player.generateFaker()
    val playerUiState = PlayerUiState(
        id = player._id,
        number = player.number.toString(),
        firstName = player.firstName,
        lastName = player.lastName,
        birthday = player.birthday,
        street = player.street,
        zipcode = player.zipcode.toString(),
        city = player.city,
        playedGames = player.playedGames.toString(),
        goals = player.goals.toString(),
        yellowCards = player.yellowCards.toString(),
        twoMinutes = player.twoMinutes.toString(),
        redCards = player.redCards.toString(),
    )

    PlayerEditScaffold(
        playerUiState = playerUiState,
        onNumberChanged = { },
        onFirstNameChanged = { },
        onLastNameChanged = { },
        onBirthdayChanged = { },
        onStreetChanged = { },
        onZipcodeChanged = { },
        onCityChanged = { },
        onPlayedGamesChanged = { },
        onGoalsChanged = { },
        onYellowCardsChanged = { },
        onTwoMinutesChanged = { },
        onRedCardsChanged = { },
        onBackClicked = { },
        onSaveClicked = { },
    )
}
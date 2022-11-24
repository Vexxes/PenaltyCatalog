package de.vexxes.penaltycatalog.presentation.screen.player

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import de.vexxes.penaltycatalog.component.BackEditTopBar
import de.vexxes.penaltycatalog.component.BackSaveTopBar
import de.vexxes.penaltycatalog.domain.model.Player
import de.vexxes.penaltycatalog.viewmodels.PlayerViewModel

@Composable
fun PlayerScreen(
    playerViewModel: PlayerViewModel,
    onBackClicked: () -> Unit,
    onEditClicked: (String) -> Unit,
    onSaveClicked: (String) -> Unit
) {
    val player by playerViewModel.player
    val id by playerViewModel.id
    val number by playerViewModel.number
    val firstName by playerViewModel.firstName
    val lastName by playerViewModel.lastName
    val birthday by playerViewModel.birthday
    val street by playerViewModel.street
    val zipcode by playerViewModel.zipcode
    val city by playerViewModel.city
    val playedGames by playerViewModel.playedGames
    val goals by playerViewModel.goals
    val yellowCards by playerViewModel.yellowCards
    val twoMinutes by playerViewModel.twoMinutes
    val redCards by playerViewModel.redCards

    BackHandler {
        onBackClicked()
    }

    if(player._id != "") {
        PlayerDetailScreen(
            player = player,
            onBackClicked = onBackClicked,
            onEditClicked = { onEditClicked(player._id) }
        )
    } else {
        PlayerNewScreen(
            id = id,
            number = number,
            onNumberChanged = { playerViewModel.number.value = it },
            firstName = firstName,
            onFirstNameChanged = { playerViewModel.firstName.value = it },
            lastName = lastName,
            onLastNameChanged = { playerViewModel.lastName.value = it },
            birthday = birthday,
            onBirthdayChanged = { playerViewModel.birthday.value = it },
            street = street,
            onStreetChanged = { playerViewModel.street.value = it },
            zipcode = zipcode,
            onZipcodeChanged = { playerViewModel.zipcode.value = it },
            city = city,
            onCityChanged = { playerViewModel.city.value = it },
            playedGames = playedGames,
            onPlayedGamesChanged = { playerViewModel.playedGames.value = it},
            goals = goals,
            onGoalsChanged = { playerViewModel.goals.value = it},
            yellowCards = yellowCards,
            onYellowCardsChanged = { playerViewModel.yellowCards.value = it},
            twoMinutes = twoMinutes,
            onTwoMinutesChanged = { playerViewModel.twoMinutes.value = it },
            redCards = redCards,
            onRedCardsChanged = { playerViewModel.redCards.value = it },
            onBackClicked = onBackClicked,
            onSaveClicked = { onSaveClicked(id) }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun PlayerDetailScreen(
    player: Player,
    onBackClicked: () -> Unit,
    onEditClicked: (String) -> Unit
) {
    Scaffold(
        topBar = {
            BackEditTopBar(
                onBackClicked = { onBackClicked() },
                onEditClicked = { onEditClicked(player._id) }
            )
        },

        content = {
            Box(
                modifier = Modifier.padding(it)
            ) {
                PlayerDetailContent(player = player)
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun PlayerNewScreen(
    id: String,
    number: String,
    onNumberChanged: (String) -> Unit,
    firstName: String,
    onFirstNameChanged: (String) -> Unit,
    lastName: String,
    onLastNameChanged: (String) -> Unit,
    birthday: String,
    onBirthdayChanged: (String) -> Unit,
    street: String,
    onStreetChanged: (String) -> Unit,
    zipcode: String,
    onZipcodeChanged: (String) -> Unit,
    city: String,
    onCityChanged: (String) -> Unit,
    playedGames: String,
    onPlayedGamesChanged: (String) -> Unit,
    goals: String,
    onGoalsChanged: (String) -> Unit,
    yellowCards: String,
    onYellowCardsChanged: (String) -> Unit,
    twoMinutes: String,
    onTwoMinutesChanged: (String) -> Unit,
    redCards: String,
    onRedCardsChanged: (String) -> Unit,
    onBackClicked: () -> Unit,
    onSaveClicked: (String) -> Unit
) {
    Scaffold(
        topBar = {
            BackSaveTopBar(
                onBackClicked = { onBackClicked() },
                onSaveClicked = { onSaveClicked(id) }
            )
        },

        content = { paddingValues ->
            Box(
                modifier = Modifier.padding(paddingValues)
            ) {
                PlayerNewContent(
                    number = number,
                    onNumberChanged = onNumberChanged,
                    firstName = firstName,
                    onFirstNameChanged = onFirstNameChanged,
                    lastName = lastName,
                    onLastNameChanged = onLastNameChanged,
                    birthday = birthday,
                    onBirthdayChanged = onBirthdayChanged,
                    street = street,
                    onStreetChanged = onStreetChanged,
                    zipcode = zipcode,
                    onZipcodeChanged = onZipcodeChanged,
                    city = city,
                    onCityChanged = onCityChanged,
                    playedGames = playedGames,
                    onPlayedGamesChanged = onPlayedGamesChanged,
                    goals = goals,
                    onGoalsChanged = onGoalsChanged,
                    yellowCards = yellowCards,
                    onYellowCardsChanged = onYellowCardsChanged,
                    twoMinutes = twoMinutes,
                    onTwoMinutesChanged = onTwoMinutesChanged,
                    redCards = redCards,
                    onRedCardsChanged = onRedCardsChanged
                )
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
private fun PlayerDetailScreenPreview() {
    val player = Player(
        _id = "63717e8314ab74703f0ab5cb",
        number = 5,
        firstName = "Thomas",
        lastName = "Schneider",
        street = "Bussardweg 3C",
        birthday = "21.06.1997",
        zipcode = 49424,
        city = "Goldenstedt",
        playedGames = 4,
        goals = 16,
        yellowCards = 1,
        twoMinutes = 1,
        redCards = 0
    )
    PlayerDetailScreen(
        player = player,
        onBackClicked = { },
        onEditClicked = { }
    )
}

@Preview(showBackground = true)
@Composable
private fun PlayerNewScreenPreview() {
    PlayerNewScreen(
        id = "",
        number = "",
        onNumberChanged = { },
        firstName = "",
        onFirstNameChanged = { },
        lastName = "",
        onLastNameChanged = { },
        birthday = "",
        onBirthdayChanged = { },
        street = "",
        onStreetChanged = { },
        zipcode = "",
        onZipcodeChanged = { },
        city = "",
        onCityChanged = { },
        playedGames = "",
        onPlayedGamesChanged = { },
        goals = "",
        onGoalsChanged = { },
        yellowCards = "",
        onYellowCardsChanged = { },
        twoMinutes = "",
        onTwoMinutesChanged = { },
        redCards = "",
        onRedCardsChanged = { },
        onBackClicked = { },
        onSaveClicked = { }
    )
}
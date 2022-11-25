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
import de.vexxes.penaltycatalog.component.BackSaveTopBar
import de.vexxes.penaltycatalog.viewmodels.PlayerViewModel

@Composable
fun PlayerEditScreen(
    playerViewModel: PlayerViewModel,
    onBackClicked: () -> Unit,
    onSaveClicked: (String?) -> Unit
) {
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

    PlayerEditScreen(
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun PlayerEditScreen(
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
    onSaveClicked: (String?) -> Unit
) {
    Scaffold(
        topBar = {
            BackSaveTopBar(
                onBackClicked = onBackClicked,
                onSaveClicked = { onSaveClicked(id) }
            )
        },

        content = { paddingValues ->
            Box(
                modifier = Modifier.padding(paddingValues)
            ) {
                PlayerEditContent(
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
private fun PlayerEditScreenPreview() {
    PlayerEditScreen(
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
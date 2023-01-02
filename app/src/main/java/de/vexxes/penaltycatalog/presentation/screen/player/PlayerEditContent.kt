package de.vexxes.penaltycatalog.presentation.screen.player

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import de.vexxes.penaltycatalog.R
import de.vexxes.penaltycatalog.component.InputOutlinedField
import de.vexxes.penaltycatalog.domain.model.playerExample
import de.vexxes.penaltycatalog.domain.uistate.PlayerUiState

@Composable
fun PlayerEditContent(
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
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        PlayerNumber(
            number = playerUiState.number,
            onNumberChanged = onNumberChanged,
            numberError = playerUiState.numberError
        )

        PlayerName(
            firstName = playerUiState.firstName,
            onFirstNameChanged = onFirstNameChanged,
            lastName = playerUiState.lastName,
            onLastNameChanged = onLastNameChanged,
            firstNameError = playerUiState.firstNameError,
            lastNameError = playerUiState.lastNameError
        )

        PlayerBirthday(
            birthday = playerUiState.birthday,
            onBirthdayChanged = onBirthdayChanged
        )

        PlayerAddress(
            street = playerUiState.street,
            onStreetChanged = onStreetChanged,
            zipcode = playerUiState.zipcode,
            onZipcodeChanged = onZipcodeChanged,
            city = playerUiState.city,
            onCityChanged = onCityChanged
        )

        PlayerStats(
            playedGames = playerUiState.playedGames,
            onPlayedGamesChanged = onPlayedGamesChanged,
            goals = playerUiState.goals,
            onGoalsChanged = onGoalsChanged,
            yellowCards = playerUiState.yellowCards,
            onYellowCardsChanged = onYellowCardsChanged,
            twoMinutes = playerUiState.twoMinutes,
            onTwoMinutesChanged = onTwoMinutesChanged,
            redCards = playerUiState.redCards,
            onRedCardsChanged = onRedCardsChanged
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PlayerEditContentPreview() {

    val player = playerExample()
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

    PlayerEditContent(
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
    )
}

@Composable
private fun PlayerNumber(
    number: String,
    onNumberChanged: (String) -> Unit,
    numberError: Boolean
) {
    InputOutlinedField(
        modifier = Modifier
            .padding(8.dp),
        text = number,
        onTextChanged = onNumberChanged,
        isError = numberError,
        label = stringResource(id = R.string.Number),
        required = true,
        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
    )
}

@Composable
private fun PlayerName(
    firstName: String,
    onFirstNameChanged: (String) -> Unit,
    lastName: String,
    onLastNameChanged: (String) -> Unit,
    firstNameError: Boolean,
    lastNameError: Boolean
) {
    Column {
        InputOutlinedField(
            modifier = Modifier
                .padding(8.dp),
            text = firstName,
            onTextChanged = onFirstNameChanged,
            isError = firstNameError,
            label = stringResource(id = R.string.FirstName),
            required = true
        )
        InputOutlinedField(
            modifier = Modifier
                .padding(8.dp),
            text = lastName,
            onTextChanged = onLastNameChanged,
            isError = lastNameError,
            label = stringResource(id = R.string.LastName),
            required = true
        )
    }
}

@Composable
private fun PlayerBirthday(
    birthday: String,
    onBirthdayChanged: (String) -> Unit
) {
    InputOutlinedField(
        modifier = Modifier
            .padding(8.dp),
        text = birthday,
        onTextChanged = onBirthdayChanged,
        label = stringResource(id = R.string.Birthday)
    )
}

@Composable
private fun PlayerAddress(
    street: String,
    onStreetChanged: (String) -> Unit,
    zipcode: String,
    onZipcodeChanged: (String) -> Unit,
    city: String,
    onCityChanged: (String) -> Unit
) {
    Column {
        InputOutlinedField(
            modifier = Modifier
                .padding(8.dp),
            text = street,
            onTextChanged = onStreetChanged,
            label = stringResource(id = R.string.Street)
        )
        Row {
            InputOutlinedField(
                modifier = Modifier
                    .padding(8.dp)
                    .weight(0.5f),
                text = zipcode,
                onTextChanged = onZipcodeChanged,
                label = stringResource(id = R.string.Zipcode),
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
            )
            InputOutlinedField(
                modifier = Modifier
                    .padding(8.dp)
                    .weight(0.5f),
                text = city,
                onTextChanged = onCityChanged,
                label = stringResource(id = R.string.City)
            )
        }
    }
}

@Composable
private fun PlayerStats(
    playedGames: String,
    onPlayedGamesChanged: (String) -> Unit,
    goals: String,
    onGoalsChanged: (String) -> Unit,
    yellowCards: String,
    onYellowCardsChanged: (String) -> Unit,
    twoMinutes: String,
    onTwoMinutesChanged: (String) -> Unit,
    redCards: String,
    onRedCardsChanged: (String) -> Unit
) {
    Column {
        Row {
            InputOutlinedField(
                modifier = Modifier
                    .padding(8.dp)
                    .weight(0.5f),
                text = playedGames,
                onTextChanged = onPlayedGamesChanged,
                label = stringResource(id = R.string.PlayedGames),
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
            )
            InputOutlinedField(
                modifier = Modifier
                    .padding(8.dp)
                    .weight(0.5f),
                text = goals,
                onTextChanged = onGoalsChanged,
                label = stringResource(id = R.string.Goals),
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
            )
        }
        Row {
            InputOutlinedField(
                modifier = Modifier
                    .padding(8.dp)
                    .weight(0.33f),
                text = yellowCards,
                onTextChanged = onYellowCardsChanged,
                label = stringResource(id = R.string.YellowCards),
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
            )
            InputOutlinedField(
                modifier = Modifier
                    .padding(8.dp)
                    .weight(0.33f),
                text = twoMinutes,
                onTextChanged = onTwoMinutesChanged,
                label = stringResource(id = R.string.TwoMinutes),
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
            )
            InputOutlinedField(
                modifier = Modifier
                    .padding(8.dp)
                    .weight(0.33f),
                text = redCards,
                onTextChanged = onRedCardsChanged,
                label = stringResource(id = R.string.RedCards),
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
            )
        }
    }
}
package de.vexxes.penaltycatalog.presentation.screen.player

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Cancel
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import de.vexxes.penaltycatalog.R

@Composable
fun PlayerEditContent(
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
    onRedCardsChanged: (String) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        PlayerNumber(
            number = number,
            onNumberChanged = onNumberChanged
        )

        PlayerName(
            firstName = firstName,
            onFirstNameChanged = onFirstNameChanged,
            lastName = lastName,
            onLastNameChanged = onLastNameChanged
        )

        PlayerBirthday(
            birthday = birthday,
            onBirthdayChanged = onBirthdayChanged
        )

        PlayerAddress(
            street = street,
            onStreetChanged = onStreetChanged,
            zipcode = zipcode,
            onZipcodeChanged = onZipcodeChanged,
            city = city,
            onCityChanged = onCityChanged
        )

        PlayerStats(
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

@Composable
private fun CancelIcon(
    onClick: () -> Unit
) {
    IconButton(
        onClick = {
            onClick()
        }
    ) {
        Icon(
            imageVector = Icons.Outlined.Cancel,
            contentDescription = stringResource(id = R.string.Cancel)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PlayerEditContentPreview() {
    PlayerEditContent(
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
    )
}

@Composable
private fun PlayerNumber(
    number: String,
    onNumberChanged: (String) -> Unit
) {
    InputOutlinedField(
        text = number,
        onTextChanged = onNumberChanged,
        label = stringResource(id = R.string.Number),
        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
    )
}

@Composable
private fun PlayerName(
    firstName: String,
    onFirstNameChanged: (String) -> Unit,
    lastName: String,
    onLastNameChanged: (String) -> Unit
) {
    Column {
        InputOutlinedField(
            text = firstName,
            onTextChanged = onFirstNameChanged,
            label = stringResource(id = R.string.FirstName)
        )
        InputOutlinedField(
            text = lastName,
            onTextChanged = onLastNameChanged,
            label = stringResource(id = R.string.LastName)
        )
    }
}

@Composable
private fun PlayerBirthday(
    birthday: String,
    onBirthdayChanged: (String) -> Unit
) {
    InputOutlinedField(
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
            text = street,
            onTextChanged = onStreetChanged,
            label = stringResource(id = R.string.Street)
        )
        Row {
            InputOutlinedField(
                modifier = Modifier.weight(0.5f),
                text = zipcode,
                onTextChanged = onZipcodeChanged,
                label = stringResource(id = R.string.Zipcode),
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
            )
            InputOutlinedField(
                modifier = Modifier.weight(0.5f),
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
                modifier = Modifier.weight(0.5f),
                text = playedGames,
                onTextChanged = onPlayedGamesChanged,
                label = stringResource(id = R.string.PlayedGames),
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
            )
            InputOutlinedField(
                modifier = Modifier.weight(0.5f),
                text = goals,
                onTextChanged = onGoalsChanged,
                label = stringResource(id = R.string.Goals),
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
            )
        }
        Row {
            InputOutlinedField(
                modifier = Modifier.weight(0.33f),
                text = yellowCards,
                onTextChanged = onYellowCardsChanged,
                label = stringResource(id = R.string.YellowCards),
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
            )
            InputOutlinedField(
                modifier = Modifier.weight(0.33f),
                text = twoMinutes,
                onTextChanged = onTwoMinutesChanged,
                label = stringResource(id = R.string.TwoMinutes),
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
            )
            InputOutlinedField(
                modifier = Modifier.weight(0.33f),
                text = redCards,
                onTextChanged = onRedCardsChanged,
                label = stringResource(id = R.string.RedCards),
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
            )
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun InputOutlinedField(
    modifier: Modifier = Modifier,
    text: String,
    onTextChanged: (String) -> Unit,
    label: String,
    keyboardOptions: KeyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Words)
) {
    var focused by remember { mutableStateOf(false) }

    OutlinedTextField(
        modifier = modifier
            .fillMaxWidth()
            .padding(4.dp)
            .onFocusChanged { focusState ->
                focused = focusState.isFocused
            },
        value = text,
        onValueChange = {
            onTextChanged(it)
        },
        label = {
            Text(text = label, softWrap = false, overflow = TextOverflow.Visible)
        },
        placeholder = {
            Text(text = label, softWrap = false, overflow = TextOverflow.Visible)
        },
        trailingIcon = {
            if (text.isNotBlank() && focused)
                CancelIcon(
                    onClick = { onTextChanged("") }
                )
        },
        keyboardOptions = keyboardOptions
    )
}
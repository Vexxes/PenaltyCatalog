package de.vexxes.penaltycatalog.presentation.screen.player

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import de.vexxes.penaltycatalog.R
import de.vexxes.penaltycatalog.domain.model.playerExample
import de.vexxes.penaltycatalog.domain.uistate.PlayerUiState
import de.vexxes.penaltycatalog.domain.visualTransformation.NumberCommaTransformation
import de.vexxes.penaltycatalog.ui.theme.Typography
import de.vexxes.penaltycatalog.ui.theme.colorSchemeSegButtons

@Composable
fun PlayerDetailContent(
    playerUiState: PlayerUiState
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        RowHeader(number = playerUiState.number, firstName = playerUiState.firstName, lastName = playerUiState.lastName)
        RowBirthday(birthday = playerUiState.birthday)
        RowAddress(street = playerUiState.street, zipcode = playerUiState.zipcode, city = playerUiState.city)
        RowOpenPenalties(openPenalties = playerUiState.sumPenalties)
        PlayerStats(
            playedGames = playerUiState.playedGames,
            goals = playerUiState.goals,
            yellowCards = playerUiState.yellowCards,
            twoMinutes = playerUiState.twoMinutes,
            redCards = playerUiState.redCards
        )
    }
}

@Composable
private fun TableColumn(
    text: String,
    textAlign: TextAlign? = null
) {
    Text(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 8.dp, end = 8.dp, top = 8.dp),
        text = text,
        textAlign = textAlign,
        style = Typography.bodyLarge
    )
}

@Composable
private fun RowHeader(
    number: String,
    firstName: String,
    lastName: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp)
    ) {
        Text(
            modifier = Modifier
                .padding(start = 8.dp)
                .weight(0.1f),
            text = number,
            style = Typography.headlineSmall,
            textAlign = TextAlign.Center
        )

        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 8.dp, end = 8.dp)
                .weight(0.9f),
            text = "$firstName $lastName",
            style = Typography.headlineSmall,
            textAlign = TextAlign.Center
        )
    }

    Divider(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 8.dp, end = 8.dp, top = 8.dp)
    )
}

@Composable
private fun RowBirthday(
    birthday: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(40.dp)
            .padding(top = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier
                .padding(start = 8.dp),
            text = stringResource(id = R.string.Birthday),
            style = Typography.bodyLarge
        )

        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 8.dp, end = 8.dp),
            text = birthday,
            style = Typography.bodyLarge,
            textAlign = TextAlign.Right
        )
    }

    Divider(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 8.dp, end = 8.dp, top = 8.dp)
    )
}

@Composable
private fun RowAddress(
    street: String,
    zipcode: String,
    city: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp)
    ) {
        Text(
            modifier = Modifier
                .padding(start = 8.dp)
                .align(Alignment.CenterVertically),
            text = stringResource(id = R.string.Address),
            style = Typography.bodyLarge
        )

        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 8.dp, end = 8.dp),
            text = "$street\r\n $zipcode $city",
            style = Typography.bodyLarge,
            textAlign = TextAlign.Right
        )
    }

    Divider(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 8.dp, end = 8.dp, top = 8.dp)
    )
}

/* TODO: Add clickable function, then go to penaltyReceived with automatic filter for the player*/
@Composable
private fun RowOpenPenalties(
    openPenalties: Double
) {
    val color = if (openPenalties > 0) colorSchemeSegButtons().foregroundNotPaid else colorSchemeSegButtons().foregroundPaid
    val format = android.icu.text.NumberFormat.getCurrencyInstance()
    format.currency = android.icu.util.Currency.getInstance("EUR")

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(40.dp)
            .padding(top = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier
                .padding(start = 8.dp),
            text = stringResource(id = R.string.OpenPenalties),
            style = Typography.bodyLarge
        )

        Row(
            modifier = Modifier
                .fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End
        ) {
            Text(
                text = format.currency.symbol,
                style = Typography.bodyLarge,
                color = color
            )

            BasicTextField(
                modifier = Modifier
                    .padding(end = 8.dp),
                value = openPenalties.toInt().toString(),
                onValueChange = { },
                enabled = false,
                textStyle = Typography.bodyLarge.copy(textAlign = TextAlign.Right, color = color),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                visualTransformation = NumberCommaTransformation()
            )
        }
    }

    Divider(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 8.dp, end = 8.dp, top = 8.dp)
    )
}

@Composable
private fun PlayerStats(
    playedGames: String,
    goals: String,
    yellowCards: String,
    twoMinutes: String,
    redCards: String
) {
    Text(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 8.dp, end = 8.dp, top = 8.dp),
        text = stringResource(id = R.string.PlayerStats),
        style = Typography.headlineSmall,
        textAlign = TextAlign.Center
    )

    Row(
        modifier = Modifier
            .height(IntrinsicSize.Min)
            .fillMaxWidth()
            .padding(top = 8.dp)
    ) {
        Column(
            modifier = Modifier
                .weight(0.5f)
        ) {
            val listNames = listOf(
                stringResource(id = R.string.PlayedGames),
                stringResource(id = R.string.Goals),
                stringResource(id = R.string.YellowCards),
                stringResource(id = R.string.TwoMinutes),
                stringResource(id = R.string.RedCards)
            )

            listNames.forEach { name ->
                TableColumn(
                    text = name,
                )
            }
        }

        Column(
            modifier = Modifier
                .weight(0.5f)
        ) {
            val listValues = listOf(
                playedGames,
                goals,
                yellowCards,
                twoMinutes,
                redCards
            )

            listValues.forEach { value ->
                TableColumn(
                    text = value.ifEmpty { "0" },
                    textAlign = TextAlign.Right
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PlayerDetailContentPreview() {

    val player = playerExample()
    val playerUiState = PlayerUiState(
        id = player.id,
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

    PlayerDetailContent(
        playerUiState = playerUiState
    )
}
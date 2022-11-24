package de.vexxes.penaltycatalog.presentation.screen.player

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import de.vexxes.penaltycatalog.R
import de.vexxes.penaltycatalog.domain.model.Player
import de.vexxes.penaltycatalog.ui.theme.Green40
import de.vexxes.penaltycatalog.ui.theme.Red80
import de.vexxes.penaltycatalog.ui.theme.Typography
import java.text.NumberFormat
import java.util.Currency
import java.util.Locale

@Composable
fun PlayerDetailContent(
    player: Player
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        RowHeader(number = player.number.toString(), firstName = player.firstName, lastName = player.lastName)
        RowBirthday(birthday = player.birthday)
        RowAddress(street = player.street, zipcode = player.zipcode.toString(), city = player.city)
        RowOpenPenalties(10f) /*TODO Insert real open penalties*/
        PlayerStats(player = player)
    }
}

@Preview(showBackground = true)
@Composable
fun PlayerDetailContentPreview() {

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

    PlayerDetailContent(
        player = player
    )
}



@Composable
private fun TableColumn(
    text: String,
    textAlign: TextAlign? = null,
    paddingValues: PaddingValues,
    showDivider: Boolean = false
) {
    Text(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 8.dp, end = 8.dp, top = 8.dp),
        text = text,
        textAlign = textAlign,
        style = Typography.bodyLarge
    )

    if (showDivider) {
        Divider(
            modifier = Modifier.padding(paddingValues)
        )
    }
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
            .padding(top = 8.dp)
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

@Composable
private fun RowOpenPenalties(
    openPenalties: Float
) {
    val format = NumberFormat.getCurrencyInstance()
    format.maximumFractionDigits = 0
    format.currency = Currency.getInstance(Locale.getDefault())

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp)
    ) {
        Text(
            modifier = Modifier
                .padding(start = 8.dp)
                .align(Alignment.CenterVertically),
            text = stringResource(id = R.string.OpenPenalties),
            style = Typography.bodyLarge
        )

        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 8.dp, end = 8.dp),
            text = format.format(openPenalties),
            style = Typography.bodyLarge,
            textAlign = TextAlign.Right,
            color = if (openPenalties > 0) Red80 else Green40
        )
    }

    Divider(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 8.dp, end = 8.dp, top = 8.dp)
    )
}

@Composable
private fun PlayerStats(
    player: Player
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
            TableColumn(
                text = stringResource(id = R.string.PlayedGames),
                paddingValues = PaddingValues(start = 8.dp, top = 8.dp),
                showDivider = true
            )

            TableColumn(
                text = stringResource(id = R.string.Goals),
                paddingValues = PaddingValues(start = 8.dp, top = 8.dp),
                showDivider = true
            )

            TableColumn(
                text = stringResource(id = R.string.YellowCards),
                paddingValues = PaddingValues(start = 8.dp, top = 8.dp),
                showDivider = true
            )

            TableColumn(
                text = stringResource(id = R.string.TwoMinutes),
                paddingValues = PaddingValues(start = 8.dp, top = 8.dp),
                showDivider = true
            )

            TableColumn(
                text = stringResource(id = R.string.RedCards),
                paddingValues = PaddingValues(start = 8.dp, top = 8.dp),
                showDivider = false
            )
        }

        // Vertical divider
        Divider(
            modifier = Modifier
                .fillMaxHeight()
                .width(1.dp)
        )

        Column(
            modifier = Modifier
                .weight(0.5f)
        ) {

            TableColumn(
                text = player.playedGames.toString(),
                textAlign = TextAlign.Center,
                paddingValues = PaddingValues(end = 8.dp, top = 8.dp),
                showDivider = true
            )

            TableColumn(
                text = player.goals.toString(),
                textAlign = TextAlign.Center,
                paddingValues = PaddingValues(end = 8.dp, top = 8.dp),
                showDivider = true
            )

            TableColumn(
                text = player.yellowCards.toString(),
                textAlign = TextAlign.Center,
                paddingValues = PaddingValues(end = 8.dp, top = 8.dp),
                showDivider = true
            )

            TableColumn(
                text = player.twoMinutes.toString(),
                textAlign = TextAlign.Center,
                paddingValues = PaddingValues(end = 8.dp, top = 8.dp),
                showDivider = true
            )

            TableColumn(
                text = player.redCards.toString(),
                textAlign = TextAlign.Center,
                paddingValues = PaddingValues(end = 8.dp, top = 8.dp),
                showDivider = false
            )
        }
    }
}
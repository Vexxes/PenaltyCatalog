package de.vexxes.penaltycatalog.presentation.screen.history

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import de.vexxes.penaltycatalog.component.EmptyContent
import de.vexxes.penaltycatalog.domain.model.PenaltyHistory
import de.vexxes.penaltycatalog.domain.model.Player
import de.vexxes.penaltycatalog.ui.theme.Typography
import de.vexxes.penaltycatalog.ui.theme.colorSchemeSegButtons
import de.vexxes.penaltycatalog.util.FilterPaidState
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import java.text.NumberFormat
import java.util.Currency

@Composable
fun PenaltyHistoryListContent(
    penaltyHistory: List<PenaltyHistory>,
    filterPaidState: FilterPaidState,
    navigateToPenaltyHistoryDetailScreen: (penaltyHistoryId: String) -> Unit
) {
    if (penaltyHistory.isNotEmpty()) {
        DisplayPenaltyHistory(
            penaltyHistory = penaltyHistory,
            filterPaidState = filterPaidState,
            navigateToPenaltyHistoryDetailScreen = navigateToPenaltyHistoryDetailScreen
        )
    } else {
        EmptyContent()
    }
}

@Composable
private fun DisplayPenaltyHistory(
    penaltyHistory: List<PenaltyHistory>,
    filterPaidState: FilterPaidState,
    navigateToPenaltyHistoryDetailScreen: (penaltyHistoryId: String) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(
            items = penaltyHistory,
            key = { penaltyHistoryItem ->
                penaltyHistoryItem.hashCode()
            }
        ) { penaltyHistoryItem ->

            when (filterPaidState) {
                FilterPaidState.OFF -> {
                    PenaltyHistoryItem(
                        penaltyHistory = penaltyHistoryItem,
                        navigateToPenaltyHistoryDetailScreen = navigateToPenaltyHistoryDetailScreen
                    )
                }

                FilterPaidState.PAID -> {
                    if (penaltyHistoryItem.penaltyPaid)
                        PenaltyHistoryItem(
                            penaltyHistory = penaltyHistoryItem,
                            navigateToPenaltyHistoryDetailScreen = navigateToPenaltyHistoryDetailScreen
                        )
                }

                FilterPaidState.NOT_PAID -> {
                    if (!penaltyHistoryItem.penaltyPaid)
                        PenaltyHistoryItem(
                            penaltyHistory = penaltyHistoryItem,
                            navigateToPenaltyHistoryDetailScreen = navigateToPenaltyHistoryDetailScreen
                        )
                }
            }

        }
    }
}

@Composable
private fun PenaltyHistoryItem(
    penaltyHistory: PenaltyHistory,
    navigateToPenaltyHistoryDetailScreen: (penaltyHistoryId: String) -> Unit
) {
    Row(
        modifier = Modifier
            .clickable {
                navigateToPenaltyHistoryDetailScreen(penaltyHistory._id)
            }
            .fillMaxWidth()
            .height(88.dp)
            .background(
                if (penaltyHistory.penaltyPaid) colorSchemeSegButtons().backgroundPaid else colorSchemeSegButtons().backgroundNotPaid
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 8.dp, end = 8.dp)
                .weight(0.8f),
            verticalArrangement = Arrangement.Center
        ) {
            PenaltyHistoryPlayerName(
                text = penaltyHistory.playerName
            )

            PenaltyHistorySubText(
                text = penaltyHistory.penaltyName
            )

            PenaltyHistorySubText(
                text = penaltyHistory.timeOfPenalty.toLocalDateTime(TimeZone.UTC).date.toString()
            )
        }

        PenaltyHistoryPenaltyAmount(
            modifier = Modifier
                .padding(end = 8.dp)
                .weight(0.2f),
            value = penaltyHistory.penaltyValue,
            isBeer = penaltyHistory.penaltyIsBeer
        )
    }
}

@Composable
private fun PenaltyHistoryPlayerName(
    text: String
) {
    Text(
        modifier = Modifier
            .fillMaxWidth(),
        text = text,
        style = Typography.titleMedium.copy(fontSize = 19.sp, fontWeight = FontWeight.Bold),
        textAlign = TextAlign.Left
    )
}

@Composable
private fun PenaltyHistorySubText(
    text: String
) {
    Text(
        modifier = Modifier
            .fillMaxWidth(),
        text = text,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
        style = Typography.titleSmall.copy(fontWeight = FontWeight.Bold),
        textAlign = TextAlign.Left
    )
}

@Composable
private fun PenaltyHistoryPenaltyAmount(
    modifier: Modifier = Modifier,
    value: Int,
    isBeer: Boolean
) {
    val text: String

    if(isBeer) {
        text = "$value " + stringResource(id = de.vexxes.penaltycatalog.R.string.Box)
    } else {
        val format = NumberFormat.getCurrencyInstance()
        format.maximumFractionDigits = 2
        format.currency = Currency.getInstance("EUR")
        text = format.format(value)
    }

    Row(
        modifier = modifier
            .fillMaxSize(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.End
    ) {
        Text(
            text = text,
            style = Typography.titleLarge.copy(fontWeight = FontWeight.Bold)
        )
    }
}

@Composable
@Preview(showBackground = true)
private fun PenaltyHistoryItemPreview() {

    val player = Player.generateFaker()

    val penaltyHistory = PenaltyHistory(
        _id = "",
        penaltyName = "Getränke zur Besprechung",
        playerName = "${player.lastName}, ${player.firstName}",
        timeOfPenalty = Clock.System.now()
    )

    PenaltyHistoryItem(
        penaltyHistory = penaltyHistory,
        navigateToPenaltyHistoryDetailScreen = { }
    )
}

private fun prepPlayerList(): List<PenaltyHistory> {
    val player1 = Player.generateFaker()
    val player2 = Player.generateFaker()
    val player3 = Player.generateFaker()

    val penaltyHistory1 = PenaltyHistory(
        _id = "",
        penaltyName = "Getränke zur Besprechung",
        playerName = "${player1.lastName}, ${player1.firstName}",
        penaltyValue = 1,
        penaltyIsBeer = true,
        timeOfPenalty = Clock.System.now()
    )

    val penaltyHistory2 = PenaltyHistory(
        _id = "",
        penaltyName = "Verspätete Zahlung des Monatsbeitrag",
        playerName = "${player2.lastName}, ${player2.firstName}",
        penaltyValue = 5,
        penaltyIsBeer = false,
        timeOfPenalty = Clock.System.now()
    )

    val penaltyHistory3 = PenaltyHistory(
        _id = "",
        penaltyName = "Verspätete Zahlung des Monatsbeitrag",
        playerName = "${player3.lastName}, ${player3.firstName}",
        penaltyValue = 5,
        penaltyIsBeer = false,
        timeOfPenalty = Clock.System.now(),
        penaltyPaid = true
    )

    return listOf(
        penaltyHistory1,
        penaltyHistory2,
        penaltyHistory3
    )
}

@Composable
@Preview(showBackground = true)
private fun PenaltyHistoryContentPreview1() {
    PenaltyHistoryListContent(
        penaltyHistory = prepPlayerList(),
        filterPaidState = FilterPaidState.OFF,
        navigateToPenaltyHistoryDetailScreen = { }
    )
}

@Composable
@Preview(showBackground = true)
private fun PenaltyHistoryContentPreview2() {
    PenaltyHistoryListContent(
        penaltyHistory = prepPlayerList(),
        filterPaidState = FilterPaidState.PAID,
        navigateToPenaltyHistoryDetailScreen = { }
    )
}

@Composable
@Preview(showBackground = true)
private fun PenaltyHistoryContentPreview3() {
    PenaltyHistoryListContent(
        penaltyHistory = prepPlayerList(),
        filterPaidState = FilterPaidState.NOT_PAID,
        navigateToPenaltyHistoryDetailScreen = { }
    )
}
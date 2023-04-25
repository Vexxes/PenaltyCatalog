package de.vexxes.penaltycatalog.presentation.screen.penaltyReceived

import android.icu.text.NumberFormat
import android.icu.util.Currency
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
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import de.vexxes.penaltycatalog.R
import de.vexxes.penaltycatalog.component.EmptyContent
import de.vexxes.penaltycatalog.domain.uistate.PenaltyReceivedUiState
import de.vexxes.penaltycatalog.domain.uistate.penaltyReceivedUiStateExample1
import de.vexxes.penaltycatalog.domain.uistate.penaltyReceivedUiStateExample2
import de.vexxes.penaltycatalog.domain.uistate.penaltyReceivedUiStateExample3
import de.vexxes.penaltycatalog.domain.visualTransformation.NumberCommaTransformation
import de.vexxes.penaltycatalog.ui.theme.Typography
import de.vexxes.penaltycatalog.ui.theme.colorSchemeSegButtons
import de.vexxes.penaltycatalog.domain.enums.FilterPaidState
import kotlinx.datetime.LocalDate
import kotlinx.datetime.toJavaLocalDate
import java.time.format.DateTimeFormatter

@Composable
fun PenaltyReceivedListContent(
    penaltyReceivedUiStateList: List<PenaltyReceivedUiState>,
    filterPaidState: FilterPaidState,
    navigateToPenaltyReceivedDetailScreen: (penaltyHistoryId: String) -> Unit
) {
    if (penaltyReceivedUiStateList.isNotEmpty()) {
        DisplayPenaltyReceived(
            penaltyReceivedUiStateList = penaltyReceivedUiStateList,
            filterPaidState = filterPaidState,
            navigateToPenaltyReceivedDetailScreen = navigateToPenaltyReceivedDetailScreen
        )
    } else {
        EmptyContent()
    }
}

@Composable
private fun DisplayPenaltyReceived(
    penaltyReceivedUiStateList: List<PenaltyReceivedUiState>,
    filterPaidState: FilterPaidState,
    navigateToPenaltyReceivedDetailScreen: (penaltyReceivedId: String) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        items(
            items = penaltyReceivedUiStateList,
            key = { penaltyReceivedItem ->
                penaltyReceivedItem.id
            }
        ) { penaltyReceivedItem ->

            val timeOfPenalty = penaltyReceivedItem.timeOfPenalty
            val timeOfPenaltyPaid = penaltyReceivedItem.timeOfPenaltyPaid ?: LocalDate.parse("1970-01-01")

            when (filterPaidState) {
                FilterPaidState.OFF -> {
                    PenaltyReceivedItem(
                        penaltyReceived = penaltyReceivedItem,
                        color = if (timeOfPenaltyPaid >= timeOfPenalty) colorSchemeSegButtons().foregroundPaid else colorSchemeSegButtons().foregroundNotPaid,
                        navigateToPenaltyReceivedDetailScreen = navigateToPenaltyReceivedDetailScreen
                    )
                }

                FilterPaidState.PAID -> {
                    if (timeOfPenaltyPaid >= timeOfPenalty) {
                        PenaltyReceivedItem(
                            penaltyReceived = penaltyReceivedItem,
                            color = colorSchemeSegButtons().foregroundPaid,
                            navigateToPenaltyReceivedDetailScreen = navigateToPenaltyReceivedDetailScreen
                        )
                    }
                }

                FilterPaidState.NOT_PAID -> {
                    if (timeOfPenaltyPaid < timeOfPenalty) {
                        PenaltyReceivedItem(
                            penaltyReceived = penaltyReceivedItem,
                            color = colorSchemeSegButtons().foregroundNotPaid,
                            navigateToPenaltyReceivedDetailScreen = navigateToPenaltyReceivedDetailScreen
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun PenaltyReceivedItem(
    penaltyReceived: PenaltyReceivedUiState,
    color: Color = colorSchemeSegButtons().foregroundNotPaid,
    navigateToPenaltyReceivedDetailScreen: (penaltyReceivedId: String) -> Unit
) {
    val formattedTimeOfPenalty = DateTimeFormatter
        .ofPattern("eeee, dd. MMMM y")
        .format(penaltyReceived.timeOfPenalty.toJavaLocalDate())

    Row(
        modifier = Modifier
            .clickable {
                navigateToPenaltyReceivedDetailScreen(penaltyReceived.id)
            }
            .fillMaxWidth()
            .height(72.dp),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 8.dp, end = 8.dp)
                .weight(0.8f),
            verticalArrangement = Arrangement.Center
        ) {
            PenaltyReceivedPlayerName(
                text = penaltyReceived.playerName
            )

            PenaltyReceivedSubText(
                text = penaltyReceived.penaltyName
            )

            PenaltyReceivedSubText(
                text = formattedTimeOfPenalty
            )
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 8.dp, end = 8.dp)
                .weight(0.3f),
            verticalArrangement = Arrangement.Center
        ) {
            PenaltyReceivedAmount(
                value = penaltyReceived.penaltyValue,
                isBeer = penaltyReceived.penaltyIsBeer,
                color = color
            )
        }
    }

    Divider()
}

@Composable
private fun PenaltyReceivedPlayerName(
    text: String
) {
    Text(
        modifier = Modifier
            .fillMaxWidth(),
        text = text,
        style = Typography.titleMedium.copy(fontWeight = FontWeight.Bold),
        textAlign = TextAlign.Left
    )
}

@Composable
private fun PenaltyReceivedSubText(
    text: String
) {
    Text(
        modifier = Modifier
            .fillMaxWidth(),
        text = text,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
        style = Typography.bodySmall.copy(fontWeight = FontWeight.Bold),
        textAlign = TextAlign.Left
    )
}

@Composable
private fun PenaltyReceivedAmount(
    value: String,
    isBeer: Boolean,
    color: Color
) {
    val format = NumberFormat.getCurrencyInstance()
    format.currency = Currency.getInstance("EUR")
    format.maximumFractionDigits = 2

    val leadingText = if (isBeer) "${stringResource(id = R.string.Box)} " else "${format.currency.symbol} "

    Row {
        Text(
            text = leadingText,
            style = Typography.titleLarge,
            color = color
        )

        BasicTextField (
            modifier = Modifier
                .padding(end = 8.dp),
            value = value,
            onValueChange = { },
            enabled = false,
            textStyle = Typography.titleLarge.copy(textAlign = TextAlign.Right, color = color),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            visualTransformation = NumberCommaTransformation()
        )
    }
}

@Composable
@Preview(name = "Light Theme", showBackground = true)
private fun PenaltyReceivedItemPreview() {
    PenaltyReceivedItem(
        penaltyReceived = penaltyReceivedUiStateExample1(),
        navigateToPenaltyReceivedDetailScreen = { }
    )
}

@Composable
@Preview(showBackground = true)
private fun PenaltyReceivedContentPreview1() {
    PenaltyReceivedListContent(
        penaltyReceivedUiStateList = listOf(
            penaltyReceivedUiStateExample1(),
            penaltyReceivedUiStateExample2(),
            penaltyReceivedUiStateExample3()
        ),
        filterPaidState = FilterPaidState.OFF,
        navigateToPenaltyReceivedDetailScreen = { }
    )
}

@Composable
@Preview(showBackground = true)
private fun PenaltyReceivedContentPreview2() {
    PenaltyReceivedListContent(
        penaltyReceivedUiStateList = listOf(
            penaltyReceivedUiStateExample1(),
            penaltyReceivedUiStateExample2(),
            penaltyReceivedUiStateExample3()
        ),
        filterPaidState = FilterPaidState.PAID,
        navigateToPenaltyReceivedDetailScreen = { }
    )
}

@Composable
@Preview(showBackground = true)
private fun PenaltyReceivedContentPreview3() {
    PenaltyReceivedListContent(
        penaltyReceivedUiStateList = listOf(
            penaltyReceivedUiStateExample1(),
            penaltyReceivedUiStateExample2(),
            penaltyReceivedUiStateExample3()
        ),
        filterPaidState = FilterPaidState.NOT_PAID,
        navigateToPenaltyReceivedDetailScreen = { }
    )
}

package de.vexxes.penaltycatalog.presentation.screen.penaltyReceived

import android.icu.text.NumberFormat
import androidx.compose.foundation.border
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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import de.vexxes.penaltycatalog.R
import de.vexxes.penaltycatalog.component.EmptyContent
import de.vexxes.penaltycatalog.domain.model.PenaltyReceived
import de.vexxes.penaltycatalog.domain.model.penaltyReceivedExample1
import de.vexxes.penaltycatalog.domain.model.penaltyReceivedExample2
import de.vexxes.penaltycatalog.domain.model.penaltyReceivedExample3
import de.vexxes.penaltycatalog.ui.theme.Typography
import de.vexxes.penaltycatalog.util.FilterPaidState
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

@Composable
fun PenaltyReceivedListContent(
    penaltyReceived: List<PenaltyReceived>,
    filterPaidState: FilterPaidState,
    navigateToPenaltyReceivedDetailScreen: (penaltyHistoryId: String) -> Unit
) {
    if (penaltyReceived.isNotEmpty()) {
        DisplayPenaltyReceived(
            penaltyReceived = penaltyReceived,
            filterPaidState = filterPaidState,
            navigateToPenaltyReceivedDetailScreen = navigateToPenaltyReceivedDetailScreen
        )
    } else {
        EmptyContent()
    }
}

@Composable
private fun DisplayPenaltyReceived(
    penaltyReceived: List<PenaltyReceived>,
    filterPaidState: FilterPaidState,
    navigateToPenaltyReceivedDetailScreen: (penaltyReceivedId: String) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        items(
            items = penaltyReceived,
            key = { penaltyReceivedItem ->
                penaltyReceivedItem.hashCode()
            }
        ) { penaltyReceivedItem ->

//            val penaltyPaidLocalDate = LocalDate.parse(penaltyReceivedItem.timeOfPenaltyPaid)
            val penaltyPaidLocalDate = Clock.System.now().toLocalDateTime(TimeZone.UTC).date //penaltyReceivedItem.timeOfPenaltyPaid
            val currentTime = Clock.System.now().toLocalDateTime(TimeZone.UTC).date

            when (filterPaidState) {
                FilterPaidState.OFF -> {
                    PenaltyReceivedItem(
                        penaltyReceived = penaltyReceivedItem,
                        navigateToPenaltyReceivedDetailScreen = navigateToPenaltyReceivedDetailScreen
                    )
                }

                // TODO: Redo when clear is how to handle penaltyPaid
                FilterPaidState.PAID -> {
                    if (currentTime > penaltyPaidLocalDate!!)
                        PenaltyReceivedItem(
                            penaltyReceived = penaltyReceivedItem,
                            navigateToPenaltyReceivedDetailScreen = navigateToPenaltyReceivedDetailScreen
                        )
                }

                FilterPaidState.NOT_PAID -> {
                    if (currentTime < penaltyPaidLocalDate!!) {
                        PenaltyReceivedItem(
                            penaltyReceived = penaltyReceivedItem,
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
    penaltyReceived: PenaltyReceived,
    navigateToPenaltyReceivedDetailScreen: (penaltyReceivedId: String) -> Unit
) {
    Row(
        modifier = Modifier
            .clickable {
                navigateToPenaltyReceivedDetailScreen(penaltyReceived.id)
            }
            .fillMaxWidth()
            .height(72.dp)
            //TODO: Find a way to get background color
            /*
            .background(
                if (penaltyReceived.penaltyPaid) colorSchemeSegButtons().backgroundPaid else colorSchemeSegButtons().backgroundNotPaid
            )
             */
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 8.dp, end = 8.dp)
                .weight(0.6f),
            verticalArrangement = Arrangement.Center
        ) {
            PenaltyReceivedPlayerName(
                // TODO: Get playerName with playerId
                text = penaltyReceived.playerId
            )

            PenaltyReceivedSubText(
                // TODO: Get penaltyName with penaltyId
                text = penaltyReceived.penaltyTypeId
            )

            PenaltyReceivedSubText(
                text = penaltyReceived.timeOfPenalty.toString()
            )
        }

        /* TODO: Read penaltyAmount from with penaltyId
        PenaltyHistoryPenaltyAmount(
            modifier = Modifier
                .padding(end = 8.dp)
                .weight(0.4f),
            value = penaltyReceived.penaltyValue,
            isBeer = penaltyReceived.penaltyIsBeer,
            color = if (penaltyReceived.penaltyPaid) colorSchemeSegButtons().backgroundPaid else colorSchemeSegButtons().backgroundNotPaid)
         */
    }
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun PenaltyReceivedPenaltyAmount(
    modifier: Modifier = Modifier,
    value: String,
    isBeer: Boolean,
    color: Color
) {
    Row(
        modifier = modifier
            .fillMaxSize(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.End
    ) {
/*        val visualTransformation = if (isBeer) VisualTransformation.None else CurrencyAmountInputVisualTransformation(
            fixedCursorAtTheEnd = true
        )*/

        Text(
            text = if (isBeer) stringResource(id = R.string.Box) else NumberFormat.getCurrencyInstance().currency.symbol,
            modifier = Modifier
                .weight(0.4f),
            style = Typography.titleMedium.copy(textAlign = TextAlign.Right)
        )

        OutlinedTextField(
            value = value,
            onValueChange = { },
            modifier = Modifier
                .weight(0.8f)
                .border(
                    width = 1.dp,
                    color,
                    TextFieldDefaults.outlinedShape
                ),
            enabled = false,
            readOnly = true,
            textStyle = Typography.titleMedium.copy(textAlign = TextAlign.Right),
//            visualTransformation = visualTransformation,
            colors = TextFieldDefaults.outlinedTextFieldColors(
                disabledTextColor = MaterialTheme.colorScheme.onSurface,
                disabledBorderColor = MaterialTheme.colorScheme.outline,
                disabledPlaceholderColor = MaterialTheme.colorScheme.onSurfaceVariant,
                disabledLabelColor = MaterialTheme.colorScheme.onSurfaceVariant,
                disabledLeadingIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                disabledTrailingIconColor = MaterialTheme.colorScheme.onSurfaceVariant
            )
        )
    }
}

@Composable
@Preview(showBackground = true)
private fun PenaltyReceivedItemPreview() {
    PenaltyReceivedItem(
        penaltyReceived = penaltyReceivedExample1(),
        navigateToPenaltyReceivedDetailScreen = { }
    )
}

@Composable
@Preview(showBackground = true)
private fun PenaltyReceivedContentPreview1() {
    PenaltyReceivedListContent(
        penaltyReceived = listOf(
            penaltyReceivedExample1(),
            penaltyReceivedExample2(),
            penaltyReceivedExample3()
        ),
        filterPaidState = FilterPaidState.OFF,
        navigateToPenaltyReceivedDetailScreen = { }
    )
}

@Composable
@Preview(showBackground = true)
private fun PenaltyReceivedContentPreview2() {
    PenaltyReceivedListContent(
        penaltyReceived = listOf(
            penaltyReceivedExample1(),
            penaltyReceivedExample2(),
            penaltyReceivedExample3()
        ),
        filterPaidState = FilterPaidState.PAID,
        navigateToPenaltyReceivedDetailScreen = { }
    )
}

@Composable
@Preview(showBackground = true)
private fun PenaltyReceivedContentPreview3() {
    PenaltyReceivedListContent(
        penaltyReceived = listOf(
            penaltyReceivedExample1(),
            penaltyReceivedExample2(),
            penaltyReceivedExample3()
        ),
        filterPaidState = FilterPaidState.NOT_PAID,
        navigateToPenaltyReceivedDetailScreen = { }
    )
}
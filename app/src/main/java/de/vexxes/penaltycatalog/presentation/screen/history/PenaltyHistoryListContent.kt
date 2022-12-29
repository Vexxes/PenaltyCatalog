package de.vexxes.penaltycatalog.presentation.screen.history

import android.icu.text.NumberFormat
import androidx.compose.foundation.background
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
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import de.vexxes.penaltycatalog.R
import de.vexxes.penaltycatalog.component.EmptyContent
import de.vexxes.penaltycatalog.domain.model.PenaltyHistory
import de.vexxes.penaltycatalog.domain.model.penaltyHistoryExample1
import de.vexxes.penaltycatalog.domain.model.penaltyHistoryExample2
import de.vexxes.penaltycatalog.domain.model.penaltyHistoryExample3
import de.vexxes.penaltycatalog.domain.visualTransformation.CurrencyAmountInputVisualTransformation
import de.vexxes.penaltycatalog.ui.theme.Typography
import de.vexxes.penaltycatalog.ui.theme.colorSchemeSegButtons
import de.vexxes.penaltycatalog.util.FilterPaidState

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
        verticalArrangement = Arrangement.spacedBy(4.dp)
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
                    if (!penaltyHistoryItem.penaltyPaid) {
                        PenaltyHistoryItem(
                            penaltyHistory = penaltyHistoryItem,
                            navigateToPenaltyHistoryDetailScreen = navigateToPenaltyHistoryDetailScreen
                        )
                    }
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
            .height(72.dp)
            .background(
                if (penaltyHistory.penaltyPaid) colorSchemeSegButtons().backgroundPaid else colorSchemeSegButtons().backgroundNotPaid
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 8.dp, end = 8.dp)
                .weight(0.6f),
            verticalArrangement = Arrangement.Center
        ) {
            PenaltyHistoryPlayerName(
                text = penaltyHistory.playerName
            )

            PenaltyHistorySubText(
                text = penaltyHistory.penaltyName
            )

            PenaltyHistorySubText(
                text = penaltyHistory.timeOfPenalty
            )
        }

        PenaltyHistoryPenaltyAmount(
            modifier = Modifier
                .padding(end = 8.dp)
                .weight(0.4f),
            value = penaltyHistory.penaltyValue,
            isBeer = penaltyHistory.penaltyIsBeer,
            color = if (penaltyHistory.penaltyPaid) colorSchemeSegButtons().backgroundPaid else colorSchemeSegButtons().backgroundNotPaid)
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
        style = Typography.titleMedium.copy(fontWeight = FontWeight.Bold),
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
        style = Typography.bodySmall.copy(fontWeight = FontWeight.Bold),
        textAlign = TextAlign.Left
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun PenaltyHistoryPenaltyAmount(
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
        val visualTransformation = if (isBeer) VisualTransformation.None else CurrencyAmountInputVisualTransformation(
            fixedCursorAtTheEnd = true
        )

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
            visualTransformation = visualTransformation,
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
private fun PenaltyHistoryItemPreview() {
    PenaltyHistoryItem(
        penaltyHistory = penaltyHistoryExample1(),
        navigateToPenaltyHistoryDetailScreen = { }
    )
}

@Composable
@Preview(showBackground = true)
private fun PenaltyHistoryContentPreview1() {
    PenaltyHistoryListContent(
        penaltyHistory = listOf(
            penaltyHistoryExample1(),
            penaltyHistoryExample2(),
            penaltyHistoryExample3()
        ),
        filterPaidState = FilterPaidState.OFF,
        navigateToPenaltyHistoryDetailScreen = { }
    )
}

@Composable
@Preview(showBackground = true)
private fun PenaltyHistoryContentPreview2() {
    PenaltyHistoryListContent(
        penaltyHistory = listOf(
            penaltyHistoryExample1(),
            penaltyHistoryExample2(),
            penaltyHistoryExample3()
        ),
        filterPaidState = FilterPaidState.PAID,
        navigateToPenaltyHistoryDetailScreen = { }
    )
}

@Composable
@Preview(showBackground = true)
private fun PenaltyHistoryContentPreview3() {
    PenaltyHistoryListContent(
        penaltyHistory = listOf(
            penaltyHistoryExample1(),
            penaltyHistoryExample2(),
            penaltyHistoryExample3()
        ),
        filterPaidState = FilterPaidState.NOT_PAID,
        navigateToPenaltyHistoryDetailScreen = { }
    )
}
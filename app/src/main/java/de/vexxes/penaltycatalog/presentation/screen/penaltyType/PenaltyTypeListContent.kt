package de.vexxes.penaltycatalog.presentation.screen.penaltyType

import android.icu.text.NumberFormat
import android.icu.util.Currency
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import de.vexxes.penaltycatalog.R
import de.vexxes.penaltycatalog.component.EmptyContent
import de.vexxes.penaltycatalog.domain.model.PenaltyType
import de.vexxes.penaltycatalog.domain.model.convertToPenaltyTypeUiState
import de.vexxes.penaltycatalog.domain.model.penaltyExample1
import de.vexxes.penaltycatalog.domain.model.penaltyExample2
import de.vexxes.penaltycatalog.domain.model.penaltyExample3
import de.vexxes.penaltycatalog.domain.uistate.PenaltyTypeUiState
import de.vexxes.penaltycatalog.domain.visualTransformation.NumberCommaTransformation
import de.vexxes.penaltycatalog.ui.theme.Typography

@Composable
fun PenaltyTypeListContent(
    penalties: List<PenaltyType>,
    navigateToPenaltyDetailScreen: (playerId: String) -> Unit
) {
    Box {
        if(penalties.isNotEmpty()) {
            DisplayPenalties(
                penalties = penalties,
                navigateToPenaltyDetailScreen = navigateToPenaltyDetailScreen
            )
        } else {
            EmptyContent()
        }
    }
}

@Composable
private fun DisplayPenalties(
    penalties: List<PenaltyType>,
    navigateToPenaltyDetailScreen: (penaltyId: String) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        items(
            items = penalties,
            key = { penalty ->
                penalty.hashCode()
            }
        ) { penalty ->
            PenaltyTypeItem(
                penaltyTypeUiState = penalty.convertToPenaltyTypeUiState(),
                navigateToPenaltyDetailScreen = navigateToPenaltyDetailScreen
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
private fun PenaltyTypeItemPreview() {
    PenaltyTypeItem(
        penaltyTypeUiState = penaltyExample1().convertToPenaltyTypeUiState(),
        navigateToPenaltyDetailScreen = { }
    )
}

@Preview(showBackground = true)
@Composable
private fun PenaltyTypeListContentPreview() {
    val penalties = listOf(
        penaltyExample1(),
        penaltyExample2(),
        penaltyExample3()
    )

    PenaltyTypeListContent(
        penalties = penalties,
        navigateToPenaltyDetailScreen = { }
    )
}

@Preview(showBackground = true)
@Composable
private fun EmptyContentPreview() {
    EmptyContent()
}

@Composable
private fun PenaltyTypeName(
    text: String
) {
    Text(
        modifier = Modifier.padding(start = 8.dp),
        text = text,
        style = Typography.titleLarge,
        textAlign = TextAlign.Left,
    )
}

@Composable
private fun PenaltyTypeAmount(
    value: String,
    isBeer: Boolean
) {
    val format = NumberFormat.getCurrencyInstance()
    format.currency = Currency.getInstance("EUR")
    format.maximumFractionDigits = 2

    val leadingText = if (isBeer) "${stringResource(id = R.string.Box)} " else "${format.currency.symbol} "

    Row(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Text(
            text = leadingText,
            style = Typography.titleLarge
        )

        BasicTextField (
            modifier = Modifier
                .padding(end = 8.dp),
            value = value,
            onValueChange = { },
            enabled = false,
            textStyle = Typography.titleLarge.copy(textAlign = TextAlign.Right, color = MaterialTheme.colorScheme.onSurface),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            visualTransformation = NumberCommaTransformation()
        )
    }
}

@Composable
private fun PenaltyTypeItem(
    penaltyTypeUiState: PenaltyTypeUiState,
    navigateToPenaltyDetailScreen: (penaltyId: String) -> Unit
) {
    Row(
        modifier = Modifier
            .clickable {
                navigateToPenaltyDetailScreen(penaltyTypeUiState.id)
            }
            .height(56.dp)
            .fillMaxSize(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            modifier = Modifier
                .weight(0.75f),
        ) {
            PenaltyTypeName(
                text = penaltyTypeUiState.name
            )
        }

        Row(
            modifier = Modifier
                .weight(0.25f),
            horizontalArrangement = Arrangement.End
        ) {
            PenaltyTypeAmount(
                value = penaltyTypeUiState.value,
                isBeer = penaltyTypeUiState.isBeer
            )
        }
    }

    Divider()
}
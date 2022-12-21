package de.vexxes.penaltycatalog.presentation.screen.penalty

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
import de.vexxes.penaltycatalog.R
import de.vexxes.penaltycatalog.component.EmptyContent
import de.vexxes.penaltycatalog.domain.model.Penalty
import de.vexxes.penaltycatalog.ui.theme.Typography
import java.text.NumberFormat
import java.util.Currency

@Composable
fun PenaltyListContent(
    penalties: List<Penalty>,
    navigateToPenaltyDetailScreen: (playerId: String) -> Unit
) {
    if(penalties.isNotEmpty()) {
        DisplayPenalties(
            penalties = penalties,
            navigateToPenaltyDetailScreen = navigateToPenaltyDetailScreen
        )
    } else {
        EmptyContent()
    }
}

@Composable
private fun DisplayPenalties(
    penalties: List<Penalty>,
    navigateToPenaltyDetailScreen: (penaltyId: String) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(
            items = penalties,
            key = { penalty ->
                penalty.hashCode()
            }
        ) { penalty ->
            PenaltyItem(
                penalty = penalty,
                navigateToPenaltyDetailScreen = navigateToPenaltyDetailScreen
            )
        }
    }
}

@Composable
private fun PenaltyItem(
    penalty: Penalty,
    navigateToPenaltyDetailScreen: (penaltyId: String) -> Unit
) {
    Row(
        modifier = Modifier
            .clickable {
                navigateToPenaltyDetailScreen(penalty._id)
            }
            .fillMaxWidth()
            .height(72.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 8.dp, end = 8.dp)
                .weight(0.80f),
            verticalArrangement = Arrangement.Center,
        ) {
            PenaltyName(
                text = penalty.name
            )

            PenaltyNameOfCategory(
                text = penalty.nameOfCategory
            )
        }

        PenaltyAmount(
            modifier = Modifier
                .padding(end = 8.dp)
                .weight(0.20f),
            value = penalty.value,
            isBeer = penalty.isBeer
        )
    }
}

@Composable
@Preview(showBackground = true)
private fun PlayerItemPreview() {
    val penalty = Penalty(
        _id = "63717e8314ab74703f0ab5cb",
        name = "Getränke zur Besprechung",
        nameOfCategory = "Sonstiges",
        description = "Mitzubringen in alphabetischer Reihenfolge nach dem Freitagstraining",
        isBeer = true,
        value = 1
    )

    PenaltyItem(
        penalty = penalty,
        navigateToPenaltyDetailScreen = { }
    )
}

@Preview(showBackground = true)
@Composable
fun PenaltyListContentPreview() {
    val penalties = listOf(
        Penalty(
            _id = "",
            name = "Monatsbeitrag",
            nameOfCategory = "Monatsbeitrag",
            description = "",
            isBeer = false,
            value = 500
        ),
        Penalty(
            _id = "",
            name = "Verspätete Zahlung des Monatsbeitrag",
            nameOfCategory = "Monatsbeitrag",
            description = "zzgl. pro Monat",
            isBeer = false,
            value = 500
        ),
        Penalty(
            _id = "",
            name = "Getränke zur Besprechung",
            nameOfCategory = "Sonstiges",
            description = "Mitzubringen in alphabetischer Reihenfolge nach dem Freitagstraining",
            isBeer = true,
            value = 1
        )
    )

    PenaltyListContent(
        penalties = penalties,
        navigateToPenaltyDetailScreen = { }
    )
}

@Preview(showBackground = true)
@Composable
fun EmptyContentPreview() {
    EmptyContent()
}

@Composable
private fun PenaltyName(
    modifier: Modifier = Modifier,
    text: String
) {
    Text(
        modifier = modifier
            .fillMaxWidth(),
        text = text,
        style = Typography.titleMedium.copy(fontSize = 19.sp, fontWeight = FontWeight.Bold),
        textAlign = TextAlign.Left,
    )
}

@Composable
private fun PenaltyNameOfCategory(
    modifier: Modifier = Modifier,
    text: String
) {
    Text(
        modifier = modifier
            .fillMaxWidth(),
        text = text,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
        style = Typography.titleSmall.copy(fontWeight = FontWeight.Bold),
    )
}

@Composable
private fun PenaltyAmount(
    modifier: Modifier = Modifier,
    value: Int,
    isBeer: Boolean
) {
    val text: String

    if(isBeer) {
        text = "$value " + stringResource(id = R.string.Box)
    } else {
        val format = NumberFormat.getCurrencyInstance()
        format.maximumFractionDigits = 2
        format.currency = Currency.getInstance("EUR")
        text = format.format(value.toDouble() / 100) // value is stored as cents, divide by 100
    }

    Row(
        modifier = modifier
            .fillMaxSize(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.End
    ) {
        Text(
            text = text,
            style = Typography.titleLarge
        )
    }
}
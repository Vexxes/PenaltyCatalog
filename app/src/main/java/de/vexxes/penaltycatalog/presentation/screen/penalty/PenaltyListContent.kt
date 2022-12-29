package de.vexxes.penaltycatalog.presentation.screen.penalty

import android.icu.text.NumberFormat
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import de.vexxes.penaltycatalog.R
import de.vexxes.penaltycatalog.component.EmptyContent
import de.vexxes.penaltycatalog.domain.model.Penalty
import de.vexxes.penaltycatalog.domain.model.penaltyExample1
import de.vexxes.penaltycatalog.domain.model.penaltyExample2
import de.vexxes.penaltycatalog.domain.model.penaltyExample3
import de.vexxes.penaltycatalog.domain.visualTransformation.CurrencyAmountInputVisualTransformation
import de.vexxes.penaltycatalog.ui.theme.Typography

@Composable
fun PenaltyListContent(
    penalties: List<Penalty>,
    navigateToPenaltyDetailScreen: (playerId: String) -> Unit
) {
    Box(modifier = Modifier.padding(8.dp)) {
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
                .weight(0.65f),
            verticalArrangement = Arrangement.Center,
        ) {
            PenaltyName(
                text = penalty.name
            )

            PenaltyNameOfCategory(
                text = penalty.categoryName
            )
        }

        PenaltyAmount(
            modifier = Modifier
                .weight(0.35f),
            value = penalty.value,
            isBeer = penalty.isBeer
        )
    }

    Divider()
}

@Composable
@Preview(showBackground = true)
private fun PlayerItemPreview() {
    PenaltyItem(
        penalty = penaltyExample1(),
        navigateToPenaltyDetailScreen = { }
    )
}

@Preview(showBackground = true)
@Composable
private fun PenaltyListContentPreview() {
    val penalties = listOf(
        penaltyExample1(),
        penaltyExample2(),
        penaltyExample3()
    )

    PenaltyListContent(
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
private fun PenaltyName(
    modifier: Modifier = Modifier,
    text: String
) {
    Text(
        modifier = modifier,
        text = text,
        style = Typography.titleMedium.copy(fontWeight = FontWeight.Bold),
        textAlign = TextAlign.Left,
    )
}

@Composable
private fun PenaltyNameOfCategory(
    modifier: Modifier = Modifier,
    text: String
) {
    Text(
        modifier = modifier,
        text = text,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
        style = Typography.bodySmall.copy(fontWeight = FontWeight.Bold),
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun PenaltyAmount(
    modifier: Modifier = Modifier,
    value: String,
    isBeer: Boolean
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
                .weight(0.5f),
            style = Typography.titleMedium.copy(textAlign = TextAlign.Right)
        )

        OutlinedTextField(
            value = value,
            onValueChange = { },
            modifier = Modifier
                .weight(0.8f)
                .border(
                    width = 1.dp,
                    MaterialTheme.colorScheme.surface,
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
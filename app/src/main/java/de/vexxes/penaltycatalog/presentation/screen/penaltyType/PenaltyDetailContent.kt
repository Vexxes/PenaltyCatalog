package de.vexxes.penaltycatalog.presentation.screen.penaltyType

import android.icu.text.NumberFormat
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import de.vexxes.penaltycatalog.domain.uistate.PenaltyTypeUiState
import de.vexxes.penaltycatalog.domain.uistate.penaltyTypeUiStateExample1
import de.vexxes.penaltycatalog.domain.uistate.penaltyTypeUiStateExample2
import de.vexxes.penaltycatalog.domain.visualTransformation.CurrencyAmountInputVisualTransformation
import de.vexxes.penaltycatalog.ui.theme.Typography

@Composable
fun PenaltyDetailContent(
    penaltyTypeUiState: PenaltyTypeUiState
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {
        PenaltyHeader(text = penaltyTypeUiState.name)
        if(penaltyTypeUiState.description.isNotEmpty()) { PenaltyDescription(text = penaltyTypeUiState.description) }
        PenaltyAmount(value = penaltyTypeUiState.value, isBeer = penaltyTypeUiState.isBeer)
        DeclaredPenalties(text = penaltyTypeUiState.valueDeclaredPenalties)
    }
}

@Composable
private fun PenaltyHeader(
    text: String
) {
    Text(
        text = text,
        style = Typography.headlineMedium
    )
}

@Composable
private fun PenaltyDescription(
    text: String
) {
    LabelHeader(text = stringResource(id = R.string.Description))

    Text(
        modifier = Modifier
            .fillMaxWidth(),
        text = text,
        style = Typography.titleMedium
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun PenaltyAmount(
    value: String,
    isBeer: Boolean
) {
    LabelHeader(text = stringResource(id = R.string.Amount))

    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        val visualTransformation = if (isBeer) VisualTransformation.None else CurrencyAmountInputVisualTransformation(
            fixedCursorAtTheEnd = true
        )

        Text(
            text = if (isBeer) stringResource(id = R.string.Box) else NumberFormat.getCurrencyInstance().currency.symbol,
            style = Typography.titleLarge.copy(textAlign = TextAlign.Right)
        )

        OutlinedTextField(
            value = value,
            onValueChange = { },
            modifier = Modifier
                .width(100.dp)
                .border(1.dp, MaterialTheme.colorScheme.surface, TextFieldDefaults.outlinedShape),
            enabled = false,
            readOnly = true,
            textStyle = Typography.titleLarge,
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
private fun LabelHeader(
    text: String
) {
    Text(
        modifier = Modifier
            .padding(top = 32.dp),
        text = text,
        style = Typography.labelLarge.copy(fontWeight = FontWeight.Bold),
        color = MaterialTheme.colorScheme.onSurfaceVariant
    )
}

/*TODO Read out the correct amount of declared penalties*/
@Composable
private fun DeclaredPenalties(
    text: String
) {
    LabelHeader(text = stringResource(id = R.string.DeclaredPenalties))

    Text(
        modifier = Modifier
            .fillMaxWidth(),
        text = text,
        style = Typography.titleLarge
    )
}

@Preview(showBackground = true)
@Composable
private fun PenaltyDetailContentMoneyPreview() {
    PenaltyDetailContent(penaltyTypeUiState = penaltyTypeUiStateExample1())
}

@Preview(showBackground = true)
@Composable
private fun PenaltyDetailContentBeerPreview() {
    PenaltyDetailContent(penaltyTypeUiState = penaltyTypeUiStateExample2())
}
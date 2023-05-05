package de.vexxes.penaltycatalog.presentation.screen.penaltyType

import android.icu.text.NumberFormat
import android.icu.util.Currency
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import de.vexxes.penaltycatalog.R
import de.vexxes.penaltycatalog.domain.uistate.PenaltyTypeUiState
import de.vexxes.penaltycatalog.domain.uistate.penaltyTypeUiStateExample1
import de.vexxes.penaltycatalog.domain.uistate.penaltyTypeUiStateExample2
import de.vexxes.penaltycatalog.domain.visualTransformation.NumberCommaTransformation
import de.vexxes.penaltycatalog.ui.theme.Typography

@Composable
fun PenaltyTypeDetailContent(
    penaltyTypeUiState: PenaltyTypeUiState
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {
        PenaltyTypeHeader(text = penaltyTypeUiState.name)
        if(penaltyTypeUiState.description.isNotEmpty()) { PenaltyTypeDescription(text = penaltyTypeUiState.description) }
        PenaltyTypeAmount(value = penaltyTypeUiState.value, isBeer = penaltyTypeUiState.isBeer)
        DeclaredPenalties(text = penaltyTypeUiState.valueDeclaredPenalties.toString())
    }
}

@Composable
private fun PenaltyTypeHeader(
    text: String
) {
    Text(
        text = text,
        style = Typography.headlineMedium
    )
}

@Composable
private fun PenaltyTypeDescription(
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

@Composable
private fun PenaltyTypeAmount(
    value: String,
    isBeer: Boolean
) {
    val format = NumberFormat.getCurrencyInstance()
    format.currency = Currency.getInstance("EUR")
    format.maximumFractionDigits = 2

    val leadingText = if (isBeer) "${stringResource(id = R.string.Box)} " else "${format.currency.symbol} "

    LabelHeader(text = stringResource(id = R.string.Amount))

    Row(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Text(
            text = leadingText,
            style = Typography.titleLarge
        )

        BasicTextField (
            value = value,
            onValueChange = { },
            enabled = false,
            textStyle = Typography.titleLarge.copy(color = MaterialTheme.colorScheme.onSurface),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            visualTransformation = NumberCommaTransformation()
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
private fun PenaltyTypeDetailContentMoneyPreview() {
    PenaltyTypeDetailContent(penaltyTypeUiState = penaltyTypeUiStateExample1())
}

@Preview(showBackground = true)
@Composable
private fun PenaltyTypeDetailContentBeerPreview() {
    PenaltyTypeDetailContent(penaltyTypeUiState = penaltyTypeUiStateExample2())
}
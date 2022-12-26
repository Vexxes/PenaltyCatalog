package de.vexxes.penaltycatalog.presentation.screen.penalty

import android.icu.text.NumberFormat
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import de.vexxes.penaltycatalog.R
import de.vexxes.penaltycatalog.domain.uistate.PenaltyUiState
import de.vexxes.penaltycatalog.domain.visualTransformation.CurrencyAmountInputVisualTransformation
import de.vexxes.penaltycatalog.ui.theme.Typography

@Composable
fun PenaltyDetailContent(
    penaltyUiState: PenaltyUiState
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {
        PenaltyHeader(text = penaltyUiState.name)
        PenaltyCategoryHeader(text = penaltyUiState.categoryName)
        if(penaltyUiState.description.isNotEmpty()) { PenaltyDescription(text = penaltyUiState.description) }
        PenaltyAmount(value = penaltyUiState.value)
        DeclaredPenalties()
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
private fun PenaltyCategoryHeader(
    text: String
) {
    Text(
        modifier = Modifier
            .padding(top = 8.dp),
        text = text,
        style = Typography.titleLarge
    )
}

@Composable
private fun PenaltyDescription(
    text: String
) {
    Text(
        modifier = Modifier
            .padding(top = 32.dp),
        text = stringResource(id = R.string.Description),
        style = Typography.labelLarge.copy(fontWeight = FontWeight.Bold)
    )

    Text(
        modifier = Modifier
            .fillMaxWidth(),
        text = text,
        style = Typography.titleLarge
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun PenaltyAmount(
    value: String
) {
    Text(
        modifier = Modifier
            .padding(top = 32.dp),
        text = stringResource(id = R.string.Amount),
        style = Typography.labelLarge.copy(fontWeight = FontWeight.Bold),
        color = MaterialTheme.colorScheme.onSurfaceVariant
    )

    OutlinedTextField(
        value = value,
        onValueChange = { },
        modifier = Modifier
            .fillMaxWidth()
            .border(width = 1.dp, MaterialTheme.colorScheme.surface, TextFieldDefaults.outlinedShape),
        enabled = false,
        readOnly = true,
        textStyle = Typography.titleLarge,
        visualTransformation = CurrencyAmountInputVisualTransformation(
            fixedCursorAtTheEnd = true
        ),
        leadingIcon = {
            Text(
                text = NumberFormat.getCurrencyInstance().currency.symbol,
                style = Typography.titleLarge
            )
        },
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

/*TODO Read out the correct amount of declared penalties*/
@Composable
private fun DeclaredPenalties() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(id = R.string.DeclaredPenalties),
            style = Typography.headlineSmall.copy(textDecoration = TextDecoration.Underline)
        )

        Text(
            text = "10",
            style = Typography.headlineMedium
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PenaltyDetailContentMoneyPreview() {

    val penaltyUiState = PenaltyUiState(
        id = "63717e8314ab74703f0ab5cb",
        name = "Monatsbeitrag",
        categoryName = "Monatsbeitrag",
        description = "",
        isBeer = false,
        value = "500"
    )

    PenaltyDetailContent(
        penaltyUiState = penaltyUiState
    )
}

@Preview(showBackground = true)
@Composable
private fun PenaltyDetailContentBeerPreview() {

    val penaltyUiState = PenaltyUiState(
        id = "63717e8314ab74703f0ab5cb",
        name = "Getränke zur Besprechung",
        categoryName = "Sonstiges",
        description = "",
        isBeer = true,
        value = "100"
    )

    PenaltyDetailContent(
        penaltyUiState = penaltyUiState
    )
}
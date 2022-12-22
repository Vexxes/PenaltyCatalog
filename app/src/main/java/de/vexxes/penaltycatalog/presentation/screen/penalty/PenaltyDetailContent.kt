package de.vexxes.penaltycatalog.presentation.screen.penalty

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
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
import de.vexxes.penaltycatalog.ui.theme.Typography
import java.text.NumberFormat
import java.util.Currency

@Composable
fun PenaltyDetailContent(
    penaltyUiState: PenaltyUiState
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        PenaltyHeader(text = penaltyUiState.name)
        PenaltyCategoryHeader(text = penaltyUiState.categoryName)
        if(penaltyUiState.description.isNotEmpty()) { PenaltyDescription(text = penaltyUiState.description) }
        PenaltyAmount(value = penaltyUiState.value.toInt(), isBeer = penaltyUiState.isBeer)
        DeclaredPenalties()
    }
}

@Composable
private fun PenaltyHeader(
    text: String
) {
    Text(
        modifier = Modifier
            .padding(8.dp),
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
            .padding(top = 8.dp, start = 8.dp, end = 8.dp),
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
            .padding(top = 32.dp, start = 8.dp, end = 8.dp),
        text = stringResource(id = R.string.Description),
        style = Typography.labelLarge.copy(fontWeight = FontWeight.Bold)
    )

    Text(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        text = text,
        style = Typography.titleLarge
    )
}

@Composable
private fun PenaltyAmount(
    value: Int,
    isBeer: Boolean
) {
    val text: String

    Text(
        modifier = Modifier
            .padding(top = 32.dp, start = 8.dp, end = 8.dp),
        text = stringResource(id = R.string.Amount),
        style = Typography.labelLarge.copy(fontWeight = FontWeight.Bold)
    )


    if(isBeer) {
        text = "$value " + stringResource(id = R.string.Box)
    } else {
        val format = NumberFormat.getCurrencyInstance()
        format.maximumFractionDigits = 2
        format.currency = Currency.getInstance("EUR")
        text = format.format(value.toDouble() / 100) // value is stored as cents, divide by 100
    }

    Text(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        text = text,
        style = Typography.titleLarge
    )
}

/*TODO Read out the correct amount of declared penalties*/
@Composable
private fun DeclaredPenalties() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 32.dp, start = 8.dp, end = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            modifier = Modifier
                .padding(8.dp),
            text = stringResource(id = R.string.DeclaredPenalties),
            style = Typography.headlineSmall.copy(textDecoration = TextDecoration.Underline)
        )

        Text(
            modifier = Modifier
                .padding(8.dp),
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
        value = "1"
    )

    PenaltyDetailContent(
        penaltyUiState = penaltyUiState
    )
}
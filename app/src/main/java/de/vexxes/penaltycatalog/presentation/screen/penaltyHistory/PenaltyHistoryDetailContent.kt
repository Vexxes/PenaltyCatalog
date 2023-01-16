package de.vexxes.penaltycatalog.presentation.screen.penaltyHistory

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import de.vexxes.penaltycatalog.domain.uistate.PenaltyHistoryUiState

@Composable
fun PenaltyHistoryDetailContent(
    penaltyHistoryUiState: PenaltyHistoryUiState,
    onPaidState: (Boolean) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {
        /* TODO: Read penaltyName and playerName
        PenaltyHistoryHeader(text = penaltyHistoryUiState.penaltyName)
        PenaltyHistoryButtonsPaid(
            penaltyPaid = penaltyHistoryUiState.penaltyPaid,
            onPaidState = onPaidState
        )
        PenaltyHistoryPlayerName(penaltyHistoryUiState.playerName)
        PenaltyHistoryDateOfPenalty(penaltyHistoryUiState.timeOfPenalty.toString())
        PenaltyAmount(
            value = penaltyHistoryUiState.penaltyValue,
            isBeer = penaltyHistoryUiState.penaltyIsBeer
        )
         */
    }
}

/*
@Composable
private fun PenaltyHistoryHeader(
    text: String
) {
    Text(
        text = text,
        style = Typography.headlineMedium
    )
}

@Composable
private fun PenaltyHistoryButtonsPaid(
    penaltyPaid: Boolean,
    onPaidState: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .padding(top = 8.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        ChipBox(
            text = stringResource(id = R.string.Paid),
            paid = penaltyPaid,
            foregroundColor = colorSchemeSegButtons().foregroundPaid,
            backgroundColor = colorSchemeSegButtons().backgroundPaid,
            imageVector = Icons.Default.ThumbUp,
            onButtonPressed = { onPaidState(true) }
        )

        ChipBox(
            text = stringResource(id = R.string.NotPaid),
            paid = !penaltyPaid,
            foregroundColor = colorSchemeSegButtons().foregroundNotPaid,
            backgroundColor = colorSchemeSegButtons().backgroundNotPaid,
            imageVector = Icons.Default.ThumbDown,
            onButtonPressed = { onPaidState(false) }
        )
    }
}

@Composable
private fun ChipBox(
    text: String,
    paid: Boolean,
    foregroundColor: Color,
    backgroundColor: Color,
    imageVector: ImageVector,
    onButtonPressed: () -> Unit
) {
    val alpha: Float by animateFloatAsState(
        targetValue = if (paid) {
            1f
        } else {
            0f
        }
    )

    Box(
        modifier = Modifier
            .size(DpSize(150.dp, 50.dp))
            .padding(start = 8.dp, end = 8.dp)
            .border(1.dp, MaterialTheme.colorScheme.outline, RoundedCornerShape(15.dp))
            .clip(RoundedCornerShape(15.dp))
            .background(if (paid) backgroundColor else MaterialTheme.colorScheme.surface)
            .clickable { onButtonPressed() },
        contentAlignment = Alignment.CenterStart
    ) {
        Row(modifier = Modifier
            .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                modifier = Modifier
                    .padding(start = 8.dp)
                    .alpha(alpha),
                imageVector = imageVector,
                contentDescription = "",
                tint = if (paid) foregroundColor else MaterialTheme.colorScheme.onSurface
            )
            Text(
                modifier = Modifier
                    .padding(start = 8.dp, end = 8.dp)
                    .fillMaxWidth(),
                text = text,
                style = Typography.titleMedium,
                color = if (paid) foregroundColor else MaterialTheme.colorScheme.onSurface,
            )
        }
    }
}

@Composable
private fun PenaltyHistoryPlayerName(
    text: String
) {
    LabelHeader(text = stringResource(id = R.string.Player))
    ValueText(text = text)
}

@Composable
private fun PenaltyHistoryDateOfPenalty(
    text: String
) {
    LabelHeader(text = stringResource(id = R.string.DateOfPenalty))
    ValueText(text = text)
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

@Composable
private fun ValueText(
    text: String
) {
    Text(
        modifier = Modifier
            .fillMaxWidth(),
        text = text,
        style = Typography.titleLarge
    )
}

@Preview(showBackground = true)
@Composable
private fun PenaltyHistoryDetailContentPreview1() {
    PenaltyHistoryDetailContent(penaltyHistoryUiStateExample1()) { }
}

@Preview(showBackground = true)
@Composable
private fun PenaltyHistoryDetailContentPreview2() {
    PenaltyHistoryDetailContent(penaltyHistoryUiStateExample2()) { }
}

@Preview(showBackground = true)
@Composable
private fun PenaltyHistoryDetailContentPreview3() {
    PenaltyHistoryDetailContent(penaltyHistoryUiStateExample3()) { }
}
*/
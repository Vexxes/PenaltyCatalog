package de.vexxes.penaltycatalog.presentation.screen.history

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ThumbDown
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import de.vexxes.penaltycatalog.R
import de.vexxes.penaltycatalog.domain.uistate.PenaltyHistoryUiState
import de.vexxes.penaltycatalog.domain.visualTransformation.CurrencyAmountInputVisualTransformation
import de.vexxes.penaltycatalog.ui.theme.Typography
import de.vexxes.penaltycatalog.ui.theme.colorSchemeSegButtons
import java.time.LocalDate

@Composable
fun PenaltyHistoryDetailContent(
    penaltyHistoryUiState: PenaltyHistoryUiState
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {
        PenaltyHistoryHeader(text = penaltyHistoryUiState.penaltyName)
        PenaltyHistoryButtonsPaid(
            penaltyPaid = penaltyHistoryUiState.penaltyPaid,
            onButtonPressed = { /*TODO Implement function*/}
        ) /*TODO Change checkbox */
        PenaltyHistoryPlayerName(penaltyHistoryUiState.playerName)
        PenaltyHistoryDateOfPenalty(penaltyHistoryUiState.timeOfPenalty.toString())
        PenaltyAmount(
            value = penaltyHistoryUiState.penaltyValue,
            isBeer = penaltyHistoryUiState.penaltyIsBeer
        )
    }
}

@Composable
private fun PenaltyHistoryHeader(
    text: String
) {
    Text(
        text = text,
        style = Typography.headlineMedium
    )
}

/*TODO: Outsource the boxes and summarize the functionality*/
@Composable
private fun PenaltyHistoryButtonsPaid(
    penaltyPaid: Boolean,
    onButtonPressed: (Boolean) -> Unit
) {
    val size = DpSize(150.dp, 50.dp)

    val alpha: Float by animateFloatAsState(
        targetValue = if (penaltyPaid) {
            1f
        } else {
            0f
        },
        animationSpec = tween(
            durationMillis = 3000,
            easing = LinearEasing,
        ),
    )

    Row(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier
                .size(size)
                .border(1.dp, MaterialTheme.colorScheme.outline, RoundedCornerShape(15.dp))
                .clip(RoundedCornerShape(15.dp))
                .background(if (penaltyPaid) colorSchemeSegButtons().backgroundPaid else MaterialTheme.colorScheme.surface)
                .clickable { onButtonPressed(true) },
            contentAlignment = Alignment.CenterStart
        ) {
            Row(modifier = Modifier
                .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    modifier = Modifier
                        .alpha(alpha),
                    imageVector = Icons.Default.ThumbUp,
                    contentDescription = "",
                    tint = if (penaltyPaid) colorSchemeSegButtons().foregroundPaid else MaterialTheme.colorScheme.onSurface
                )
                Text(
                    modifier = Modifier
                        .padding(end = 16.dp)
                        .fillMaxWidth(),
                    text = stringResource(id = R.string.Paid),
                    style = Typography.titleMedium,
                    color = if (penaltyPaid) colorSchemeSegButtons().foregroundPaid else MaterialTheme.colorScheme.onSurface,
                )
            }
        }

        Box(
            modifier = Modifier
                .size(size)
                .padding(start = 8.dp, end = 8.dp)
                .border(1.dp, MaterialTheme.colorScheme.outline, RoundedCornerShape(15.dp))
                .clip(RoundedCornerShape(15.dp))
                .background(if (!penaltyPaid) colorSchemeSegButtons().backgroundNotPaid else MaterialTheme.colorScheme.surface)
                .clickable { onButtonPressed(false) },
            contentAlignment = Alignment.CenterStart
        ) {
            Row(modifier = Modifier
                .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    modifier = Modifier
                        .padding(8.dp)
                        .alpha(1f - alpha),
                    imageVector = Icons.Default.ThumbDown,
                    contentDescription = "",
                    tint = if (!penaltyPaid) colorSchemeSegButtons().foregroundNotPaid else MaterialTheme.colorScheme.onSurface
                )
                Text(
                    modifier = Modifier
                        .padding(end = 16.dp)
                        .fillMaxWidth(),
                    text = stringResource(id = R.string.NotPaid),
                    style = Typography.titleMedium,
                    color = if (!penaltyPaid) colorSchemeSegButtons().foregroundNotPaid else MaterialTheme.colorScheme.onSurface
                )
            }
        }
    }
}

@Composable
private fun PenaltyHistoryPlayerName(
    text: String
) {
    Text(
        modifier = Modifier
            .padding(top = 32.dp),
        text = stringResource(id = R.string.Player),
        style = Typography.labelLarge.copy(fontWeight = FontWeight.Bold),
        color = MaterialTheme.colorScheme.onSurfaceVariant
    )

    Text(
        modifier = Modifier
            .fillMaxWidth(),
        text = text,
        style = Typography.titleLarge
    )
}

@Composable
private fun PenaltyHistoryDateOfPenalty(
    text: String
) {
    Text(
        modifier = Modifier
            .padding(top = 32.dp),
        text = stringResource(id = R.string.DateOfPenalty),
        style = Typography.labelLarge.copy(fontWeight = FontWeight.Bold),
        color = MaterialTheme.colorScheme.onSurfaceVariant
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
    value: String,
    isBeer: Boolean
) {
/*    val text: String

    Text(
        modifier = Modifier
            .padding(top = 32.dp, start = 8.dp, end = 8.dp),
        text = stringResource(id = R.string.Amount),
        style = Typography.labelLarge.copy(fontWeight = FontWeight.Bold),
        color = MaterialTheme.colorScheme.onSurfaceVariant
    )

    if(isBeer) {
        text = "$value " + stringResource(id = R.string.Box)
    } else {
        val format = NumberFormat.getCurrencyInstance()
        format.maximumFractionDigits = 2
        format.currency = Currency.getInstance("EUR")
        text = format.format(value)
    }

    Text(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 8.dp),
        text = text,
        style = Typography.titleLarge
    )*/

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
            .border(
                width = 1.dp,
                MaterialTheme.colorScheme.surface,
                TextFieldDefaults.outlinedShape
            ),
        enabled = false,
        readOnly = true,
        textStyle = Typography.titleLarge,
        visualTransformation = if (!isBeer) {
            CurrencyAmountInputVisualTransformation(
                fixedCursorAtTheEnd = true
            )
        } else {
            VisualTransformation.None
        },
        leadingIcon = {
            /*TODO: Currency symbol should only be shown, if isBeer is false*/
            Text(
                text = android.icu.text.NumberFormat.getCurrencyInstance().currency.symbol,
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

@Preview(showBackground = true)
@Composable
private fun PenaltyHistoryDetailContentPreview1() {
    val penaltyUiState = PenaltyHistoryUiState(
        id = "",
        playerName = "Thomas Schneider",
        penaltyName = "Verspätete Zahlung des Monatsbeitrag",
        penaltyValue = "5",
        penaltyIsBeer = false,
        timeOfPenalty = LocalDate.now(),
        penaltyPaid = false
    )

    PenaltyHistoryDetailContent(penaltyUiState)
}

@Preview(showBackground = true)
@Composable
private fun PenaltyHistoryDetailContentPreview2() {
    val penaltyHistoryUiState = PenaltyHistoryUiState(
        id = "",
        playerName = "Thomas Schneider",
        penaltyName = "Verspätete Zahlung des Monatsbeitrag",
        penaltyValue = "5",
        penaltyIsBeer = false,
        timeOfPenalty = LocalDate.now(),
        penaltyPaid = true
    )

    PenaltyHistoryDetailContent(penaltyHistoryUiState)
}

@Preview(showBackground = true)
@Composable
private fun PenaltyHistoryDetailContentPreview3() {
    val penaltyHistoryUiState = PenaltyHistoryUiState(
        id = "",
        playerName = "Thomas Schneider",
        penaltyName = "Geburtstag",
        penaltyValue = "1",
        penaltyIsBeer = true,
        timeOfPenalty = LocalDate.now(),
        penaltyPaid = true
    )

    PenaltyHistoryDetailContent(penaltyHistoryUiState)
}
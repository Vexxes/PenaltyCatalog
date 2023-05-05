package de.vexxes.penaltycatalog.presentation.screen.penaltyReceived

import android.icu.text.NumberFormat
import android.icu.util.Currency
import androidx.compose.animation.core.animateFloatAsState
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
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ThumbDown
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import de.vexxes.penaltycatalog.R
import de.vexxes.penaltycatalog.domain.uistate.PenaltyReceivedUiState
import de.vexxes.penaltycatalog.domain.uistate.penaltyReceivedUiStateExample1
import de.vexxes.penaltycatalog.domain.uistate.penaltyReceivedUiStateExample2
import de.vexxes.penaltycatalog.domain.uistate.penaltyReceivedUiStateExample3
import de.vexxes.penaltycatalog.domain.visualTransformation.NumberCommaTransformation
import de.vexxes.penaltycatalog.ui.theme.Typography
import de.vexxes.penaltycatalog.ui.theme.colorSchemeSegButtons
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toJavaLocalDate
import kotlinx.datetime.toLocalDateTime
import java.time.format.DateTimeFormatter

@Composable
fun PenaltyReceivedDetailContent(
    penaltyReceivedUiState: PenaltyReceivedUiState,
    onPaidStateChanged: (LocalDate?) -> Unit
) {
    val timeOfPenalty = penaltyReceivedUiState.timeOfPenalty
    val timeOfPenaltyPaid = penaltyReceivedUiState.timeOfPenaltyPaid

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {
        PenaltyReceivedHeader(text = penaltyReceivedUiState.penaltyName)

        PenaltyHistoryButtonsPaid(
            penaltyPaid = if(timeOfPenaltyPaid != null) timeOfPenaltyPaid >= timeOfPenalty else false,
            onPaidStateChanged = onPaidStateChanged
        )

        PenaltyReceivedPlayerName(text = penaltyReceivedUiState.playerName)

        PenaltyReceivedAmount(
            value = penaltyReceivedUiState.penaltyValue,
            isBeer = penaltyReceivedUiState.penaltyIsBeer)

        PenaltyReceivedTime(date = penaltyReceivedUiState.timeOfPenalty, R.string.TimeOfPenalty)

        if (penaltyReceivedUiState.timeOfPenaltyPaid != null) PenaltyReceivedTime(date = penaltyReceivedUiState.timeOfPenaltyPaid, labelId = R.string.TimeOfPenaltyPaid)
    }
}

@Composable
private fun PenaltyReceivedHeader(
    text: String
) {
    Text(
        text = text,
        style = Typography.headlineMedium
    )
}

@Composable
private fun PenaltyReceivedPlayerName(
    text: String
) {
    LabelHeader(text = stringResource(id = R.string.Player))

    Text(
        modifier = Modifier
            .fillMaxWidth(),
        text = text,
        style = Typography.titleLarge
    )
}

@Composable
private fun PenaltyReceivedAmount(
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
private fun PenaltyReceivedTime(
    date: LocalDate,
    labelId: Int
) {
    val output = DateTimeFormatter
        .ofPattern("eeee, dd. MMMM y")
        .format(date.toJavaLocalDate())

    LabelHeader(text = stringResource(id = labelId))

    Text(
        modifier = Modifier
            .fillMaxWidth(),
        text = output,
        style = Typography.titleLarge
    )
}

@Composable
private fun PenaltyHistoryButtonsPaid(
    penaltyPaid: Boolean,
    onPaidStateChanged: (LocalDate?) -> Unit
) {
    Row(
        modifier = Modifier
            .padding(top = 32.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        ChipBox(
            text = stringResource(id = R.string.Paid),
            paid = penaltyPaid,
            foregroundColor = colorSchemeSegButtons().foregroundPaid,
            backgroundColor = colorSchemeSegButtons().backgroundPaid,
            imageVector = Icons.Default.ThumbUp,
            onButtonPressed = { onPaidStateChanged(Clock.System.now().toLocalDateTime(TimeZone.UTC).date) }
        )

        ChipBox(
            text = stringResource(id = R.string.NotPaid),
            paid = !penaltyPaid,
            foregroundColor = colorSchemeSegButtons().foregroundNotPaid,
            backgroundColor = colorSchemeSegButtons().backgroundNotPaid,
            imageVector = Icons.Default.ThumbDown,
            onButtonPressed = { onPaidStateChanged(null) }
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
        },
        label = "alpha-value"
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
@Preview(showBackground = true)
private fun PenaltyReceivedDetailContentPreview1() {
    PenaltyReceivedDetailContent(
        penaltyReceivedUiState = penaltyReceivedUiStateExample1(),
        onPaidStateChanged = { }
    )
}

@Composable
@Preview(showBackground = true)
private fun PenaltyReceivedDetailContentPreview2() {
    PenaltyReceivedDetailContent(
        penaltyReceivedUiState = penaltyReceivedUiStateExample2(),
        onPaidStateChanged = { }
    )
}

@Composable
@Preview(showBackground = true)
private fun PenaltyReceivedDetailContentPreview3() {
    PenaltyReceivedDetailContent(
        penaltyReceivedUiState = penaltyReceivedUiStateExample3(),
        onPaidStateChanged = { }
    )
}
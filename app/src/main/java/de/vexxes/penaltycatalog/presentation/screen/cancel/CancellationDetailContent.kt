package de.vexxes.penaltycatalog.presentation.screen.cancel

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import de.vexxes.penaltycatalog.R
import de.vexxes.penaltycatalog.domain.uistate.CancellationUiState
import de.vexxes.penaltycatalog.domain.uistate.cancellationUiStateExample1
import de.vexxes.penaltycatalog.domain.uistate.cancellationUiStateExample2
import de.vexxes.penaltycatalog.domain.uistate.cancellationUiStateExample3
import de.vexxes.penaltycatalog.ui.theme.Typography
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.toJavaLocalDateTime
import java.time.format.DateTimeFormatter

@Composable
fun CancellationDetailContent(
    cancellationUiState: CancellationUiState
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {
        CancellationHeader(dateTime =  cancellationUiState.eventStartOfEvent)
        CancellationPlayerName(text = cancellationUiState.playerName)
        CancellationTimeOfCancellation(dateTime = cancellationUiState.timeOfCancellation)
        CancellationEventName(text = cancellationUiState.eventTitle)
    }
}

@Composable
private fun CancellationHeader(
    dateTime: LocalDateTime?
) {
    val output = DateTimeFormatter
        .ofPattern("dd. MMMM y")
        .format(dateTime?.toJavaLocalDateTime())

    Text(
        text = stringResource(id = R.string.CancelledFor) + " " + output,
        style = Typography.titleLarge
    )
}

@Composable
private fun CancellationPlayerName(
    text: String
) {
    LabelHeader(
        modifier = Modifier.padding(top = 32.dp),
        text = stringResource(id = R.string.Player)
    )
    TextValue(text = text)
}

@Composable
private fun CancellationTimeOfCancellation(
    dateTime: LocalDateTime
) {
    val output = DateTimeFormatter
        .ofPattern("dd. MMMM y HH:mm")
        .format(dateTime.toJavaLocalDateTime())

    LabelHeader(
        modifier = Modifier.padding(top = 32.dp),
        text = stringResource(id = R.string.TimeOfCancellation)
    )
    TextValue(text = output)
}

@Composable
private fun CancellationEventName(
    text: String
) {
    LabelHeader(
        modifier = Modifier.padding(top = 32.dp),
        text = stringResource(id = R.string.EventTitle)
    )
    TextValue(text = text)
}

@Composable
private fun LabelHeader(
    modifier: Modifier = Modifier,
    text: String,
    textAlign: TextAlign? = null
) {
    Text(
        modifier = modifier,
        text = text,
        style = Typography.labelMedium.copy(fontWeight = FontWeight.Bold),
        color = MaterialTheme.colorScheme.onSurfaceVariant,
        textAlign = textAlign
    )
}

@Composable
private fun TextValue(
    text: String
) {
    Text(
        text = text,
        style = Typography.bodyLarge
    )
}

@Composable
@Preview(showBackground = true)
private fun CancellationDetailContentPreview1() {
    CancellationDetailContent(cancellationUiState = cancellationUiStateExample1())
}

@Composable
@Preview(showBackground = true)
private fun CancellationDetailContentPreview2() {
    CancellationDetailContent(cancellationUiState = cancellationUiStateExample2())
}

@Composable
@Preview(showBackground = true)
private fun CancellationDetailContentPreview3() {
    CancellationDetailContent(cancellationUiState = cancellationUiStateExample3())
}
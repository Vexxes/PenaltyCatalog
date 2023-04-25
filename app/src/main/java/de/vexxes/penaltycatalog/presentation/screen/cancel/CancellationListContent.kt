package de.vexxes.penaltycatalog.presentation.screen.cancel

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import de.vexxes.penaltycatalog.R
import de.vexxes.penaltycatalog.component.EmptyContent
import de.vexxes.penaltycatalog.domain.uistate.CancellationUiState
import de.vexxes.penaltycatalog.domain.uistate.cancellationUiStateExample1
import de.vexxes.penaltycatalog.domain.uistate.cancellationUiStateExample2
import de.vexxes.penaltycatalog.domain.uistate.cancellationUiStateExample3
import de.vexxes.penaltycatalog.ui.theme.PenaltyCatalogTheme
import de.vexxes.penaltycatalog.ui.theme.Typography
import kotlinx.datetime.toJavaLocalDateTime
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CancellationListContent(
    cancellationUiStates: List<CancellationUiState>,
    todayFilterSelected: Boolean,
    navigateToCancellationDetailScreen: (cancellationId: String) -> Unit,
    onTodayFilterChipClicked: () -> Unit
) {

    Column(
        modifier = Modifier.padding(start = 8.dp, end = 8.dp)
    ) {
        FilterChip(
            selected = todayFilterSelected,
            onClick = { onTodayFilterChipClicked() },
            label = { Text(stringResource(id = R.string.Today)) }
        )

        if (cancellationUiStates.isNotEmpty()) {
            DisplayCancellations(
                cancellationUiStates = cancellationUiStates,
                navigateToCancellationDetailScreen = navigateToCancellationDetailScreen
            )
        } else {
            EmptyContent()
        }
    }
}

@Composable
private fun DisplayCancellations(
    cancellationUiStates: List<CancellationUiState>,
    navigateToCancellationDetailScreen: (cancellationId: String) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        items(
            items = cancellationUiStates,
            key = { cancellationUiState ->
                cancellationUiState.hashCode()
            }
        ) { cancellationUiState ->
            CancellationItem(
                cancellationUiState = cancellationUiState,
                navigateToCancellationDetailScreen = navigateToCancellationDetailScreen
            )
        }
    }
}

@Composable
private fun CancellationItem(
    cancellationUiState: CancellationUiState,
    navigateToCancellationDetailScreen: (cancellationId: String) -> Unit
) {
    val timeOfCancellationFormatted = DateTimeFormatter
        .ofPattern("eee, dd. MMMM y HH:mm")
        .format(cancellationUiState.timeOfCancellation.toJavaLocalDateTime())
    val eventStartOfEventFormatted = cancellationUiState.eventStartOfEvent.let {
        DateTimeFormatter
            .ofPattern("eee, dd. MMMM y HH:mm")
            .format(it.toJavaLocalDateTime())
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .clickable { navigateToCancellationDetailScreen(cancellationUiState.id) }
    ) {
        Column(
            modifier = Modifier
                .weight(0.5f)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = cancellationUiState.playerName,
                style = Typography.bodyMedium
            )
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = timeOfCancellationFormatted,
                style = Typography.bodyMedium
            )
        }

        Divider(
            modifier = Modifier
                .padding(start = 4.dp, end = 4.dp)
                .fillMaxHeight()
                .width(1.dp)
        )

        Column(
            modifier = Modifier
                .weight(0.5f)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center
        ) {
            if (eventStartOfEventFormatted != null) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = eventStartOfEventFormatted,
                    style = Typography.bodyMedium.copy(textAlign = TextAlign.Center)
                )
            }
        }
    }
}

@Composable
@Preview(name = "Light Theme", showBackground = true)
private fun CancellationItemPreview() {
    PenaltyCatalogTheme {
        CancellationItem(
            cancellationUiState = cancellationUiStateExample1(),
            navigateToCancellationDetailScreen = { }
        )
    }
}

@Composable
@Preview(name = "Light Theme", showBackground = true)
private fun CancellationListPreview() {
    PenaltyCatalogTheme {
        CancellationListContent(
            cancellationUiStates = listOf(
                cancellationUiStateExample1(),
                cancellationUiStateExample2(),
                cancellationUiStateExample3()
            ),
            todayFilterSelected = false,
            navigateToCancellationDetailScreen = { },
            onTodayFilterChipClicked = { }
        )
    }
}
package de.vexxes.penaltycatalog.presentation.screen.cancel

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.maxkeppeker.sheets.core.models.base.rememberUseCaseState
import com.maxkeppeler.sheets.date_time.DateTimeDialog
import com.maxkeppeler.sheets.date_time.models.DateTimeSelection
import de.vexxes.penaltycatalog.R
import de.vexxes.penaltycatalog.component.InputOutlinedField
import de.vexxes.penaltycatalog.domain.model.Event
import de.vexxes.penaltycatalog.domain.model.Player
import de.vexxes.penaltycatalog.domain.uistate.CancellationUiState
import de.vexxes.penaltycatalog.domain.uistate.cancellationUiStateExample1
import de.vexxes.penaltycatalog.ui.theme.Typography
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toJavaLocalDateTime
import kotlinx.datetime.toKotlinLocalDateTime
import kotlinx.datetime.toLocalDateTime
import java.time.format.DateTimeFormatter

@Composable
fun CancellationEditContent(
    cancellationUiState: CancellationUiState,
    players: List<Player>,
    events: List<Event>,
    onPlayerIdChanged: (String) -> Unit,
    onTimeOfCancellationChanged: (LocalDateTime) -> Unit,
    onEventIdChanged: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
            .verticalScroll(rememberScrollState())
    ) {
        CancellationPlayerExposedMenu(
            text = cancellationUiState.playerName,
            error = cancellationUiState.playerIdError,
            playerList = players,
            onPlayerIdChanged = onPlayerIdChanged
        )

        CancellationEventExposedMenu(
            text = cancellationUiState.eventTitle,
            error = cancellationUiState.eventIdError,
            eventList = events,
            onEventIdChanged = onEventIdChanged
        )

        CancellationDatePicker(
            dateTime = cancellationUiState.timeOfCancellation,
            onDateTimeChanged = onTimeOfCancellationChanged
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CancellationPlayerExposedMenu(
    text: String,
    error: Boolean,
    playerList: List<Player>,
    onPlayerIdChanged: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    val angle: Float by animateFloatAsState(
        targetValue = if (expanded) 180f else 0f, label = "Animate expanded icon"
    )

    ExposedDropdownMenuBox(
        modifier = Modifier.padding(top = 16.dp),
        expanded = expanded,
        onExpandedChange = { expanded = it }
    ) {
        InputOutlinedField(
            modifier = Modifier
                .menuAnchor()
                .clickable {
                    expanded = true
                },
            readOnly = true,
            text = text,
            onTextChanged = { },
            label = stringResource(id = R.string.Player),
            required = true,
            trailingIcon = {
                IconButton(
                    modifier = Modifier
                        .rotate(angle),
                    onClick = { expanded = true }) {
                    Icon(
                        imageVector = Icons.Default.ArrowDropDown,
                        contentDescription = ""
                    )
                }
            },
            isError = error
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
        ) {
            playerList.sortedWith(compareBy { it.lastName }).forEach { player ->
                DropdownMenuItem(
                    onClick = {
                        expanded = false
                        onPlayerIdChanged(player.id)
                    },
                    text = {
                        Text(
                            text = "${player.lastName}, ${player.firstName}",
                            style = Typography.bodyLarge
                        )
                    }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CancellationEventExposedMenu(
    text: String,
    error: Boolean,
    eventList: List<Event>,
    onEventIdChanged: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    val angle: Float by animateFloatAsState(
        targetValue = if (expanded) 180f else 0f, label = "Animate expanded icon"
    )

    ExposedDropdownMenuBox(
        modifier = Modifier.padding(top = 16.dp),
        expanded = expanded,
        onExpandedChange = { expanded = it }
    ) {
        InputOutlinedField(
            modifier = Modifier
                .menuAnchor()
                .clickable {
                    expanded = true
                },
            readOnly = true,
            text = text,
            onTextChanged = { },
            label = stringResource(id = R.string.Events),
            required = true,
            trailingIcon = {
                IconButton(
                    modifier = Modifier
                        .rotate(angle),
                    onClick = { expanded = true }) {
                    Icon(
                        imageVector = Icons.Default.ArrowDropDown,
                        contentDescription = ""
                    )
                }
            },
            isError = error
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
        ) {
            eventList.sortedWith(compareBy { it.startOfEvent.date }).forEach { event ->

                // Show only future events
                if (Clock.System.now().toLocalDateTime(TimeZone.UTC).date < event.startOfEvent.date) {
                    val output = DateTimeFormatter
                        .ofPattern("eeee, dd. MMMM y")
                        .format(event.startOfEvent.toJavaLocalDateTime())

                    DropdownMenuItem(
                        onClick = {
                            expanded = false
                            onEventIdChanged(event.id)
                        },
                        text = {
                            Text(
                                text = output,
                                style = Typography.bodyLarge
                            )
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun CancellationDatePicker(
    dateTime: LocalDateTime,
    onDateTimeChanged: (LocalDateTime) -> Unit
) {
    val output = DateTimeFormatter
        .ofPattern("eeee, dd. MMMM y HH:mm:ss")
        .format(dateTime.toJavaLocalDateTime())

    val dateTimeSheetState = rememberUseCaseState()

    val openDateTimeDialog = { dateTimeSheetState.show() }

    DateTimeDialog(
        state = dateTimeSheetState,
        selection = DateTimeSelection.DateTime { newDateTime ->
            println(newDateTime)
            println(java.time.LocalDateTime.now())

            onDateTimeChanged(newDateTime.toKotlinLocalDateTime())
        }
    )

    InputOutlinedField(
        modifier = Modifier
            .clickable { openDateTimeDialog() }
            .padding(top = 16.dp),
        enabled = false,
        readOnly = true,
        text = output,
        onTextChanged = { },
        label = stringResource(id = R.string.TimeOfCancellation),
        trailingIcon = {
            IconButton(
                onClick = { openDateTimeDialog() }) {
                Icon(
                    imageVector = Icons.Default.DateRange,
                    contentDescription = ""
                )
            }
        },
        colors = OutlinedTextFieldDefaults.colors(
            disabledTextColor = MaterialTheme.colorScheme.onSurface,
            disabledBorderColor = MaterialTheme.colorScheme.outline,
            disabledLeadingIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
            disabledTrailingIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
            disabledLabelColor = MaterialTheme.colorScheme.onSurfaceVariant,
            disabledPlaceholderColor = MaterialTheme.colorScheme.onSurfaceVariant,
        )
    )
}

@Composable
@Preview(showBackground = true)
private fun CancellationEditContentPreview1() {
    CancellationEditContent(
        cancellationUiState = cancellationUiStateExample1(),
        players = emptyList(),
        events = emptyList(),
        onPlayerIdChanged = { },
        onTimeOfCancellationChanged = { },
        onEventIdChanged = { }
    )
}
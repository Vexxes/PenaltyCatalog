package de.vexxes.penaltycatalog.presentation.screen.events

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
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
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
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
import com.maxkeppeker.sheets.core.models.base.rememberSheetState
import com.maxkeppeler.sheets.date_time.DateTimeDialog
import com.maxkeppeler.sheets.date_time.models.DateTimeSelection
import de.vexxes.penaltycatalog.R
import de.vexxes.penaltycatalog.component.InputOutlinedField
import de.vexxes.penaltycatalog.domain.enums.EventType
import de.vexxes.penaltycatalog.domain.uistate.EventUiState
import de.vexxes.penaltycatalog.domain.uistate.eventUiStateExample1
import de.vexxes.penaltycatalog.domain.uistate.eventUiStateExample2
import de.vexxes.penaltycatalog.domain.uistate.eventUiStateExample3
import de.vexxes.penaltycatalog.ui.theme.Typography
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.toJavaLocalDateTime
import kotlinx.datetime.toKotlinLocalDateTime
import java.time.format.DateTimeFormatter

@Composable
fun EventEditContent(
    eventUiState: EventUiState,
    onEventTitleChanged: (String) -> Unit,
    onEventTypeChanged: (EventType) -> Unit,
    onStartOfEventChanged: (LocalDateTime) -> Unit,
    onStartOfMeetingChanged: (LocalDateTime) -> Unit,
    onAddressChanged: (String) -> Unit,
    onDescriptionChanged: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        EventTitle(
            text = eventUiState.title,
            error = eventUiState.titleError,
            onTextChanged = onEventTitleChanged
        )

        EventTypeExposedMenu(
            eventType = eventUiState.type,
            onEventTypeChanged = onEventTypeChanged
        )

        EventDatePicker(
            dateTime = eventUiState.startOfEvent,
            onDateTimeChanged = onStartOfEventChanged
        )

        EventDatePicker(
            dateTime = eventUiState.startOfMeeting,
            onDateTimeChanged = onStartOfMeetingChanged
        )

        EventAddress(
            text = eventUiState.address,
            onTextChanged = onAddressChanged
        )

        EventDescription(
            text = eventUiState.description,
            onTextChanged = onDescriptionChanged
        )
    }
}

@Composable
private fun EventTitle(
    text: String,
    error: Boolean,
    onTextChanged: (String) -> Unit
) {
    InputOutlinedField(
        modifier = Modifier
            .height(90.dp),
        text = text,
        onTextChanged = onTextChanged,
        label = stringResource(id = R.string.EventTitle),
        required = true,
        isError = error
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun EventTypeExposedMenu(
    eventType: EventType,
    onEventTypeChanged: (EventType) -> Unit
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
            text = stringResource(id = eventType.nameId),
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
            }
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
        ) {
            EventType.values().forEach { eventType ->
                DropdownMenuItem(
                    onClick = {
                        expanded = false
                        onEventTypeChanged(eventType)
                    },
                    text = {
                        Text(
                            text = stringResource(id = eventType.nameId),
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
private fun EventDatePicker(
    dateTime: LocalDateTime,
    onDateTimeChanged: (LocalDateTime) -> Unit
) {
    val output = DateTimeFormatter
        .ofPattern("eeee, dd. MMMM y HH:mm:ss")
        .format(dateTime.toJavaLocalDateTime())

    val dateTimeSheetState = rememberSheetState()

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
        label = stringResource(id = R.string.StartOfEvent),
        trailingIcon = {
            IconButton(
                onClick = { openDateTimeDialog() }) {
                Icon(
                    imageVector = Icons.Default.DateRange,
                    contentDescription = ""
                )
            }
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

@Composable
private fun EventAddress(
    text: String,
    onTextChanged: (String) -> Unit
) {
    InputOutlinedField(
        modifier = Modifier
            .padding(top = 16.dp),
        text = text,
        onTextChanged = onTextChanged,
        label = stringResource(id = R.string.Address)
    )
}

@Composable
private fun EventDescription(
    text: String,
    onTextChanged: (String) -> Unit
) {
    InputOutlinedField(
        modifier = Modifier
            .padding(top = 16.dp)
            .fillMaxSize()
            .height(210.dp),
        text = text,
        onTextChanged = onTextChanged,
        label = stringResource(id = R.string.EventDescription)
    )
}

@Composable
@Preview(showBackground = true)
private fun EventEditContentPreview1() {
    EventEditContent(
        eventUiState = eventUiStateExample1(),
        onEventTitleChanged = { },
        onEventTypeChanged = { },
        onStartOfEventChanged = { },
        onStartOfMeetingChanged = { },
        onAddressChanged = { },
        onDescriptionChanged = { }
    )
}

@Composable
@Preview(showBackground = true)
private fun EventEditContentPreview2() {
    EventEditContent(
        eventUiState = eventUiStateExample2(),
        onEventTitleChanged = { },
        onEventTypeChanged = { },
        onStartOfEventChanged = { },
        onStartOfMeetingChanged = { },
        onAddressChanged = { },
        onDescriptionChanged = { }
    )
}

@Composable
@Preview(showBackground = true)
private fun EventEditContentPreview3() {
    EventEditContent(
        eventUiState = eventUiStateExample3(),
        onEventTitleChanged = { },
        onEventTypeChanged = { },
        onStartOfEventChanged = { },
        onStartOfMeetingChanged = { },
        onAddressChanged = { },
        onDescriptionChanged = { }
    )
}
package de.vexxes.penaltycatalog.presentation.screen.penaltyReceived

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
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
import com.maxkeppeler.sheets.calendar.CalendarDialog
import com.maxkeppeler.sheets.calendar.models.CalendarConfig
import com.maxkeppeler.sheets.calendar.models.CalendarSelection
import com.maxkeppeler.sheets.calendar.models.CalendarStyle
import de.vexxes.penaltycatalog.R
import de.vexxes.penaltycatalog.component.InputOutlinedField
import de.vexxes.penaltycatalog.domain.model.PenaltyType
import de.vexxes.penaltycatalog.domain.model.Player
import de.vexxes.penaltycatalog.domain.uistate.PenaltyReceivedUiState
import de.vexxes.penaltycatalog.domain.uistate.penaltyReceivedUiStateExample1
import de.vexxes.penaltycatalog.domain.uistate.penaltyReceivedUiStateExample2
import de.vexxes.penaltycatalog.domain.uistate.penaltyReceivedUiStateExample3
import de.vexxes.penaltycatalog.ui.theme.Typography
import kotlinx.datetime.LocalDate

@Composable
fun PenaltyReceivedEditContent(
    penaltyReceivedUiState: PenaltyReceivedUiState,
    penaltyList: List<PenaltyType>,
    playerList: List<Player>,
    onPenaltyIdChanged: (String) -> Unit,
    onPlayerIdChanged: (String) -> Unit,
    onTimeOfPenaltyChanged: (LocalDate) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {
        PenaltyReceivedDatePicker(
            timeOfPenalty = penaltyReceivedUiState.timeOfPenalty,
            onTimeOfPenaltyChanged = onTimeOfPenaltyChanged
        )

        PenaltyReceivedPlayerExposedMenu(
            text = penaltyReceivedUiState.playerName,
            error = penaltyReceivedUiState.playerIdError,
            playerList = playerList,
            onPlayerIdChanged = onPlayerIdChanged
        )


        PenaltyReceivedPenaltyExposedMenu(
            text = penaltyReceivedUiState.penaltyName,
            error = penaltyReceivedUiState.penaltyIdError,
            penaltyList = penaltyList,
            onPenaltyIdChanged = onPenaltyIdChanged
        )
        /*
                if (penaltyHistoryUiState.penaltyName.isNotEmpty()) {
                    PenaltyHistoryAmount(
                        value = penaltyHistoryUiState.penaltyValue,
                        isBeer = penaltyHistoryUiState.penaltyIsBeer
                    )
                }
         */
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun PenaltyReceivedDatePicker(
    timeOfPenalty: LocalDate,
    onTimeOfPenaltyChanged: (LocalDate) -> Unit
) {
    val calendarSheetState = rememberSheetState()

    CalendarDialog(
        state = calendarSheetState,
        config = CalendarConfig(
            yearSelection = true,
            monthSelection = true,
            style = CalendarStyle.MONTH
        ),
        selection = CalendarSelection.Date { selectedDate ->
            onTimeOfPenaltyChanged(LocalDate.parse(selectedDate.toString()))
        }
    )

    InputOutlinedField(
        modifier = Modifier.clickable { calendarSheetState.show() },
        enabled = false,
        readOnly = true,
        text = timeOfPenalty.toString(),
        onTextChanged = { },
        label = stringResource(id = R.string.TimeOfPenalty),
        trailingIcon = {
            IconButton(
                onClick = { calendarSheetState.show() }) {
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun PenaltyReceivedPlayerExposedMenu(
    text: String,
    error: Boolean,
    playerList: List<Player>,
    onPlayerIdChanged: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    val angle: Float by animateFloatAsState(
        targetValue = if (expanded) 180f else 0f
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
private fun PenaltyReceivedPenaltyExposedMenu(
    text: String,
    error: Boolean,
    penaltyList: List<PenaltyType>,
    onPenaltyIdChanged: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    val angle: Float by animateFloatAsState(
        targetValue = if (expanded) 180f else 0f
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
            label = stringResource(id = R.string.Penalty),
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
            penaltyList.forEach { penalty ->
                DropdownMenuItem(
                    onClick = {
                        expanded = false
                        onPenaltyIdChanged(penalty.id)
                    },
                    text = {
                        Text(
                            text = penalty.name,
                            style = Typography.bodyLarge
                        )
                    }
                )
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
private fun PenaltyReceivedEditContentPreview1() {
    PenaltyReceivedEditContent(
        penaltyReceivedUiState = penaltyReceivedUiStateExample1(),
        penaltyList = emptyList(),
        playerList = emptyList(),
        onPenaltyIdChanged = { },
        onPlayerIdChanged = { },
        onTimeOfPenaltyChanged = { }
    )
}

@Composable
@Preview(showBackground = true)
private fun PenaltyReceivedEditContentPreview2() {
    PenaltyReceivedEditContent(
        penaltyReceivedUiState = penaltyReceivedUiStateExample2(),
        penaltyList = emptyList(),
        playerList = emptyList(),
        onPenaltyIdChanged = { },
        onPlayerIdChanged = { },
        onTimeOfPenaltyChanged = { }
    )
}

@Composable
@Preview(showBackground = true)
private fun PenaltyReceivedEditContentPreview3() {
    PenaltyReceivedEditContent(
        penaltyReceivedUiState = penaltyReceivedUiStateExample3(),
        penaltyList = emptyList(),
        playerList = emptyList(),
        onPenaltyIdChanged = { },
        onPlayerIdChanged = { },
        onTimeOfPenaltyChanged = { }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview(showBackground = true)
private fun CalendarPreview() {
    val calendarSheetState = rememberSheetState()
    calendarSheetState.show()

    CalendarDialog(
        state = calendarSheetState,
        config = CalendarConfig(
            yearSelection = true,
            monthSelection = true,
            style = CalendarStyle.MONTH
        ),
        selection = CalendarSelection.Date { },
    )
}
package de.vexxes.penaltycatalog.presentation.screen.history

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import de.vexxes.penaltycatalog.R
import de.vexxes.penaltycatalog.component.InputOutlinedField
import de.vexxes.penaltycatalog.domain.model.Penalty
import de.vexxes.penaltycatalog.domain.model.Player
import de.vexxes.penaltycatalog.domain.uistate.PenaltyHistoryUiState
import de.vexxes.penaltycatalog.ui.theme.Typography
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

@Composable
fun PenaltyHistoryEditContent(
    penaltyHistoryUiState: PenaltyHistoryUiState,
    penaltyList: List<Penalty>,
    playerList: List<Player>,
    onPenaltyChanged: (String) -> Unit,
    onPlayerChanged: (String) -> Unit,
    onTimeOfPenaltyChanged: (Instant) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        PenaltyHistoryPlayerExposedMenu(
            text = penaltyHistoryUiState.playerName,
            playerList = playerList,
            onPlayerChanged = onPlayerChanged
        )

        PenaltyHistoryPenaltyExposedMenu(
            text = penaltyHistoryUiState.penaltyName,
            penaltyList = penaltyList,
            onPenaltyChanged = onPenaltyChanged
        )

        PenaltyHistoryDatePicker(
            timeOfPenalty = penaltyHistoryUiState.timeOfPenalty,
            onTimeOfPenaltyChanged = onTimeOfPenaltyChanged
        )

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun PenaltyHistoryPlayerExposedMenu(
    text: String,
    playerList: List<Player>,
    onPlayerChanged: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    val angle: Float by animateFloatAsState(
        targetValue = if (expanded) 180f else 0f
    )

    ExposedDropdownMenuBox(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        expanded = expanded,
        onExpandedChange = { expanded = !expanded }
    ) {
        InputOutlinedField(
            modifier = Modifier
                .menuAnchor(),
            readOnly = true,
            text = text,
            onTextChanged = { },
            label = stringResource(id = R.string.Player),
            trailingIcon = {
                IconButton(
                    modifier = Modifier
                        .rotate(angle),
                    onClick = { }) {
                    Icon(
                        imageVector = Icons.Default.ArrowDropDown,
                        contentDescription = ""
                    )
                }
            }
        )

        /*TODO Why does the width of dropdownmenu item not match to the parent container*/
        ExposedDropdownMenu(
            modifier = Modifier
                .padding(8.dp),
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            playerList.forEach { player ->
                DropdownMenuItem(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {
                        expanded = !expanded
                        onPlayerChanged("${player.lastName}, ${player.firstName}")
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
private fun PenaltyHistoryPenaltyExposedMenu(
    text: String,
    penaltyList: List<Penalty>,
    onPenaltyChanged: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    val angle: Float by animateFloatAsState(
        targetValue = if (expanded) 180f else 0f
    )

    ExposedDropdownMenuBox(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        expanded = expanded,
        onExpandedChange = { expanded = !expanded }
    ) {
        InputOutlinedField(
            modifier = Modifier
                .menuAnchor(),
            readOnly = true,
            text = text,
            onTextChanged = { },
            label = stringResource(id = R.string.Penalty),
            trailingIcon = {
                IconButton(
                    modifier = Modifier
                        .rotate(angle),
                    onClick = { }) {
                    Icon(
                        imageVector = Icons.Default.ArrowDropDown,
                        contentDescription = ""
                    )
                }
            }
        )

        /*TODO Why does the width of dropdownmenu item not match to the parent container*/
        ExposedDropdownMenu(
            modifier = Modifier
                .padding(8.dp),
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            penaltyList.forEach { penalty ->
                DropdownMenuItem(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {
                        expanded = !expanded
                        onPenaltyChanged(penalty.name)
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
private fun PenaltyHistoryDatePicker(
    timeOfPenalty: Instant,
    onTimeOfPenaltyChanged: (Instant) -> Unit
) {
    /*TODO Implement date picker*/

    InputOutlinedField(
        modifier = Modifier
            .padding(8.dp),
        readOnly = true,
        text = timeOfPenalty.toLocalDateTime(TimeZone.UTC).date.toString(),
        onTextChanged = { },
        label = stringResource(id = R.string.DateOfPenalty),
        trailingIcon = {
            IconButton(
                onClick = { }) {
                Icon(
                    imageVector = Icons.Default.DateRange,
                    contentDescription = ""
                )
            }
        }
    )
}

/*
val penaltyValue: String = "",
val penaltyIsBeer: Boolean = false,
val timeOfPenalty: Instant = Clock.System.now(),
val penaltyPaid: Boolean = false
*/


@Composable
@Preview(showBackground = true)
private fun PenaltyHistoryEditContentPreview() {
    val penaltyHistoryUiState = PenaltyHistoryUiState()

    PenaltyHistoryEditContent(
        penaltyHistoryUiState = penaltyHistoryUiState,
        penaltyList = emptyList(),
        playerList = emptyList(),
        onPenaltyChanged = { },
        onPlayerChanged = { },
        onTimeOfPenaltyChanged = { }
    )
}
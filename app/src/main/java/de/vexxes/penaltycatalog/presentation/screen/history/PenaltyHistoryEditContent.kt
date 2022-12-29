package de.vexxes.penaltycatalog.presentation.screen.history

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.mabn.calendarlibrary.ExpandableCalendar
import com.mabn.calendarlibrary.core.CalendarTheme
import de.vexxes.penaltycatalog.R
import de.vexxes.penaltycatalog.component.InputOutlinedField
import de.vexxes.penaltycatalog.domain.model.Penalty
import de.vexxes.penaltycatalog.domain.model.Player
import de.vexxes.penaltycatalog.domain.uistate.PenaltyHistoryUiState
import de.vexxes.penaltycatalog.domain.uistate.penaltyHistoryUiStateExample1
import de.vexxes.penaltycatalog.domain.uistate.penaltyHistoryUiStateExample2
import de.vexxes.penaltycatalog.domain.uistate.penaltyHistoryUiStateExample3
import de.vexxes.penaltycatalog.domain.visualTransformation.CurrencyAmountInputVisualTransformation
import de.vexxes.penaltycatalog.ui.theme.Typography
import java.time.LocalDate

@Composable
fun PenaltyHistoryEditContent(
    penaltyHistoryUiState: PenaltyHistoryUiState,
    penaltyList: List<Penalty>,
    playerList: List<Player>,
    onPenaltyChanged: (String, String) -> Unit,
    onPlayerChanged: (String) -> Unit,
    onTimeOfPenaltyChanged: (LocalDate) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {
        PenaltyHistoryDatePicker(
            timeOfPenalty = penaltyHistoryUiState.timeOfPenalty,
            onTimeOfPenaltyChanged = onTimeOfPenaltyChanged
        )

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

        if (penaltyHistoryUiState.penaltyName.isNotEmpty()) {
            PenaltyHistoryAmount(
                value = penaltyHistoryUiState.penaltyValue,
                isBeer = penaltyHistoryUiState.penaltyIsBeer
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun PenaltyHistoryDatePicker(
    timeOfPenalty: LocalDate,
    onTimeOfPenaltyChanged: (LocalDate) -> Unit
) {
    var showDialog by remember { mutableStateOf(false) }

    if (showDialog) {
        Dialog(
            onDismissRequest = { showDialog = false }
        ) {
            ExpandableCalendar(
                theme = CalendarTheme(
                    backgroundColor = MaterialTheme.colorScheme.surface,
                    headerBackgroundColor = MaterialTheme.colorScheme.surface,
                    dayBackgroundColor = MaterialTheme.colorScheme.surface,
                    selectedDayBackgroundColor = MaterialTheme.colorScheme.primary,
                    dayValueTextColor = MaterialTheme.colorScheme.onSurface,
                    selectedDayValueTextColor = MaterialTheme.colorScheme.onPrimary,
                    headerTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    weekDaysTextColor = MaterialTheme.colorScheme.onSurface,
                    dayShape = MaterialTheme.shapes.extraLarge
                ),
                onDayClick = {
                    onTimeOfPenaltyChanged(it)
                    showDialog = false
                }
            )
        }
    }

    InputOutlinedField(
        modifier = Modifier.clickable { showDialog = true },
        enabled = false,
        readOnly = true,
        text = timeOfPenalty.toString(),
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
            playerList.sortedWith(compareBy { it.lastName }).forEach { player ->
                DropdownMenuItem(
                    onClick = {
                        expanded = false
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
    onPenaltyChanged: (String, String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    val angle: Float by animateFloatAsState(
        targetValue = if (expanded) 180f else 0f
    )

    ExposedDropdownMenuBox(
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
            penaltyList.forEach { penalty ->
                DropdownMenuItem(
                    onClick = {
                        expanded = false
                        onPenaltyChanged(penalty._id, penalty.name)
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun PenaltyHistoryAmount(
    value: String,
    isBeer: Boolean
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            modifier = Modifier
                .padding(top = 16.dp),
            text = stringResource(id = R.string.Amount),
            style = Typography.labelLarge.copy(fontWeight = FontWeight.Bold),
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            val visualTransformation = if (isBeer) VisualTransformation.None else CurrencyAmountInputVisualTransformation(
                fixedCursorAtTheEnd = true
            )

            Text(
                text = if (isBeer) stringResource(id = R.string.Box) else android.icu.text.NumberFormat.getCurrencyInstance().currency.symbol,
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
}

@Composable
@Preview(showBackground = true)
private fun PenaltyHistoryEditContentPreview1() {
    PenaltyHistoryEditContent(
        penaltyHistoryUiState = penaltyHistoryUiStateExample1(),
        penaltyList = emptyList(),
        playerList = emptyList(),
        onPenaltyChanged = { _, _ -> },
        onPlayerChanged = { },
        onTimeOfPenaltyChanged = { }
    )
}

@Composable
@Preview(showBackground = true)
private fun PenaltyHistoryEditContentPreview2() {
    PenaltyHistoryEditContent(
        penaltyHistoryUiState = penaltyHistoryUiStateExample2(),
        penaltyList = emptyList(),
        playerList = emptyList(),
        onPenaltyChanged = { _, _ -> },
        onPlayerChanged = { },
        onTimeOfPenaltyChanged = { }
    )
}

@Composable
@Preview(showBackground = true)
private fun PenaltyHistoryEditContentPreview3() {
    PenaltyHistoryEditContent(
        penaltyHistoryUiState = penaltyHistoryUiStateExample3(),
        penaltyList = emptyList(),
        playerList = emptyList(),
        onPenaltyChanged = { _, _ -> },
        onPlayerChanged = { },
        onTimeOfPenaltyChanged = { }
    )
}
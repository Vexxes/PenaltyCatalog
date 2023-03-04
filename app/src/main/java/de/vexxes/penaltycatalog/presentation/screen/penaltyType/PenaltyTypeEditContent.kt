package de.vexxes.penaltycatalog.presentation.screen.penaltyType

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.text.isDigitsOnly
import de.vexxes.penaltycatalog.R
import de.vexxes.penaltycatalog.component.InputOutlinedField
import de.vexxes.penaltycatalog.domain.enums.BeerMoneyType
import de.vexxes.penaltycatalog.domain.uistate.PenaltyTypeUiState
import de.vexxes.penaltycatalog.domain.uistate.penaltyTypeUiStateExample1
import de.vexxes.penaltycatalog.domain.uistate.penaltyTypeUiStateExample2
import de.vexxes.penaltycatalog.domain.visualTransformation.NumberCommaTransformation
import de.vexxes.penaltycatalog.ui.theme.Typography

@Composable
fun PenaltyTypeEditContent(
    penaltyTypeUiState: PenaltyTypeUiState,
    onPenaltyNameChanged: (String) -> Unit,
    onPenaltyDescriptionChanged: (String) -> Unit,
    onPenaltyAmountChanged: (String) -> Unit,
    onPenaltyTypeChanged: (BeerMoneyType) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
            .verticalScroll(rememberScrollState())
    ) {
        PenaltyTypeName(
            text = penaltyTypeUiState.name,
            error = penaltyTypeUiState.nameError,
            onTextChanged = onPenaltyNameChanged
        )

        PenaltyTypeAmount(
            value = penaltyTypeUiState.value,
            error = penaltyTypeUiState.valueError,
            onTextChanged = onPenaltyAmountChanged
        )

        PenaltyType(
            isBeer = penaltyTypeUiState.isBeer,
            onPenaltyTypeChanged = onPenaltyTypeChanged
        )

        PenaltyTypeDescription(
            text = penaltyTypeUiState.description,
            onTextChanged = onPenaltyDescriptionChanged
        )
    }
}

@Composable
private fun PenaltyTypeName(
    text: String,
    error: Boolean,
    onTextChanged: (String) -> Unit
) {
    InputOutlinedField(
        text = text,
        onTextChanged = onTextChanged,
        label = stringResource(id = R.string.PenaltyName),
        required = true,
        isError = error
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun PenaltyType(
    isBeer: Boolean,
    onPenaltyTypeChanged: (BeerMoneyType) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    val angle: Float by animateFloatAsState(
        targetValue = if (expanded) 180f else 0f
    )

    ExposedDropdownMenuBox(
        modifier = Modifier
            .fillMaxWidth(),
        expanded = expanded,
        onExpandedChange = { expanded = !expanded }
    ) {
        InputOutlinedField(
            modifier = Modifier
                .menuAnchor(),
            readOnly = true,
            text = if(isBeer) stringResource(id = R.string.Beer) else stringResource(id = R.string.Money),
            onTextChanged = { },
            label = stringResource(id = R.string.BeerOrMoney),
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
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            BeerMoneyType.values().forEach { penaltyType ->
                DropdownMenuItem(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {
                        expanded = !expanded
                        onPenaltyTypeChanged(penaltyType) },
                    text = {
                        Text(
                            text = penaltyType.name,
                            style = Typography.bodyLarge
                        )
                    }
                )
            }
        }
    }
}

@Composable
private fun PenaltyTypeDescription(
    text: String,
    onTextChanged: (String) -> Unit
) {
    InputOutlinedField(
        modifier = Modifier
            .fillMaxSize(),
        text = text,
        onTextChanged = onTextChanged,
        label = stringResource(id = R.string.PenaltyDescription)
    )
}

@Composable
private fun PenaltyTypeAmount(
    value: String,
    error: Boolean,
    onTextChanged: (String) -> Unit
) {
    var text by remember { mutableStateOf(value) }

    InputOutlinedField(
        text = text,
        onTextChanged = { newText: String ->
            if (newText.length <= Long.MAX_VALUE.toString().length && newText.isDigitsOnly()) {
                text = newText
                println(newText)
                onTextChanged(newText)
            }
        },
        isError = error,
        label = stringResource(id = R.string.Amount),
        required = true,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        visualTransformation = NumberCommaTransformation()
    )
}

@Preview(showBackground = true)
@Composable
private fun PenaltyTypeEditContentPreview1() {
    PenaltyTypeEditContent(
        penaltyTypeUiState = penaltyTypeUiStateExample1(),
        onPenaltyNameChanged = { },
        onPenaltyDescriptionChanged = { },
        onPenaltyAmountChanged = { },
        onPenaltyTypeChanged = { }
    )
}

@Preview(showBackground = true)
@Composable
private fun PenaltyTypeEditContentPreview2() {
    PenaltyTypeEditContent(
        penaltyTypeUiState = penaltyTypeUiStateExample2(),
        onPenaltyNameChanged = { },
        onPenaltyDescriptionChanged = { },
        onPenaltyAmountChanged = { },
        onPenaltyTypeChanged = { }
    )
}
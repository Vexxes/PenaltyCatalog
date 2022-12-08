package de.vexxes.penaltycatalog.presentation.screen.penalty

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import de.vexxes.penaltycatalog.R
import de.vexxes.penaltycatalog.component.InputOutlinedField
import de.vexxes.penaltycatalog.domain.model.PenaltyCategory
import de.vexxes.penaltycatalog.domain.model.PenaltyType
import de.vexxes.penaltycatalog.ui.theme.Typography

@Composable
fun PenaltyEditContent(
    categoryList: List<PenaltyCategory>,
    penaltyName: String,
    penaltyDescription: String,
    isBeer: Boolean,
    penaltyAmount: String,
    onCategoryChanged: (String) -> Unit,
    onPenaltyNameChanged: (String) -> Unit,
    onPenaltyDescriptionChanged: (String) -> Unit,
    onPenaltyAmountChanged: (String) -> Unit,
    onPenaltyTypeChanged: (PenaltyType) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        CategoryExposedMenu(
            categoryList = categoryList,
            onCategoryChanged = onCategoryChanged
        )

        PenaltyName(
            text = penaltyName,
            onTextChanged = onPenaltyNameChanged
        )

        PenaltyType(
            isBeer = isBeer,
            onPenaltyTypeChanged = onPenaltyTypeChanged
        )

        PenaltyAmount(
            text = penaltyAmount,
            onTextChanged = onPenaltyAmountChanged
        )

        PenaltyDescription(
            text = penaltyDescription,
            onTextChanged = onPenaltyDescriptionChanged
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CategoryExposedMenu(
    categoryList: List<PenaltyCategory>,
    onCategoryChanged: (String) -> Unit
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
            text = categoryList.first().name, /*TODO Show correct name of category*/
            onTextChanged = { },
            label = stringResource(id = R.string.Category),
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
            categoryList.forEach { penaltyCategory ->
                DropdownMenuItem(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {
                        expanded = !expanded
                        onCategoryChanged(penaltyCategory.name)
                    },
                    text = {
                        Text(
                            text = penaltyCategory.name,
                            style = Typography.bodyLarge
                        )
                    }
                )
            }
        }
    }
}

@Composable
private fun PenaltyName(
    text: String,
    onTextChanged: (String) -> Unit
) {
    InputOutlinedField(
        modifier = Modifier
            .padding(8.dp),
        text = text,
        onTextChanged = onTextChanged,
        label = stringResource(id = R.string.PenaltyName)
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun PenaltyType(
    isBeer: Boolean,
    onPenaltyTypeChanged: (PenaltyType) -> Unit
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
            modifier = Modifier
                .padding(8.dp),
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            PenaltyType.values().forEach { penaltyType ->
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
private fun PenaltyDescription(
    text: String,
    onTextChanged: (String) -> Unit
) {
    InputOutlinedField(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp),
        text = text,
        onTextChanged = onTextChanged,
        label = stringResource(id = R.string.PenaltyDescription)
    )
}

@Composable
private fun PenaltyAmount(
    text: String,
    onTextChanged: (String) -> Unit
) {
    InputOutlinedField(
        modifier = Modifier
            .padding(8.dp),
        text = text,
        onTextChanged = onTextChanged,
        label = stringResource(id = R.string.Amount)
    )
}

@Preview(showBackground = true)
@Composable
private fun PenaltyEditContentPreview() {
    val categoryList = listOf(
        PenaltyCategory(name = "Monatsbeitrag"),
        PenaltyCategory(name = "Verspätungen / Abwesenheiten"),
        PenaltyCategory(name = "Grob mannschaftsschädigendes Verhalten"),
        PenaltyCategory(name = "Sonstiges")
    )

    PenaltyEditContent(
        categoryList = categoryList,
        penaltyName = "Getränke zur Besprechung",
        penaltyDescription = "Mitzubringen in alphabetischer Reihenfolge nach dem Freitagstraining",
        isBeer = true,
        penaltyAmount = "1",
        onCategoryChanged = { },
        onPenaltyNameChanged = { },
        onPenaltyDescriptionChanged = { },
        onPenaltyAmountChanged = { },
        onPenaltyTypeChanged = { }
    )
}
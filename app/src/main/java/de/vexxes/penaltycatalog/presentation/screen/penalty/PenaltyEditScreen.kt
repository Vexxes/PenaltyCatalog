package de.vexxes.penaltycatalog.presentation.screen.penalty

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import de.vexxes.penaltycatalog.component.BackSaveTopBar
import de.vexxes.penaltycatalog.domain.model.PenaltyCategory
import de.vexxes.penaltycatalog.domain.model.PenaltyType
import de.vexxes.penaltycatalog.domain.uievent.PenaltyUiEvent
import de.vexxes.penaltycatalog.domain.uistate.PenaltyUiState
import de.vexxes.penaltycatalog.viewmodels.PenaltyViewModel

@Composable
fun PenaltyEditScreen(
    penaltyViewModel: PenaltyViewModel,
    onBackClicked: () -> Unit,
    onSaveClicked: (String?) -> Unit
) {
    val categoryList by penaltyViewModel.categories
    val penaltyUiState by penaltyViewModel.penaltyUiState

    BackHandler {
        onBackClicked()
    }

    /*TODO Replace with correct values*/
    PenaltyEditScaffold(
        penaltyUiState = penaltyUiState,
        categoryList = categoryList,
        onCategoryChanged = { penaltyViewModel.onPenaltyUiEvent(PenaltyUiEvent.CategoryNameChanged(it)) },
        onPenaltyNameChanged = { penaltyViewModel.onPenaltyUiEvent(PenaltyUiEvent.NameChanged(it)) },
        onPenaltyDescriptionChanged = { penaltyViewModel.onPenaltyUiEvent(PenaltyUiEvent.DescriptionChanged(it)) },
        onPenaltyAmountChanged = {
                penaltyViewModel.onPenaltyUiEvent(PenaltyUiEvent.ValueChanged(it))
        },
        onPenaltyTypeChanged = { penaltyType ->
            when(penaltyType) {
                PenaltyType.BEER ->
                    penaltyViewModel.onPenaltyUiEvent(PenaltyUiEvent.IsBeerChanged(true))
                PenaltyType.MONEY ->
                    penaltyViewModel.onPenaltyUiEvent(PenaltyUiEvent.IsBeerChanged(false))
            }
        },
        onBackClicked = onBackClicked,
        onSaveClicked = onSaveClicked
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun PenaltyEditScaffold(
    penaltyUiState: PenaltyUiState,
    categoryList: List<PenaltyCategory>,
    onCategoryChanged: (String) -> Unit,
    onPenaltyNameChanged: (String) -> Unit,
    onPenaltyDescriptionChanged: (String) -> Unit,
    onPenaltyAmountChanged: (String) -> Unit,
    onPenaltyTypeChanged: (PenaltyType) -> Unit,
    onBackClicked: () -> Unit,
    onSaveClicked: (String?) -> Unit
) {
    Scaffold(
        topBar = {
            BackSaveTopBar(
                onBackClicked = onBackClicked,
                onSaveClicked = { onSaveClicked(penaltyUiState.id) }
            )
        },

        content = { paddingValues ->
            Box(
                modifier = Modifier.padding(paddingValues)
            ) {
                PenaltyEditContent(
                    penaltyUiState = penaltyUiState,
                    categoryList = categoryList,
                    onCategoryChanged = onCategoryChanged,
                    onPenaltyNameChanged = onPenaltyNameChanged,
                    onPenaltyDescriptionChanged = onPenaltyDescriptionChanged,
                    onPenaltyAmountChanged = onPenaltyAmountChanged,
                    onPenaltyTypeChanged = onPenaltyTypeChanged
                )
            }
        },
    )
}

@Preview(showBackground = true)
@Composable
private fun PenaltyEditScreenPreview() {
    val categoryList = listOf(
        PenaltyCategory(name = "Monatsbeitrag"),
        PenaltyCategory(name = "Verspätungen / Abwesenheiten"),
        PenaltyCategory(name = "Grob mannschaftsschädigendes Verhalten"),
        PenaltyCategory(name = "Sonstiges")
    )

    val penaltyUiState = PenaltyUiState(
        name = "Getränke zur Besprechung",
        description = "Mitzubringen in alphabetischer Reihenfolge nach dem Freitagstraining",
        isBeer = true,
        value = "1"
    )

    PenaltyEditScaffold(
        penaltyUiState = penaltyUiState,
        categoryList = categoryList,
        onCategoryChanged = { },
        onPenaltyNameChanged = { },
        onPenaltyDescriptionChanged = { },
        onPenaltyAmountChanged = { },
        onPenaltyTypeChanged = { },
        onBackClicked = { },
        onSaveClicked = { }
    )
}
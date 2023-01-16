package de.vexxes.penaltycatalog.presentation.screen.penaltyType

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
import de.vexxes.penaltycatalog.domain.enums.BeerMoneyType
import de.vexxes.penaltycatalog.domain.uievent.PenaltyTypeUiEvent
import de.vexxes.penaltycatalog.domain.uistate.PenaltyTypeUiState
import de.vexxes.penaltycatalog.domain.uistate.penaltyTypeUiStateExample1
import de.vexxes.penaltycatalog.viewmodels.PenaltyTypeViewModel

@Composable
fun PenaltyTypeEditScreen(
    penaltyTypeViewModel: PenaltyTypeViewModel,
    onBackClicked: () -> Unit,
    onSaveClicked: (String?) -> Unit
) {
    val penaltyUiState by penaltyTypeViewModel.penaltyTypeUiState

    BackHandler {
        onBackClicked()
    }

    PenaltyTypeEditScaffold(
        penaltyTypeUiState = penaltyUiState,
        onPenaltyNameChanged = { penaltyTypeViewModel.onPenaltyUiEvent(PenaltyTypeUiEvent.NameChanged(it)) },
        onPenaltyDescriptionChanged = { penaltyTypeViewModel.onPenaltyUiEvent(PenaltyTypeUiEvent.DescriptionChanged(it)) },
        onPenaltyAmountChanged = {
            penaltyTypeViewModel.onPenaltyUiEvent(PenaltyTypeUiEvent.ValueChanged(it))
        },
        onPenaltyTypeChanged = { penaltyType ->
            when(penaltyType) {
                BeerMoneyType.BEER ->
                    penaltyTypeViewModel.onPenaltyUiEvent(PenaltyTypeUiEvent.IsBeerChanged(true))
                BeerMoneyType.MONEY ->
                    penaltyTypeViewModel.onPenaltyUiEvent(PenaltyTypeUiEvent.IsBeerChanged(false))
            }
        },
        onBackClicked = onBackClicked,
        onSaveClicked = onSaveClicked
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun PenaltyTypeEditScaffold(
    penaltyTypeUiState: PenaltyTypeUiState,
    onPenaltyNameChanged: (String) -> Unit,
    onPenaltyDescriptionChanged: (String) -> Unit,
    onPenaltyAmountChanged: (String) -> Unit,
    onPenaltyTypeChanged: (BeerMoneyType) -> Unit,
    onBackClicked: () -> Unit,
    onSaveClicked: (String?) -> Unit
) {
    Scaffold(
        topBar = {
            BackSaveTopBar(
                onBackClicked = onBackClicked,
                onSaveClicked = { onSaveClicked(penaltyTypeUiState.id) }
            )
        },

        content = { paddingValues ->
            Box(
                modifier = Modifier.padding(paddingValues)
            ) {
                PenaltyTypeEditContent(
                    penaltyTypeUiState = penaltyTypeUiState,
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
private fun PenaltyTypeEditScreenPreview() {
    PenaltyTypeEditScaffold(
        penaltyTypeUiState = penaltyTypeUiStateExample1(),
        onPenaltyNameChanged = { },
        onPenaltyDescriptionChanged = { },
        onPenaltyAmountChanged = { },
        onPenaltyTypeChanged = { },
        onBackClicked = { },
        onSaveClicked = { }
    )
}
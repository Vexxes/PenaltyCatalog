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
import de.vexxes.penaltycatalog.viewmodels.PenaltyViewModel

@Composable
fun PenaltyEditScreen(
    penaltyViewModel: PenaltyViewModel,
    onBackClicked: () -> Unit
) {
    val categoryList by penaltyViewModel.categories
    val id by penaltyViewModel.id
    val penaltyName by penaltyViewModel.penaltyName
    val penaltyDescription by penaltyViewModel.penaltyDescription
    val isBeer by penaltyViewModel.isBeer
    val penaltyAmount by penaltyViewModel.penaltyAmount

    BackHandler {
        onBackClicked()
    }

    /*TODO Replace with correct values*/
    PenaltyEditScaffold(
        id = id,
        categoryList = categoryList,
        penaltyName = penaltyName,
        penaltyDescription = penaltyDescription,
        isBeer = isBeer,
        penaltyAmount = penaltyAmount,
        onCategoryChanged = {
            penaltyViewModel.penaltyCategoryName.value = it
        },
        onPenaltyNameChanged = {
            penaltyViewModel.penaltyName.value = it
        },
        onPenaltyDescriptionChanged = {
            penaltyViewModel.penaltyDescription.value = it
        },
        onPenaltyAmountChanged = {
            penaltyViewModel.penaltyAmount.value = it
        },
        onPenaltyTypeChanged = { penaltyType ->
            when(penaltyType) {
                PenaltyType.BEER ->
                    penaltyViewModel.isBeer.value = true
                PenaltyType.MONEY ->
                    penaltyViewModel.isBeer.value = false
            }
        },
        onBackClicked = onBackClicked,
        onSaveClicked = { penaltyViewModel.updatePenalty() }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun PenaltyEditScaffold(
    id: String,
    categoryList: List<PenaltyCategory>,
    penaltyName: String,
    penaltyDescription: String,
    isBeer: Boolean,
    penaltyAmount: String,
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
                onSaveClicked = { onSaveClicked(id) }
            )
        },

        content = { paddingValues ->
            Box(
                modifier = Modifier.padding(paddingValues)
            ) {
                PenaltyEditContent(
                    categoryList = categoryList,
                    penaltyName = penaltyName,
                    penaltyAmount = penaltyAmount,
                    penaltyDescription = penaltyDescription,
                    isBeer = isBeer,
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

    PenaltyEditScaffold(
        id = "",
        categoryList = categoryList,
        penaltyName = "",
        penaltyDescription = "",
        isBeer = true,
        penaltyAmount = "",
        onCategoryChanged = { },
        onPenaltyNameChanged = { },
        onPenaltyDescriptionChanged = { },
        onPenaltyAmountChanged = { },
        onPenaltyTypeChanged = { },
        onBackClicked = { },
        onSaveClicked = { }
    )
}
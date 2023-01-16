package de.vexxes.penaltycatalog.navigation.composables.penaltyType

import androidx.compose.runtime.LaunchedEffect
import de.vexxes.penaltycatalog.navigation.ScreenNavigation
import de.vexxes.penaltycatalog.util.SearchAppBarState
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import de.vexxes.penaltycatalog.domain.uievent.SearchUiEvent
import de.vexxes.penaltycatalog.presentation.screen.penaltyType.PenaltyTypeListScreen
import de.vexxes.penaltycatalog.viewmodels.PenaltyTypeViewModel

fun NavGraphBuilder.penaltyListComposable(
    penaltyTypeViewModel: PenaltyTypeViewModel,
    navigateToPenaltyDetailScreen: (String) -> Unit,
    navigateToPenaltyEditScreen: (String) -> Unit
) {
    composable(
        route = ScreenNavigation.Penalties.route
    ) {
        LaunchedEffect(true) {
            penaltyTypeViewModel.onSearchUiEvent(SearchUiEvent.SearchAppBarStateChanged(SearchAppBarState.CLOSED))
        }

        PenaltyTypeListScreen(
            penaltyTypeViewModel = penaltyTypeViewModel,
            navigateToPenaltyDetailScreen = { penaltyTypeId ->
                navigateToPenaltyDetailScreen(penaltyTypeId)
            },
            navigateToPenaltyEditScreen = { penaltyTypeId ->
                navigateToPenaltyEditScreen(penaltyTypeId)
            }
        )
    }
}
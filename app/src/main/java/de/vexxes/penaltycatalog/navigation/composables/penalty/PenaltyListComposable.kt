package de.vexxes.penaltycatalog.navigation.composables.penalty

import androidx.compose.runtime.LaunchedEffect
import de.vexxes.penaltycatalog.navigation.ScreenNavigation
import de.vexxes.penaltycatalog.util.SearchAppBarState
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import de.vexxes.penaltycatalog.domain.uievent.SearchUiEvent
import de.vexxes.penaltycatalog.presentation.screen.penalty.PenaltyListScreen
import de.vexxes.penaltycatalog.viewmodels.PenaltyViewModel

fun NavGraphBuilder.penaltyListComposable(
    penaltyViewModel: PenaltyViewModel,
    navigateToPenaltyDetailScreen: (String) -> Unit,
    navigateToPenaltyEditScreen: (String) -> Unit
) {
    composable(
        route = ScreenNavigation.Penalties.route
    ) {
        LaunchedEffect(true) {
            penaltyViewModel.onSearchUiEvent(SearchUiEvent.SearchAppBarStateChanged(SearchAppBarState.CLOSED))
        }

        PenaltyListScreen(
            penaltyViewModel = penaltyViewModel,
            navigateToPenaltyDetailScreen = { penaltyId ->
                navigateToPenaltyDetailScreen(penaltyId)
            },
            navigateToPenaltyEditScreen = { penaltyId ->
                navigateToPenaltyEditScreen(penaltyId)
            }
        )
    }
}
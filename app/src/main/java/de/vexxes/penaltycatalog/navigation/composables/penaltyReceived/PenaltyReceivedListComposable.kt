package de.vexxes.penaltycatalog.navigation.composables.penaltyReceived

import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import de.vexxes.penaltycatalog.domain.uievent.SearchUiEvent
import de.vexxes.penaltycatalog.navigation.ScreenNavigation
import de.vexxes.penaltycatalog.presentation.screen.penaltyReceived.PenaltyReceivedListScreen
import de.vexxes.penaltycatalog.util.SearchAppBarState
import de.vexxes.penaltycatalog.viewmodels.PenaltyReceivedViewModel

fun NavGraphBuilder.penaltyReceivedListComposable(
    penaltyReceivedViewModel: PenaltyReceivedViewModel,
    navigateToPenaltyReceivedDetailScreen: (String) -> Unit,
    navigateToPenaltyReceivedEditScreen: (String) -> Unit,
) {
    composable(
        route = ScreenNavigation.PenaltyReceived.route
    ) {
        LaunchedEffect(true) {
            penaltyReceivedViewModel.onSearchUiEvent(SearchUiEvent.SearchAppBarStateChanged(SearchAppBarState.CLOSED))
        }

        penaltyReceivedViewModel.updateLists()

        PenaltyReceivedListScreen(
            penaltyReceivedViewModel = penaltyReceivedViewModel,
            navigateToPenaltyReceivedDetailScreen = { penaltyHistoryId ->
                navigateToPenaltyReceivedDetailScreen(penaltyHistoryId)
            },
            navigateToPenaltyReceivedEditScreen = { penaltyHistoryId ->
                navigateToPenaltyReceivedEditScreen(penaltyHistoryId)
            }
        )
    }
}
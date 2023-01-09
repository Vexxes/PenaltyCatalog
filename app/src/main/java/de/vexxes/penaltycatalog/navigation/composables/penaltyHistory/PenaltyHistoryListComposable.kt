package de.vexxes.penaltycatalog.navigation.composables.penaltyHistory

import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import de.vexxes.penaltycatalog.domain.uievent.SearchUiEvent
import de.vexxes.penaltycatalog.navigation.ScreenNavigation
import de.vexxes.penaltycatalog.presentation.screen.penaltyHistory.PenaltyHistoryListScreen
import de.vexxes.penaltycatalog.util.SearchAppBarState
import de.vexxes.penaltycatalog.viewmodels.PenaltyHistoryViewModel

fun NavGraphBuilder.penaltyHistoryListComposable(
    penaltyHistoryViewModel: PenaltyHistoryViewModel,
    navigateToPenaltyHistoryDetailScreen: (String) -> Unit,
    navigateToPenaltyHistoryEditScreen: (String) -> Unit,
) {
    composable(
        route = ScreenNavigation.PenaltyHistory.route
    ) {
        LaunchedEffect(true) {
            penaltyHistoryViewModel.onSearchUiEvent(SearchUiEvent.SearchAppBarStateChanged(SearchAppBarState.CLOSED))
        }

        PenaltyHistoryListScreen(
            penaltyHistoryViewModel = penaltyHistoryViewModel,
            navigateToPenaltyHistoryDetailScreen = { penaltyHistoryId ->
                navigateToPenaltyHistoryDetailScreen(penaltyHistoryId)
            },
            navigateToPenaltyHistoryEditScreen = { penaltyHistoryId ->
                navigateToPenaltyHistoryEditScreen(penaltyHistoryId)
            }
        )
    }
}
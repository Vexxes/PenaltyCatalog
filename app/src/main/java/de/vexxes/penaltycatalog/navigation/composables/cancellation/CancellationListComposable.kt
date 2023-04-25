package de.vexxes.penaltycatalog.navigation.composables.cancellation

import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import de.vexxes.penaltycatalog.domain.enums.SearchAppBarState
import de.vexxes.penaltycatalog.domain.uievent.SearchUiEvent
import de.vexxes.penaltycatalog.navigation.ScreenNavigation
import de.vexxes.penaltycatalog.presentation.screen.cancel.CancellationListScreen
import de.vexxes.penaltycatalog.viewmodels.CancellationViewModel

fun NavGraphBuilder.cancellationListComposable(
    cancellationViewModel: CancellationViewModel,
    navigateToCancellationDetailScreen: (String) -> Unit,
    navigateToCancellationEditScreen: (String) -> Unit
) {
    composable(
        route = ScreenNavigation.Cancellations.route
    ) {
        LaunchedEffect(true) {
            cancellationViewModel.onSearchUiEvent(SearchUiEvent.SearchAppBarStateChanged(SearchAppBarState.CLOSED))
        }

        cancellationViewModel.getAllCancellations()

        CancellationListScreen(
            cancellationViewModel = cancellationViewModel,
            navigateToCancellationDetailScreen = { cancellationId ->
                navigateToCancellationDetailScreen(cancellationId)
            },
            navigateToCancellationEditScreen = { cancellationId ->
                navigateToCancellationEditScreen(cancellationId)
            }
        )
    }
}
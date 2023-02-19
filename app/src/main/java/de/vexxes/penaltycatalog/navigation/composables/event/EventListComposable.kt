package de.vexxes.penaltycatalog.navigation.composables.event

import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import de.vexxes.penaltycatalog.domain.enums.SearchAppBarState
import de.vexxes.penaltycatalog.domain.uievent.SearchUiEvent
import de.vexxes.penaltycatalog.navigation.ScreenNavigation
import de.vexxes.penaltycatalog.presentation.screen.events.EventListScreen
import de.vexxes.penaltycatalog.viewmodels.EventViewModel

fun NavGraphBuilder.eventListComposable(
    eventViewModel: EventViewModel,
    navigateToEventDetailScreen: (String) -> Unit,
    navigateToEventEditScreen: (String) -> Unit
) {
    composable(
        route = ScreenNavigation.Events.route
    ) {
        LaunchedEffect(true) {
            eventViewModel.onSearchUiEvent(SearchUiEvent.SearchAppBarStateChanged(SearchAppBarState.CLOSED))
        }

        eventViewModel.getAllEvents()

        EventListScreen(
            eventViewModel = eventViewModel,
            navigateToEventDetailScreen = { eventId ->
                navigateToEventDetailScreen(eventId)
            },
            navigateToEventEditScreen = { eventId ->
                navigateToEventEditScreen(eventId)
            }
        )
    }
}
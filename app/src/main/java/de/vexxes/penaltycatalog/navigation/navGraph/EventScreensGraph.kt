package de.vexxes.penaltycatalog.navigation.navGraph

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import de.vexxes.penaltycatalog.navigation.Graph
import de.vexxes.penaltycatalog.navigation.Screen
import de.vexxes.penaltycatalog.navigation.ScreenNavigation
import de.vexxes.penaltycatalog.navigation.composables.event.eventDetailComposable
import de.vexxes.penaltycatalog.navigation.composables.event.eventListComposable
import de.vexxes.penaltycatalog.viewmodels.EventViewModel

fun NavGraphBuilder.eventScreensGraph(
    navController: NavController,
    eventViewModel: EventViewModel
) {
    navigation(
        startDestination = ScreenNavigation.Events.route,
        route = Graph.EVENTS
    ) {
        eventListComposable(
            eventViewModel = eventViewModel,
            navigateToEventDetailScreen = { eventId ->
                navController.navigate(route = Screen.EventDetail.route + "/$eventId")
            },
            navigateToEventEditScreen = { eventId ->
                navController.navigate(route = Screen.EventEdit.route + "/$eventId")
            }
        )

        eventDetailComposable(
            eventViewModel = eventViewModel,
            navigateToEventList = {
                navController.navigate(ScreenNavigation.Events.route)
            },
            onEditClicked = { eventId ->
                navController.navigate(Screen.EventEdit.route + "/$eventId")
            }
        )

        /* TODO Call all composable screens */

    }
}
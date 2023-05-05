package de.vexxes.penaltycatalog.navigation.navGraph

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import de.vexxes.penaltycatalog.navigation.Graph
import de.vexxes.penaltycatalog.navigation.Screen
import de.vexxes.penaltycatalog.navigation.composables.event.eventDetailComposable
import de.vexxes.penaltycatalog.navigation.composables.event.eventEditComposable
import de.vexxes.penaltycatalog.navigation.composables.event.eventListComposable
import de.vexxes.penaltycatalog.viewmodels.EventViewModel

fun NavGraphBuilder.eventScreensGraph(
    navController: NavController,
    eventViewModel: EventViewModel
) {
    navigation(
        startDestination = Screen.Events.route,
        route = Graph.EVENTS
    ) {
        eventListComposable(
            eventViewModel = eventViewModel,
            navigateToEventDetailScreen = {
                navController.navigate(route = Screen.EventDetail.passEventId(it))
            },
            navigateToEventEditScreen = {
                navController.navigate(route = Screen.EventEdit.passEventId(it))
            }
        )

        eventDetailComposable(
            eventViewModel = eventViewModel,
            navigateToEventList = {
                navController.popBackStack()
            },
            onEditClicked = {
                navController.navigate(Screen.EventEdit.passEventId(it))
            }
        )

        eventEditComposable(
            eventViewModel = eventViewModel,
            navigateBack = { navController.popBackStack() }
        )
    }
}
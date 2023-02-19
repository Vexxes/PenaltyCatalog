package de.vexxes.penaltycatalog.navigation.navGraph

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import de.vexxes.penaltycatalog.navigation.Graph
import de.vexxes.penaltycatalog.navigation.Screen
import de.vexxes.penaltycatalog.navigation.ScreenNavigation
import de.vexxes.penaltycatalog.navigation.composables.penaltyReceived.penaltyReceivedDetailComposable
import de.vexxes.penaltycatalog.navigation.composables.penaltyReceived.penaltyReceivedEditComposable
import de.vexxes.penaltycatalog.navigation.composables.penaltyReceived.penaltyReceivedListComposable
import de.vexxes.penaltycatalog.viewmodels.PenaltyReceivedViewModel

fun NavGraphBuilder.penaltyReceivedScreensGraph(
    navController: NavController,
    penaltyReceivedViewModel: PenaltyReceivedViewModel
) {
    navigation(
        startDestination = ScreenNavigation.PenaltyReceived.route,
        route = Graph.PENALTY_RECEIVED
    ) {
        penaltyReceivedListComposable(
            penaltyReceivedViewModel = penaltyReceivedViewModel,
            navigateToPenaltyReceivedDetailScreen = { penaltyReceivedId ->
                navController.navigate(route = Screen.PenaltyReceivedDetail.route + "/$penaltyReceivedId")
            },
            navigateToPenaltyReceivedEditScreen = { penaltyReceivedId ->
                navController.navigate(route = Screen.PenaltyReceivedEdit.route + "/$penaltyReceivedId")
            }
        )

        penaltyReceivedDetailComposable(
            penaltyReceivedViewModel = penaltyReceivedViewModel,
            navigateToPenaltyReceivedList = {
                navController.navigate(ScreenNavigation.PenaltyReceived.route)
            },
            onEditClicked = { penaltyReceivedId ->
                navController.navigate(Screen.PenaltyReceivedEdit.route + "/$penaltyReceivedId")
            }
        )

        penaltyReceivedEditComposable(
            penaltyReceivedViewModel = penaltyReceivedViewModel,
            navigateBack = { navController.popBackStack() }
        )
    }
}
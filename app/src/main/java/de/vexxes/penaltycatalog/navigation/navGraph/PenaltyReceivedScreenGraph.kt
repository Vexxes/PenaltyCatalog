package de.vexxes.penaltycatalog.navigation.navGraph

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import de.vexxes.penaltycatalog.navigation.Graph
import de.vexxes.penaltycatalog.navigation.Screen
import de.vexxes.penaltycatalog.navigation.composables.penaltyReceived.penaltyReceivedDetailComposable
import de.vexxes.penaltycatalog.navigation.composables.penaltyReceived.penaltyReceivedEditComposable
import de.vexxes.penaltycatalog.navigation.composables.penaltyReceived.penaltyReceivedListComposable
import de.vexxes.penaltycatalog.viewmodels.PenaltyReceivedViewModel

fun NavGraphBuilder.penaltyReceivedScreensGraph(
    navController: NavController,
    penaltyReceivedViewModel: PenaltyReceivedViewModel
) {
    navigation(
        startDestination = Screen.PenaltyReceived.route,
        route = Graph.PENALTY_RECEIVED
    ) {
        penaltyReceivedListComposable(
            penaltyReceivedViewModel = penaltyReceivedViewModel,
            navigateToPenaltyReceivedDetailScreen = {
                navController.navigate(route = Screen.PenaltyReceivedDetail.passPenaltyReceivedId(it))
            },
            navigateToPenaltyReceivedEditScreen = {
                navController.navigate(route = Screen.PenaltyReceivedEdit.passPenaltyReceivedId(it))
            }
        )

        penaltyReceivedDetailComposable(
            penaltyReceivedViewModel = penaltyReceivedViewModel,
            navigateToPenaltyReceivedList = {
                navController.popBackStack()
            },
            onEditClicked = {
                navController.navigate(Screen.PenaltyReceivedEdit.passPenaltyReceivedId(it))
            }
        )

        penaltyReceivedEditComposable(
            penaltyReceivedViewModel = penaltyReceivedViewModel,
            navigateBack = { navController.popBackStack() }
        )
    }
}
package de.vexxes.penaltycatalog.navigation.navGraph

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import de.vexxes.penaltycatalog.navigation.Graph
import de.vexxes.penaltycatalog.navigation.Screen
import de.vexxes.penaltycatalog.navigation.ScreenNavigation
import de.vexxes.penaltycatalog.navigation.composables.penaltyReceived.penaltyHistoryDetailComposable
import de.vexxes.penaltycatalog.navigation.composables.penaltyReceived.penaltyHistoryEditComposable
import de.vexxes.penaltycatalog.navigation.composables.penaltyReceived.penaltyHistoryListComposable
import de.vexxes.penaltycatalog.viewmodels.PenaltyReceivedViewModel

fun NavGraphBuilder.penaltyHistoryGraph(
    navController: NavController,
    penaltyReceivedViewModel: PenaltyReceivedViewModel
) {
    navigation(
        startDestination = ScreenNavigation.PenaltyHistory.route,
        route = Graph.HISTORY
    ) {
        penaltyHistoryListComposable(
            penaltyReceivedViewModel = penaltyReceivedViewModel,
            navigateToPenaltyHistoryDetailScreen = { penaltyHistoryId ->
                navController.navigate(route = Screen.PenaltyHistoryDetail.route + "/$penaltyHistoryId")
            },
            navigateToPenaltyHistoryEditScreen = { penaltyHistoryId ->
                navController.navigate(route = Screen.PenaltyHistoryEdit.route + "/$penaltyHistoryId")
            }
        )

        penaltyHistoryDetailComposable(
            penaltyReceivedViewModel = penaltyReceivedViewModel,
            navigateToPenaltyHistoryList = {
                navController.navigate(ScreenNavigation.PenaltyHistory.route)
            },
            onEditClicked = { penaltyHistoryId ->
                navController.navigate(Screen.PenaltyHistoryEdit.route + "/$penaltyHistoryId")
            }
        )

        penaltyHistoryEditComposable(
            penaltyReceivedViewModel = penaltyReceivedViewModel,
            navigateBack = { navController.popBackStack() }
        )
    }
}
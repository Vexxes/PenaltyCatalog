package de.vexxes.penaltycatalog.navigation.navGraph

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import de.vexxes.penaltycatalog.navigation.Graph
import de.vexxes.penaltycatalog.navigation.Screen
import de.vexxes.penaltycatalog.navigation.ScreenNavigation
import de.vexxes.penaltycatalog.navigation.composables.penaltyHistory.penaltyHistoryDetailComposable
import de.vexxes.penaltycatalog.navigation.composables.penaltyHistory.penaltyHistoryEditComposable
import de.vexxes.penaltycatalog.navigation.composables.penaltyHistory.penaltyHistoryListComposable
import de.vexxes.penaltycatalog.viewmodels.PenaltyHistoryViewModel

fun NavGraphBuilder.penaltyHistoryGraph(
    navController: NavController,
    penaltyHistoryViewModel: PenaltyHistoryViewModel
) {
    navigation(
        startDestination = ScreenNavigation.PenaltyHistory.route,
        route = Graph.HISTORY
    ) {
        penaltyHistoryListComposable(
            penaltyHistoryViewModel = penaltyHistoryViewModel,
            navigateToPenaltyHistoryDetailScreen = { penaltyHistoryId ->
                navController.navigate(route = Screen.PenaltyHistoryDetail.route + "/$penaltyHistoryId")
            },
            navigateToPenaltyHistoryEditScreen = { penaltyHistoryId ->
                navController.navigate(route = Screen.PenaltyHistoryEdit.route + "/$penaltyHistoryId")
            }
        )

        penaltyHistoryDetailComposable(
            penaltyHistoryViewModel = penaltyHistoryViewModel,
            navigateToPenaltyHistoryList = {
                navController.navigate(ScreenNavigation.PenaltyHistory.route)
            },
            onEditClicked = { penaltyHistoryId ->
                navController.navigate(Screen.PenaltyHistoryEdit.route + "/$penaltyHistoryId")
            }
        )

        penaltyHistoryEditComposable(
            penaltyHistoryViewModel = penaltyHistoryViewModel,
            navigateBack = { navController.popBackStack() }
        )
    }
}
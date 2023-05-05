package de.vexxes.penaltycatalog.navigation.navGraph

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import de.vexxes.penaltycatalog.navigation.Graph
import de.vexxes.penaltycatalog.navigation.Screen
import de.vexxes.penaltycatalog.navigation.composables.penaltyType.penaltyDetailComposable
import de.vexxes.penaltycatalog.navigation.composables.penaltyType.penaltyEditComposable
import de.vexxes.penaltycatalog.viewmodels.PenaltyTypeViewModel
import de.vexxes.penaltycatalog.navigation.composables.penaltyType.penaltyListComposable as penaltyListComposable

fun NavGraphBuilder.penaltyScreensGraph(
    navController: NavController,
    penaltyTypeViewModel: PenaltyTypeViewModel
) {
    navigation(
        startDestination = Screen.Penalties.route,
        route = Graph.PENALTY
    ) {
        penaltyListComposable(
            penaltyTypeViewModel = penaltyTypeViewModel,
            navigateToPenaltyDetailScreen = {
                navController.navigate(Screen.PenaltyDetail.passPenaltyTypeId(it))
            },
            navigateToPenaltyEditScreen = {
                navController.navigate(Screen.PenaltyEdit.passPenaltyTypeId(it))
            }
        )

        penaltyDetailComposable(
            penaltyTypeViewModel = penaltyTypeViewModel,
            navigateToPenaltyList = {
                navController.popBackStack()
            },
            onEditClicked = {
                navController.navigate(Screen.PenaltyEdit.passPenaltyTypeId(it))
            }
        )

        penaltyEditComposable(
            penaltyTypeViewModel = penaltyTypeViewModel,
            navigateBack = { navController.popBackStack() }
        )
    }
}
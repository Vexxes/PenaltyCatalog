package de.vexxes.penaltycatalog.navigation.navGraph

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import de.vexxes.penaltycatalog.navigation.Graph
import de.vexxes.penaltycatalog.navigation.Screen
import de.vexxes.penaltycatalog.navigation.ScreenNavigation
import de.vexxes.penaltycatalog.navigation.composables.penalty.penaltyDetailComposable
import de.vexxes.penaltycatalog.navigation.composables.penalty.penaltyEditComposable
import de.vexxes.penaltycatalog.viewmodels.PenaltyViewModel
import de.vexxes.penaltycatalog.navigation.composables.penalty.penaltyListComposable as penaltyListComposable

fun NavGraphBuilder.penaltyScreensGraph(
    navController: NavController,
    penaltyViewModel: PenaltyViewModel
) {
    navigation(
        startDestination = ScreenNavigation.Penalties.route,
        route = Graph.PENALTY
    ) {
        penaltyListComposable(
            penaltyViewModel = penaltyViewModel,
            navigateToPenaltyDetailScreen = { penaltyId ->
                navController.navigate(route = Screen.PenaltyDetail.route + "/$penaltyId")
            },
            navigateToPenaltyEditScreen = { penaltyId ->
                navController.navigate(route = Screen.PenaltyEdit.route + "/$penaltyId")
            }
        )

        penaltyDetailComposable(
            penaltyViewModel = penaltyViewModel,
            navigateToPenaltyList = {
                navController.navigate(ScreenNavigation.Penalties.route)
            },
            onEditClicked = { penaltyId ->
                navController.navigate(Screen.PenaltyEdit.route + "/$penaltyId")
            }
        )

        penaltyEditComposable(
            penaltyViewModel = penaltyViewModel,
            navigateBack = {
                navController.popBackStack()
            }
        )
    }
}
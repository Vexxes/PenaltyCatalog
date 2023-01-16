package de.vexxes.penaltycatalog.navigation.navGraph

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import de.vexxes.penaltycatalog.navigation.Graph
import de.vexxes.penaltycatalog.navigation.Screen
import de.vexxes.penaltycatalog.navigation.ScreenNavigation
import de.vexxes.penaltycatalog.navigation.composables.penaltyType.penaltyDetailComposable
import de.vexxes.penaltycatalog.navigation.composables.penaltyType.penaltyEditComposable
import de.vexxes.penaltycatalog.viewmodels.PenaltyTypeViewModel
import de.vexxes.penaltycatalog.navigation.composables.penaltyType.penaltyListComposable as penaltyListComposable

fun NavGraphBuilder.penaltyScreensGraph(
    navController: NavController,
    penaltyTypeViewModel: PenaltyTypeViewModel
) {
    navigation(
        startDestination = ScreenNavigation.Penalties.route,
        route = Graph.PENALTY
    ) {
        penaltyListComposable(
            penaltyTypeViewModel = penaltyTypeViewModel,
            navigateToPenaltyDetailScreen = { penaltyTypeId ->
                navController.navigate(route = Screen.PenaltyDetail.route + "/$penaltyTypeId")
            },
            navigateToPenaltyEditScreen = { penaltyTypeId ->
                navController.navigate(route = Screen.PenaltyEdit.route + "/$penaltyTypeId")
            }
        )

        penaltyDetailComposable(
            penaltyTypeViewModel = penaltyTypeViewModel,
            navigateToPenaltyList = {
                navController.navigate(ScreenNavigation.Penalties.route)
            },
            onEditClicked = { penaltyTypeId ->
                navController.navigate(Screen.PenaltyEdit.route + "/$penaltyTypeId")
            }
        )

        penaltyEditComposable(
            penaltyTypeViewModel = penaltyTypeViewModel,
            navigateBack = {
                navController.popBackStack()
            }
        )
    }
}
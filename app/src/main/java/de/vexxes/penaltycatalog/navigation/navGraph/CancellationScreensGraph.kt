package de.vexxes.penaltycatalog.navigation.navGraph

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import de.vexxes.penaltycatalog.navigation.Graph
import de.vexxes.penaltycatalog.navigation.Screen
import de.vexxes.penaltycatalog.navigation.ScreenNavigation
import de.vexxes.penaltycatalog.navigation.composables.cancellation.cancellationDetailComposable
import de.vexxes.penaltycatalog.navigation.composables.cancellation.cancellationEditComposable
import de.vexxes.penaltycatalog.navigation.composables.cancellation.cancellationListComposable
import de.vexxes.penaltycatalog.viewmodels.CancellationViewModel

fun NavGraphBuilder.cancellationScreensGraph(
    navController: NavController,
    cancellationViewModel: CancellationViewModel
) {
    navigation(
        startDestination = ScreenNavigation.Cancellations.route,
        route = Graph.CANCELLATIONS
    ) {
        cancellationListComposable(
            cancellationViewModel = cancellationViewModel,
            navigateToCancellationDetailScreen = { cancellationId ->
                navController.navigate(route = Screen.CancellationDetail.route + "/$cancellationId")
            },
            navigateToCancellationEditScreen = { cancellationId ->
                navController.navigate(route = Screen.CancellationEdit.route + "/$cancellationId")
            }
        )

        cancellationDetailComposable(
            cancellationViewModel = cancellationViewModel,
            navigateToCancellationList = {
                navController.navigate(ScreenNavigation.Cancellations.route)
            },
            onEditClicked = { cancellationId ->
                navController.navigate(Screen.CancellationEdit.route + "/$cancellationId")
            }
        )

        cancellationEditComposable(
            cancellationViewModel = cancellationViewModel,
            navigateBack = { navController.popBackStack() }
        )
    }
}
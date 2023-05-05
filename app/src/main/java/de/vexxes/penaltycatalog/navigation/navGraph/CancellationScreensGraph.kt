package de.vexxes.penaltycatalog.navigation.navGraph

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import de.vexxes.penaltycatalog.navigation.Graph
import de.vexxes.penaltycatalog.navigation.Screen
import de.vexxes.penaltycatalog.navigation.composables.cancellation.cancellationDetailComposable
import de.vexxes.penaltycatalog.navigation.composables.cancellation.cancellationEditComposable
import de.vexxes.penaltycatalog.navigation.composables.cancellation.cancellationListComposable
import de.vexxes.penaltycatalog.viewmodels.CancellationViewModel

fun NavGraphBuilder.cancellationScreensGraph(
    navController: NavController,
    cancellationViewModel: CancellationViewModel
) {
    navigation(
        startDestination = Screen.Cancellations.route,
        route = Graph.CANCELLATIONS
    ) {
        cancellationListComposable(
            cancellationViewModel = cancellationViewModel,
            navigateToCancellationDetailScreen = {
                navController.navigate(route = Screen.CancellationDetail.passCancellationId(it))
            },
            navigateToCancellationEditScreen = {
                navController.navigate(route = Screen.CancellationEdit.passCancellationId(it))
            }
        )

        cancellationDetailComposable(
            cancellationViewModel = cancellationViewModel,
            navigateToCancellationList = {
                navController.popBackStack()
            },
            onEditClicked = {
                navController.navigate(Screen.CancellationEdit.passCancellationId(it))
            }
        )

        cancellationEditComposable(
            cancellationViewModel = cancellationViewModel,
            navigateBack = { navController.popBackStack() }
        )
    }
}
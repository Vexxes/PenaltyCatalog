package de.vexxes.penaltycatalog.navigation.navGraph

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import de.vexxes.penaltycatalog.navigation.Graph
import de.vexxes.penaltycatalog.navigation.Screen
import de.vexxes.penaltycatalog.navigation.ScreenNavigation
import de.vexxes.penaltycatalog.navigation.ScreenText
import de.vexxes.penaltycatalog.navigation.composables.penalty.penaltyDetailComposable
import de.vexxes.penaltycatalog.navigation.composables.penalty.penaltyEditComposable
import de.vexxes.penaltycatalog.navigation.composables.player.playerDetailComposable
import de.vexxes.penaltycatalog.navigation.composables.player.playerEditComposable
import de.vexxes.penaltycatalog.navigation.composables.player.playerListComposable
import de.vexxes.penaltycatalog.viewmodels.PenaltyViewModel
import de.vexxes.penaltycatalog.viewmodels.PlayerViewModel
import de.vexxes.penaltycatalog.navigation.composables.penalty.penaltyListComposable as penaltyListComposable

fun NavGraphBuilder.mainScreensGraph(
    navController: NavController,
    playerViewModel: PlayerViewModel,
    penaltyViewModel: PenaltyViewModel
) {
    navigation(
        startDestination = ScreenNavigation.Penalties.route,
        route = Graph.MAIN
    ) {
        /*TODO Outsource the composable to different files*/

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

        composable(route = ScreenNavigation.PenaltyHistory.route) {
            // TODO Call penalty history screen
            ScreenText(text = ScreenNavigation.PenaltyHistory.name)
        }

        composable(route = ScreenNavigation.Cancellations.route) {
            // TODO Call cancellations screen
            ScreenText(text = ScreenNavigation.Cancellations.name)
        }

        composable(route = ScreenNavigation.Events.route) {
            // TODO Call events screen
            ScreenText(text = ScreenNavigation.Events.name)
        }

        playerListComposable(
            playerViewModel = playerViewModel,
            navigateToPlayerDetailScreen = { playerId ->
                navController.navigate(route = Screen.PlayerDetail.route + "/$playerId")
            },
            navigateToPlayerEditScreen = { playerId ->
                navController.navigate(route = Screen.PlayerEdit.route + "/$playerId")
            }
        )

        playerDetailComposable(
            playerViewModel = playerViewModel,
            navigateToPlayerList = {
                navController.navigate(ScreenNavigation.Players.route)
            },
            onEditClicked = { playerId ->
                navController.navigate(Screen.PlayerEdit.route + "/$playerId")
            }
        )

        playerEditComposable(
            playerViewModel = playerViewModel,
            navigateBack = {
                navController.popBackStack()
            }
        )
    }
}
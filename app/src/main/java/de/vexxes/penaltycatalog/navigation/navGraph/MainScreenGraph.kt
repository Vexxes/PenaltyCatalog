package de.vexxes.penaltycatalog.navigation.navGraph

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import de.vexxes.penaltycatalog.navigation.Graph
import de.vexxes.penaltycatalog.navigation.Screen
import de.vexxes.penaltycatalog.navigation.ScreenNavigation
import de.vexxes.penaltycatalog.navigation.ScreenText
import de.vexxes.penaltycatalog.navigation.composables.playerDetailComposable
import de.vexxes.penaltycatalog.navigation.composables.playerEditComposable
import de.vexxes.penaltycatalog.presentation.screen.playerList.PlayerListScreen
import de.vexxes.penaltycatalog.viewmodels.PlayerViewModel

//TODO Check if navController can be removed as parameter
fun NavGraphBuilder.mainScreensGraph(
    navController: NavController,
    playerViewModel: PlayerViewModel
) {
    navigation(
        startDestination = ScreenNavigation.Penalties.route,
        route = Graph.MAIN
    ) {
        /*TODO Outsource the composable to different files*/

        composable(route = ScreenNavigation.Penalties.route) {
            // TODO: Call penalties screen
            ScreenText(text = ScreenNavigation.Penalties.name)
        }

        composable(route = ScreenNavigation.Players.route) {
            PlayerListScreen(
                navigateToPlayerDetailScreen = { playerId ->
                    navController.navigate(route = Screen.PlayerDetail.route + "/$playerId")
                },
                navigateToPlayerEditScreen = { playerId ->
                    navController.navigate(route = Screen.PlayerEdit.route + "/$playerId")
                },
                playerViewModel = playerViewModel
            )
        }

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
//                navController.navigate(Screen.PlayerDetail.route)
            }
        )
    }
}
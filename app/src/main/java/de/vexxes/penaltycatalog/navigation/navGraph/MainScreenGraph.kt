package de.vexxes.penaltycatalog.navigation.navGraph

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import de.vexxes.penaltycatalog.navigation.Graph
import de.vexxes.penaltycatalog.navigation.Screen
import de.vexxes.penaltycatalog.navigation.ScreenText
import de.vexxes.penaltycatalog.presentation.screen.player.PlayerListScreen
import de.vexxes.penaltycatalog.viewmodels.PlayerViewModel

//TODO Check if navController can be removed as parameter
fun NavGraphBuilder.mainScreensGraph(
    navController: NavController,
    playerViewModel: PlayerViewModel
) {
    navigation(
        startDestination = Screen.Penalties.route,
        route = Graph.MAIN
    ) {
        composable(route = Screen.Penalties.route) {
            // TODO: Call penalties screen
            ScreenText(text = Screen.Penalties.name)
        }

        composable(route = Screen.Players.route) {
            // TODO Call player screen
//            ScreenText(text = Screen.Players.name)
            PlayerListScreen(
                navigateToPlayerDetailScreen = { },
                playerViewModel = playerViewModel
            )
        }

        composable(route = Screen.PenaltyHistory.route) {
            // TODO Call penalty history screen
            ScreenText(text = Screen.PenaltyHistory.name)
        }

        composable(route = Screen.Cancellations.route) {
            // TODO Call cancellations screen
            ScreenText(text = Screen.Cancellations.name)
        }

        composable(route = Screen.Events.route) {
            // TODO Call events screen
            ScreenText(text = Screen.Events.name)
        }
    }
}
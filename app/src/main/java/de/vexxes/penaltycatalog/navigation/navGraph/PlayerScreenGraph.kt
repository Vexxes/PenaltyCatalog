package de.vexxes.penaltycatalog.navigation.navGraph

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import de.vexxes.penaltycatalog.navigation.Graph
import de.vexxes.penaltycatalog.navigation.Screen
import de.vexxes.penaltycatalog.navigation.composables.player.playerDetailComposable
import de.vexxes.penaltycatalog.navigation.composables.player.playerEditComposable
import de.vexxes.penaltycatalog.navigation.composables.player.playerListComposable
import de.vexxes.penaltycatalog.viewmodels.PlayerViewModel

fun NavGraphBuilder.playerScreensGraph(
    navController: NavController,
    playerViewModel: PlayerViewModel
) {
    navigation(
        startDestination = Screen.Players.route,
        route = Graph.PLAYER
    ) {
        playerListComposable(
            playerViewModel = playerViewModel,
            navigateToPlayerDetailScreen = {
                navController.navigate(Screen.PlayerDetail.passPlayerId(it))
            },
            navigateToPlayerEditScreen = {
                navController.navigate(Screen.PlayerEdit.passPlayerId(it))
            }
        )

        playerDetailComposable(
            playerViewModel = playerViewModel,
            navigateToPlayerList = {
                navController.popBackStack()
            },
            onEditClicked = {
                navController.navigate(Screen.PlayerEdit.passPlayerId(it))
            }
        )

        playerEditComposable(
            playerViewModel = playerViewModel,
            navigateBack = { navController.popBackStack() }
        )
    }
}
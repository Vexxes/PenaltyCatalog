package de.vexxes.penaltycatalog.navigation.navGraph

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import de.vexxes.penaltycatalog.navigation.Graph
import de.vexxes.penaltycatalog.navigation.Screen
import de.vexxes.penaltycatalog.navigation.ScreenNavigation
import de.vexxes.penaltycatalog.navigation.composables.player.playerDetailComposable
import de.vexxes.penaltycatalog.navigation.composables.player.playerEditComposable
import de.vexxes.penaltycatalog.navigation.composables.player.playerListComposable
import de.vexxes.penaltycatalog.viewmodels.PlayerViewModel

fun NavGraphBuilder.playerScreensGraph(
    navController: NavController,
    playerViewModel: PlayerViewModel
) {
    navigation(
        startDestination = ScreenNavigation.Players.route,
        route = Graph.PLAYER
    ) {
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
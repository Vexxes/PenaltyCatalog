package de.vexxes.penaltycatalog.navigation.composables

import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import de.vexxes.penaltycatalog.navigation.Screen
import de.vexxes.penaltycatalog.presentation.screen.player.PlayerDetailScreen
import de.vexxes.penaltycatalog.viewmodels.PlayerViewModel

fun NavGraphBuilder.playerDetailComposable(
    playerViewModel: PlayerViewModel,
    navigateToPlayerList: () -> Unit,
    onEditClicked: (String) -> Unit
) {
    composable(
        route = Screen.PlayerDetail.route + Screen.PlayerDetail.argument,
        arguments = listOf(navArgument("playerId") {
            type = NavType.StringType
        })
    ) { navBackStackEntry ->

        // Get playerId from argument
        val playerId = navBackStackEntry.arguments?.getString("playerId")
        LaunchedEffect(key1 = playerId) {
            if (!playerId.isNullOrEmpty() && playerId != "-1")
                playerViewModel.getPlayerById(playerId = playerId)
        }

        PlayerDetailScreen(
            playerViewModel = playerViewModel,
            onBackClicked = {
                    navigateToPlayerList()
                },
            onDeleteClicked = {
                /*TODO Show dialog before deleting player*/
                playerViewModel.deletePlayer()
                navigateToPlayerList()
            },
            onEditClicked = { onEditClicked(it) }
        )
    }
}
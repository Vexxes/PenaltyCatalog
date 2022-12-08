package de.vexxes.penaltycatalog.navigation.composables.player

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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

        var visible by remember {
            mutableStateOf(false)
        }
        LaunchedEffect(key1 = true) { visible = true}

        AnimatedVisibility(
            visible = visible,
            enter = fadeIn(animationSpec = tween(durationMillis = 500)),
            exit = fadeOut(animationSpec = tween(durationMillis = 500))
        ) {
            PlayerDetailScreen(
                playerViewModel = playerViewModel,
                onBackClicked = {
                    navigateToPlayerList()
                },
                onDeleteClicked = {
                    playerViewModel.deletePlayer()
                    navigateToPlayerList()
                },
                onEditClicked = { onEditClicked(it) }
            )
        }
    }
}
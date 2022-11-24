package de.vexxes.penaltycatalog.navigation.composables

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
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
import de.vexxes.penaltycatalog.presentation.screen.player.PlayerScreen
import de.vexxes.penaltycatalog.viewmodels.PlayerViewModel

fun NavGraphBuilder.playerSingle(
    playerViewModel: PlayerViewModel,
    navigateToPlayerList: () -> Unit
) {
    composable(
        route = Screen.PlayerSingle.route,
        arguments = listOf(navArgument("playerId") {
            type = NavType.StringType
        })
    ) { navBackStackEntry ->

        // Get playerId from argument
        val playerId = navBackStackEntry.arguments?.getString("playerId")
        LaunchedEffect(key1 = playerId) {
            if(!playerId.isNullOrEmpty() && playerId != "-1") {
                playerViewModel.getPlayerById(playerId = playerId)
            } else {
                playerViewModel.resetPlayer()
            }
        }

        var visible by remember {
            mutableStateOf(false)
        }
        LaunchedEffect(key1 = true) { visible = true}

        AnimatedVisibility(
            visible = visible,
            enter = slideInHorizontally(animationSpec = tween(durationMillis = 300)) { fullWidth -> fullWidth },
            exit = slideOutHorizontally(animationSpec = tween(durationMillis = 300)) { fullWidth -> fullWidth }
        ) {
            // TODO Call player detail screen
            PlayerScreen(
                playerViewModel = playerViewModel,
                onBackClicked = {
                    visible = false
                    navigateToPlayerList()
                },
                onEditClicked = { /*TODO Handle on edit clicked*/ },
                onSaveClicked = { /*TODO Handle on save clicked*/ }
            )
        }
    }
}
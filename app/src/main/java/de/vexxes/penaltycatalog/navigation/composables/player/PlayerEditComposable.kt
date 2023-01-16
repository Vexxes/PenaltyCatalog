package de.vexxes.penaltycatalog.navigation.composables.player

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
import de.vexxes.penaltycatalog.presentation.screen.player.PlayerEditScreen
import de.vexxes.penaltycatalog.viewmodels.PlayerViewModel

fun NavGraphBuilder.playerEditComposable(
    playerViewModel: PlayerViewModel,
    navigateBack: () -> Unit
) {
    composable(
        route = Screen.PlayerEdit.route + Screen.PlayerEdit.argument,
        arguments = listOf(navArgument("playerId") {
            type = NavType.StringType
        })
    ) { navBackStackEntry ->

        // Get playerId from argument
        val playerId = navBackStackEntry.arguments?.getString("playerId")
        LaunchedEffect(key1 = playerId) {
            if(playerId == "-1")
                playerViewModel.resetPlayerUiState()
        }

        var visible by remember {
            mutableStateOf(false)
        }
        LaunchedEffect(key1 = true) {
            visible = true
        }

        // Make invisible and navigate back, if postPlayer or updatePlayer was successful
        var postPlayer by playerViewModel.postPlayer
        var updatePlayer by playerViewModel.updatePlayer
        if (postPlayer || updatePlayer) {
            postPlayer = false
            updatePlayer = false
            visible = false
            navigateBack()
        }

        AnimatedVisibility(
            visible = visible,
            enter = slideInHorizontally(animationSpec = tween(durationMillis = 300)) { fullWidth -> fullWidth },
            exit = slideOutHorizontally(animationSpec = tween(durationMillis = 300)) { fullWidth -> fullWidth }
        ) {
            PlayerEditScreen(
                playerViewModel = playerViewModel,
                onBackClicked = {
                    visible = false
                    navigateBack()
                },
                onSaveClicked = {
                    if (playerViewModel.playerUiState.value.id == "") {
                        playerViewModel.postPlayer()
                    } else {
                        playerViewModel.updatePlayer()
                    }
                }
            )
        }
    }
}
package de.vexxes.penaltycatalog.navigation.composables.player

import de.vexxes.penaltycatalog.navigation.ScreenNavigation
import de.vexxes.penaltycatalog.presentation.screen.player.PlayerListScreen
import de.vexxes.penaltycatalog.util.SearchAppBarState
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import de.vexxes.penaltycatalog.viewmodels.PlayerViewModel

fun NavGraphBuilder.playerListComposable(
    playerViewModel: PlayerViewModel,
    navigateToPlayerDetailScreen: (String) -> Unit,
    navigateToPlayerEditScreen: (String) -> Unit
) {
    composable(
        route = ScreenNavigation.Players.route
    ) {
        playerViewModel.searchAppBarState.value = SearchAppBarState.CLOSED

        PlayerListScreen(
            playerViewModel = playerViewModel,
            navigateToPlayerDetailScreen = { playerId ->
                navigateToPlayerDetailScreen(playerId)

            },
            navigateToPlayerEditScreen = { playerId ->
                navigateToPlayerEditScreen(playerId)
            },
        )
    }
}
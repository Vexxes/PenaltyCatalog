package de.vexxes.penaltycatalog.navigation.composables.player

import androidx.compose.runtime.LaunchedEffect
import de.vexxes.penaltycatalog.navigation.Screen
import de.vexxes.penaltycatalog.presentation.screen.player.PlayerListScreen
import de.vexxes.penaltycatalog.domain.enums.SearchAppBarState
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import de.vexxes.penaltycatalog.domain.uievent.SearchUiEvent
import de.vexxes.penaltycatalog.viewmodels.PlayerViewModel

fun NavGraphBuilder.playerListComposable(
    playerViewModel: PlayerViewModel,
    navigateToPlayerDetailScreen: (String) -> Unit,
    navigateToPlayerEditScreen: (String) -> Unit
) {
    composable(
        route = Screen.Players.route
    ) {
        LaunchedEffect(true) {
            playerViewModel.onSearchUiEvent(SearchUiEvent.SearchAppBarStateChanged(SearchAppBarState.CLOSED))
        }

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
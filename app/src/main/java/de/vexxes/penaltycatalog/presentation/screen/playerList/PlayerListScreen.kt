package de.vexxes.penaltycatalog.presentation.screen.playerList

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import de.vexxes.penaltycatalog.R
import de.vexxes.penaltycatalog.component.GeneralTopBar
import de.vexxes.penaltycatalog.domain.model.Player
import de.vexxes.penaltycatalog.util.SearchAppBarState
import de.vexxes.penaltycatalog.util.SortOrder
import de.vexxes.penaltycatalog.util.toValue
import de.vexxes.penaltycatalog.viewmodels.PlayerViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlayerListScreen(
    navigateToPlayerDetailScreen: (playerId: String) -> Unit,
    navigateToPlayerEditScreen: (playerId: String) -> Unit,
    playerViewModel: PlayerViewModel
) {

    val players by playerViewModel.players
    val searchAppBarState by playerViewModel.searchAppBarState
    val searchTextState by playerViewModel.searchText

    LaunchedEffect(key1 = true) {
        playerViewModel.getAllPlayers()
    }

    Scaffold(

        topBar = {
            GeneralTopBar(
                searchAppBarState = searchAppBarState,
                searchTextState = searchTextState,
                onDefaultSearchClicked = {
                    playerViewModel.searchAppBarState.value = SearchAppBarState.OPENED
                },
                onSearchBarClicked = {
                    /*TODO Create action for onSearchBarClicked*/
                },
                onAscendingClicked = {
                    playerViewModel.sortOrder.value = SortOrder.ASCENDING
                    playerViewModel.getAllPlayers()
                },
                onDescendingClicked = {
                    playerViewModel.sortOrder.value = SortOrder.DESCENDING
                    playerViewModel.getAllPlayers()
                },
                onTextChanged = {
                    playerViewModel.searchText.value = it
                    playerViewModel.getPlayersBySearch()
                },
                onCloseClicked = {
                    playerViewModel.searchAppBarState.value = SearchAppBarState.CLOSED
                    playerViewModel.searchText.value = ""
                    playerViewModel.getAllPlayers()
                }
            )
        },

        content = {
            Box(
                modifier = Modifier.padding(it)
            ) {
                PlayerListContent(
                    players = players,
                    navigateToPlayerDetailScreen = navigateToPlayerDetailScreen
                )
            }
        },

        floatingActionButton = {
            PlayerFab(navigateToPlayerDetailScreen = navigateToPlayerEditScreen)
        }
    )
}

@Composable
fun PlayerFab(
    navigateToPlayerDetailScreen: (playerId: String) -> Unit
) {
    FloatingActionButton(
        onClick = {
            navigateToPlayerDetailScreen("-1")
        }) {
        Icon(
            imageVector = Icons.Default.Add,
            contentDescription = stringResource(id = R.string.AddPlayer)
        )
    }
}

@Preview
@Composable
fun PlayerFabPreview() {
    PlayerFab(
        navigateToPlayerDetailScreen = { }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun PlayerListScreenPreview() {
    val players = listOf(
        Player(_id = "",
            number = 5,
            firstName = "Thomas",
            lastName = "Schneider",
            birthday = "21.06.1997",
            street = "Bussardweg 3C",
            zipcode = 49424,
            city = "Goldenstedt",
            playedGames = 4,
            goals = 16,
            yellowCards = 1,
            twoMinutes = 1,
            redCards = 0
        ),
        Player(_id = "",
            number = 1,
            firstName = "Michael",
            lastName = "Muhle",
            birthday = "02.06.1996",
            street = "Einer Straße 1",
            zipcode = 49424,
            city = "Goldenstedt",
            playedGames = 4,
            goals = 0,
            yellowCards = 0,
            twoMinutes = 0,
            redCards = 0
        )
    )

    Scaffold(

        topBar = {
            GeneralTopBar(
                searchAppBarState = SearchAppBarState.CLOSED,
                searchTextState = "",
                onDefaultSearchClicked = { },
                onSearchBarClicked = { },
                onAscendingClicked = { },
                onDescendingClicked = { },
                onTextChanged = { },
                onCloseClicked = { }
            )
        },

        content = {
            Box(
                modifier = Modifier.padding(it)
            ) {
                PlayerListContent(
                    players = players,
                    navigateToPlayerDetailScreen = { }
                )
            }
        },

        floatingActionButton = {
            PlayerFab(navigateToPlayerDetailScreen = {  })
        }
    )
}
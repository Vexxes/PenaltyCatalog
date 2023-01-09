package de.vexxes.penaltycatalog.presentation.screen.player

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import de.vexxes.penaltycatalog.R
import de.vexxes.penaltycatalog.component.GeneralTopBar
import de.vexxes.penaltycatalog.domain.model.ApiResponse
import de.vexxes.penaltycatalog.domain.model.Player
import de.vexxes.penaltycatalog.domain.model.playerExample
import de.vexxes.penaltycatalog.domain.enums.SortOrder
import de.vexxes.penaltycatalog.domain.uievent.SearchUiEvent
import de.vexxes.penaltycatalog.util.RequestState
import de.vexxes.penaltycatalog.util.SearchAppBarState
import de.vexxes.penaltycatalog.viewmodels.PlayerViewModel
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun PlayerListScreen(
    navigateToPlayerDetailScreen: (playerId: String) -> Unit,
    navigateToPlayerEditScreen: (playerId: String) -> Unit,
    playerViewModel: PlayerViewModel
) {
    val players by playerViewModel.players
    val requestState by playerViewModel.requestState
    val searchUiState by playerViewModel.searchUiState

    val refreshPlayers = { playerViewModel.getAllPlayers() }
    val refreshing = requestState is RequestState.Loading
    val pullRefreshState = rememberPullRefreshState(refreshing = refreshing, onRefresh = { refreshPlayers() })

    LaunchedEffect(key1 = true) {
        refreshPlayers()
    }

    Box(modifier = Modifier
        .pullRefresh(pullRefreshState))
    {
        PlayerListScaffold(
            searchAppBarState = searchUiState.searchAppBarState,
            searchText = searchUiState.searchText,
            onSearchTextChanged = {
                playerViewModel.onSearchUiEvent(SearchUiEvent.SearchTextChanged(it))
            },
            onDefaultSearchClicked = {
                playerViewModel.onSearchUiEvent(SearchUiEvent.SearchAppBarStateChanged(SearchAppBarState.OPENED))
            },
            onCloseClicked = {
                playerViewModel.onSearchUiEvent(SearchUiEvent.SearchAppBarStateChanged(SearchAppBarState.CLOSED))
                playerViewModel.onSearchUiEvent(SearchUiEvent.SearchTextChanged(""))
            },
            players = players,
            requestState = requestState,
            navigateToPlayerDetailScreen = navigateToPlayerDetailScreen,
            navigateToPlayerEditScreen = navigateToPlayerEditScreen
        )

        PullRefreshIndicator(refreshing = refreshing, state = pullRefreshState, modifier = Modifier.align(
            Alignment.TopCenter)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlayerListScaffold(
    searchAppBarState: SearchAppBarState,
    searchText: String,
    onSearchTextChanged: (String) -> Unit,
    onDefaultSearchClicked: () -> Unit,
    onCloseClicked: () -> Unit,
    players: List<Player>,
    requestState: RequestState,
    navigateToPlayerDetailScreen: (String) -> Unit,
    navigateToPlayerEditScreen: (String) -> Unit
) {
    Scaffold(

        topBar = {
            GeneralTopBar(
                searchAppBarState = searchAppBarState,
                searchTextState = searchText,
                onDefaultSearchClicked = onDefaultSearchClicked,
                onSortClicked = { },
                onSearchTextChanged = onSearchTextChanged,
                onCloseClicked = onCloseClicked
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
        },
/*
        snackbarHost = {
            PlayerListSnackbar(
                requestState = requestState,
            )
        }
 */
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

@Composable
fun PlayerListSnackbar(
    requestState: RequestState
) {
    var showSnackbar by remember { mutableStateOf(true)}

    /*TODO Other approach possible?*/
    // Reset snackbar after 3 seconds
    LaunchedEffect(key1 = requestState) {
        showSnackbar = true
        delay(3000)
        showSnackbar = false
    }

    if (showSnackbar) {
        Snackbar(
            modifier = Modifier
                .padding(8.dp),
            action = {
                    Text(
                        modifier = Modifier
                            .clickable { /*resetApiResponse()*/ },
                        text = stringResource(id = R.string.Ok))
            }
        ) {
            Text(
                text = "" //if(!apiResponse.message.isNullOrEmpty()) apiResponse.message else ""
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PlayerListScreenPreview() {
    val players = listOf(
        playerExample(),
        playerExample(),
        playerExample()
    )

    PlayerListScaffold(
        searchAppBarState = SearchAppBarState.CLOSED,
        searchText = "",
        onSearchTextChanged = { },
        onDefaultSearchClicked = { },
        onCloseClicked = { },
        players = players,
        requestState = RequestState.Success,
        navigateToPlayerDetailScreen = { },
        navigateToPlayerEditScreen = { }
    )
}

@Preview
@Composable
fun PlayerFabPreview() {
    PlayerFab(
        navigateToPlayerDetailScreen = { }
    )
}
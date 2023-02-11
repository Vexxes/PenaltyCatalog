package de.vexxes.penaltycatalog.presentation.screen.penaltyType

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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import de.vexxes.penaltycatalog.R
import de.vexxes.penaltycatalog.component.GeneralTopBar
import de.vexxes.penaltycatalog.domain.enums.SearchAppBarState
import de.vexxes.penaltycatalog.domain.model.PenaltyType
import de.vexxes.penaltycatalog.domain.model.penaltyExample1
import de.vexxes.penaltycatalog.domain.model.penaltyExample2
import de.vexxes.penaltycatalog.domain.model.penaltyExample3
import de.vexxes.penaltycatalog.domain.uievent.SearchUiEvent
import de.vexxes.penaltycatalog.util.RequestState
import de.vexxes.penaltycatalog.viewmodels.PenaltyTypeViewModel

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun PenaltyTypeListScreen(
    penaltyTypeViewModel: PenaltyTypeViewModel,
    navigateToPenaltyDetailScreen: (penaltyTypeId: String) -> Unit,
    navigateToPenaltyEditScreen: (penaltyTypeId: String) -> Unit
) {
    val penalties by penaltyTypeViewModel.penalties
    val requestState by penaltyTypeViewModel.requestState
    val searchUiState by penaltyTypeViewModel.searchUiState

    val refreshPenalties = { penaltyTypeViewModel.getAllPenalties() }
    val refreshing = requestState is RequestState.Loading
    val pullRefreshState = rememberPullRefreshState(refreshing = refreshing, onRefresh = { refreshPenalties() })

    LaunchedEffect(key1 = true) {
        refreshPenalties()
    }

    Box(modifier = Modifier.pullRefresh(pullRefreshState)) {
        PenaltyTypeListScaffold(
            searchAppBarState = searchUiState.searchAppBarState,
            searchText = searchUiState.searchText,
            onSearchTextChanged = { penaltyTypeViewModel.onSearchUiEvent(SearchUiEvent.SearchTextChanged(it)) },
            onDefaultSearchClicked = { penaltyTypeViewModel.onSearchUiEvent(SearchUiEvent.SearchAppBarStateChanged(
                SearchAppBarState.OPENED)) },
            onCloseClicked = {
                penaltyTypeViewModel.onSearchUiEvent(SearchUiEvent.SearchAppBarStateChanged(SearchAppBarState.CLOSED))
                penaltyTypeViewModel.onSearchUiEvent(SearchUiEvent.SearchTextChanged(""))
            },
            penalties = penalties,
            navigateToPenaltyDetailScreen = navigateToPenaltyDetailScreen,
            navigateToPenaltyEditScreen = navigateToPenaltyEditScreen
        )

        PullRefreshIndicator(refreshing = refreshing, state = pullRefreshState, modifier = Modifier.align(Alignment.TopCenter))
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun PenaltyTypeListScaffold(
    searchAppBarState: SearchAppBarState,
    searchText: String,
    onSearchTextChanged: (String) -> Unit,
    onDefaultSearchClicked: () -> Unit,
    onCloseClicked: () -> Unit,
    penalties: List<PenaltyType>,
    navigateToPenaltyDetailScreen: (String) -> Unit,
    navigateToPenaltyEditScreen: (String) -> Unit
) {
    Scaffold(
        topBar = {
            GeneralTopBar(
                searchAppBarState = searchAppBarState,
                searchTextState = searchText,
                onDefaultSearchClicked = onDefaultSearchClicked,
                onSearchTextChanged = onSearchTextChanged,
                onCloseClicked = onCloseClicked
            )
        },

        content = {
            Box(modifier = Modifier.padding(it)) {
                PenaltyTypeListContent(
                    penalties = penalties,
                    navigateToPenaltyDetailScreen = navigateToPenaltyDetailScreen
                )
            }
        },

        floatingActionButton = {
            PenaltyTypeFab(navigateToPenaltyEditScreen = navigateToPenaltyEditScreen)
        }
    )
}

@Composable
private fun PenaltyTypeFab(
    navigateToPenaltyEditScreen: (String) -> Unit
) {
    FloatingActionButton(
        onClick = {
            navigateToPenaltyEditScreen("-1")
        }) {
        Icon(
            imageVector = Icons.Default.Add,
            contentDescription = stringResource(id = R.string.AddPenalty)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PenaltyTypeListScreenPreview() {

    val penalties = listOf(
        penaltyExample1(),
        penaltyExample2(),
        penaltyExample3()
    )

    PenaltyTypeListScaffold(
        searchAppBarState = SearchAppBarState.CLOSED,
        searchText = "",
        onSearchTextChanged = { },
        onDefaultSearchClicked = { },
        onCloseClicked = { },
        penalties = penalties,
        navigateToPenaltyDetailScreen = { },
        navigateToPenaltyEditScreen = { }
    )
}

@Preview
@Composable
private fun PenaltyTypeFabPreview() {
    PenaltyTypeFab(
        navigateToPenaltyEditScreen = { }
    )
}
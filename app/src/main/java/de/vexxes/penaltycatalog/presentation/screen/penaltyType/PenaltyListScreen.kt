package de.vexxes.penaltycatalog.presentation.screen.penaltyType

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import de.vexxes.penaltycatalog.R
import de.vexxes.penaltycatalog.component.GeneralTopBar
import de.vexxes.penaltycatalog.domain.model.ApiResponse
import de.vexxes.penaltycatalog.domain.model.Penalty
import de.vexxes.penaltycatalog.domain.enums.SortOrder
import de.vexxes.penaltycatalog.domain.model.penaltyExample1
import de.vexxes.penaltycatalog.domain.model.penaltyExample2
import de.vexxes.penaltycatalog.domain.model.penaltyExample3
import de.vexxes.penaltycatalog.domain.uievent.SearchUiEvent
import de.vexxes.penaltycatalog.util.RequestState
import de.vexxes.penaltycatalog.util.SearchAppBarState
import de.vexxes.penaltycatalog.viewmodels.PenaltyTypeViewModel
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun PenaltyListScreen(
    penaltyTypeViewModel: PenaltyTypeViewModel,
    navigateToPenaltyDetailScreen: (penaltyId: String) -> Unit,
    navigateToPenaltyEditScreen: (penaltyId: String) -> Unit
) {
    val penalties by penaltyTypeViewModel.penalties
    val apiResponse by penaltyTypeViewModel.lastResponse
    val searchUiState by penaltyTypeViewModel.searchUiState

    val refreshPenalties = { penaltyTypeViewModel.getAllPenalties() }
    val refreshing = penaltyTypeViewModel.apiResponse.value is RequestState.Loading
    val pullRefreshState = rememberPullRefreshState(refreshing = refreshing, onRefresh = { refreshPenalties() })

    LaunchedEffect(key1 = true) {
        refreshPenalties()
    }

    Box(modifier = Modifier.pullRefresh(pullRefreshState)) {
        PenaltyListScaffold(
            searchAppBarState = searchUiState.searchAppBarState,
            searchText = searchUiState.searchText,
            onSearchTextChanged = { penaltyTypeViewModel.onSearchUiEvent(SearchUiEvent.SearchTextChanged(it)) },
            onDefaultSearchClicked = { penaltyTypeViewModel.onSearchUiEvent(SearchUiEvent.SearchAppBarStateChanged(SearchAppBarState.OPENED)) },
            onSortClicked = { },
            onCloseClicked = {
                penaltyTypeViewModel.onSearchUiEvent(SearchUiEvent.SearchAppBarStateChanged(SearchAppBarState.CLOSED))
                penaltyTypeViewModel.onSearchUiEvent(SearchUiEvent.SearchTextChanged(""))
            },
            penalties = penalties,
            apiResponse = apiResponse,
            resetApiResponse = { penaltyTypeViewModel.resetLastResponse() },
            navigateToPenaltyDetailScreen = navigateToPenaltyDetailScreen,
            navigateToPenaltyEditScreen = navigateToPenaltyEditScreen
        )

        PullRefreshIndicator(refreshing = refreshing, state = pullRefreshState, modifier = Modifier.align(Alignment.TopCenter))
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PenaltyListScaffold(
    searchAppBarState: SearchAppBarState,
    searchText: String,
    onSearchTextChanged: (String) -> Unit,
    onDefaultSearchClicked: () -> Unit,
    onSortClicked: (SortOrder) -> Unit,
    onCloseClicked: () -> Unit,
    penalties: List<Penalty>,
    apiResponse: ApiResponse,
    resetApiResponse: () -> Unit,
    navigateToPenaltyDetailScreen: (String) -> Unit,
    navigateToPenaltyEditScreen: (String) -> Unit
) {
    Scaffold(
        topBar = {
            GeneralTopBar(
                searchAppBarState = searchAppBarState,
                searchTextState = searchText,
                onDefaultSearchClicked = onDefaultSearchClicked,
                onSortClicked = onSortClicked,
                onSearchTextChanged = onSearchTextChanged,
                onCloseClicked = onCloseClicked
            )
        },

        content = {
            Box(modifier = Modifier.padding(it)) {
                PenaltyListContent(
                    penalties = penalties,
                    navigateToPenaltyDetailScreen = navigateToPenaltyDetailScreen
                )
            }
        },

        floatingActionButton = {
            PenaltyFab(navigateToPenaltyEditScreen = navigateToPenaltyEditScreen)
        },

        snackbarHost = {
            PenaltyListSnackbar(
                apiResponse = apiResponse,
                resetApiResponse = resetApiResponse
            )
        }
    )
}

@Composable
fun PenaltyFab(
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

@Composable
fun PenaltyListSnackbar(
    apiResponse: ApiResponse,
    resetApiResponse: () -> Unit
) {
    /*TODO Other approach possible?*/
    // Reset snackbar after 3 seconds
    LaunchedEffect(key1 = true) {
        delay(3000)
        resetApiResponse()
    }

    if(apiResponse.hashCode() != ApiResponse().hashCode()) {
        Snackbar(
            modifier = Modifier
                .padding(8.dp),
            action = {
                Text(
                    modifier = Modifier
                        .clickable { resetApiResponse() },
                    text = stringResource(id = R.string.Ok)
                )
            }
        ) {
            Text(
                text = if(!apiResponse.message.isNullOrEmpty()) apiResponse.message else ""
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PenaltyListScreenPreview() {

    val penalties = listOf(
        penaltyExample1(),
        penaltyExample2(),
        penaltyExample3()
    )

    PenaltyListScaffold(
        searchAppBarState = SearchAppBarState.CLOSED,
        searchText = "",
        onSearchTextChanged = { },
        onDefaultSearchClicked = { },
        onSortClicked = { },
        onCloseClicked = { },
        penalties = penalties,
        apiResponse = ApiResponse(),
        resetApiResponse = { },
        navigateToPenaltyDetailScreen = { },
        navigateToPenaltyEditScreen = { }
    )
}

@Preview
@Composable
fun PenaltyFabPreview() {
    PenaltyFab(
        navigateToPenaltyEditScreen = { }
    )
}
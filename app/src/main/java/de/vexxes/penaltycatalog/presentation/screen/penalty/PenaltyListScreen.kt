package de.vexxes.penaltycatalog.presentation.screen.penalty

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
import de.vexxes.penaltycatalog.domain.model.SortOrder
import de.vexxes.penaltycatalog.domain.uievent.SearchUiEvent
import de.vexxes.penaltycatalog.util.RequestState
import de.vexxes.penaltycatalog.util.SearchAppBarState
import de.vexxes.penaltycatalog.viewmodels.PenaltyViewModel
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun PenaltyListScreen(
    penaltyViewModel: PenaltyViewModel,
    navigateToPenaltyDetailScreen: (penaltyId: String) -> Unit,
    navigateToPenaltyEditScreen: (penaltyId: String) -> Unit
) {
    val penalties by penaltyViewModel.penalties
    val apiResponse by penaltyViewModel.lastResponse
    val searchUiState by penaltyViewModel.searchUiState

    val refreshPenalties = { penaltyViewModel.getAllPenalties() }
    val refreshing = penaltyViewModel.apiResponse.value is RequestState.Loading
    val pullRefreshState = rememberPullRefreshState(refreshing = refreshing, onRefresh = { refreshPenalties() })

    LaunchedEffect(key1 = true) {
        refreshPenalties()
    }

    Box(modifier = Modifier.pullRefresh(pullRefreshState)) {
        PenaltyListScaffold(
            searchAppBarState = searchUiState.searchAppBarState,
            searchText = searchUiState.searchText,
            onSearchTextChanged = { penaltyViewModel.onSearchUiEvent(SearchUiEvent.SearchTextChanged(it)) },
            onDefaultSearchClicked = { penaltyViewModel.onSearchUiEvent(SearchUiEvent.SearchAppBarStateChanged(SearchAppBarState.OPENED)) },
            onSortClicked = { },
            onCloseClicked = {
                penaltyViewModel.onSearchUiEvent(SearchUiEvent.SearchAppBarStateChanged(SearchAppBarState.CLOSED))
                penaltyViewModel.onSearchUiEvent(SearchUiEvent.SearchTextChanged(""))
            },
            penalties = penalties,
            apiResponse = apiResponse,
            resetApiResponse = { penaltyViewModel.resetLastResponse() },
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
        Penalty(
            _id = "",
            name = "Monatsbeitrag",
            categoryName = "Monatsbeitrag",
            description = "",
            isBeer = false,
            value = "5"
        ),
        Penalty(
            _id = "",
            name = "Verspätete Zahlung des Monatsbeitrag",
            categoryName = "Monatsbeitrag",
            description = "zzgl. pro Monat",
            isBeer = false,
            value = "5"
        ),
        Penalty(
            _id = "",
            name = "Getränke zur Besprechung",
            categoryName = "Sonstiges",
            description = "Mitzubringen in alphabetischer Reihenfolge nach dem Freitagstraining",
            isBeer = true,
            value = "1"
        )
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
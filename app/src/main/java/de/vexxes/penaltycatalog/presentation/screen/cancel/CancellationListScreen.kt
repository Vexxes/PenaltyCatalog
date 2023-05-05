package de.vexxes.penaltycatalog.presentation.screen.cancel

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import de.vexxes.penaltycatalog.R
import de.vexxes.penaltycatalog.component.GeneralTopBar
import de.vexxes.penaltycatalog.domain.enums.SearchAppBarState
import de.vexxes.penaltycatalog.domain.uievent.SearchUiEvent
import de.vexxes.penaltycatalog.domain.uistate.CancellationUiState
import de.vexxes.penaltycatalog.domain.uistate.SearchUiState
import de.vexxes.penaltycatalog.domain.uistate.cancellationUiStateExample1
import de.vexxes.penaltycatalog.domain.uistate.cancellationUiStateExample2
import de.vexxes.penaltycatalog.domain.uistate.cancellationUiStateExample3
import de.vexxes.penaltycatalog.ui.theme.PenaltyCatalogTheme
import de.vexxes.penaltycatalog.util.RequestState
import de.vexxes.penaltycatalog.viewmodels.CancellationViewModel

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CancellationListScreen(
    cancellationViewModel: CancellationViewModel,
    navigateToCancellationDetailScreen: (cancellationId: String) -> Unit,
    navigateToCancellationEditScreen: (cancellationId: String) -> Unit
) {
    val cancellationsUiStates by cancellationViewModel.cancellationUiStates
    val todayFilterSelected by cancellationViewModel.todayFilterSelected
    val requestState by cancellationViewModel.requestState
    val searchUiState by cancellationViewModel.searchUiState

    val refreshCancellations = { cancellationViewModel.getAllCancellations() }
    val refreshing = requestState is RequestState.Loading
    val pullRefreshState = rememberPullRefreshState(refreshing = refreshing, onRefresh = { refreshCancellations() })

    Box(modifier = Modifier.pullRefresh(pullRefreshState)) {
        CancellationListScaffold(
            searchUiState = searchUiState,
            onSearchTextChanged = { cancellationViewModel.onSearchUiEvent(SearchUiEvent.SearchTextChanged(it)) },
            onDefaultSearchClicked = { cancellationViewModel.onSearchUiEvent(SearchUiEvent.SearchAppBarStateChanged(SearchAppBarState.OPENED)) },
            onCloseClicked = {
                cancellationViewModel.onSearchUiEvent(SearchUiEvent.SearchAppBarStateChanged(SearchAppBarState.CLOSED))
                cancellationViewModel.onSearchUiEvent(SearchUiEvent.SearchTextChanged(""))
            },
            cancellationsUiStates = cancellationsUiStates,
            todayFilterSelected = todayFilterSelected,
            navigateToCancellationDetailScreen = navigateToCancellationDetailScreen,
            navigateToCancellationEditScreen = navigateToCancellationEditScreen,
            onTodayFilterChipClicked = { cancellationViewModel.onTodayFilterEvent() }
        )

        PullRefreshIndicator(refreshing = refreshing, state = pullRefreshState, modifier = Modifier.align(Alignment.TopCenter))
    }
}

@Composable
private fun CancellationListScaffold(
    searchUiState: SearchUiState,
    onSearchTextChanged: (String) -> Unit,
    onDefaultSearchClicked: () -> Unit,
    onCloseClicked: () -> Unit,
    cancellationsUiStates: List<CancellationUiState>,
    todayFilterSelected: Boolean,
    navigateToCancellationDetailScreen: (cancellationId: String) -> Unit,
    navigateToCancellationEditScreen: (cancellationId: String) -> Unit,
    onTodayFilterChipClicked: () -> Unit
) {
    Scaffold(
        topBar = {
            GeneralTopBar(
                searchAppBarState = searchUiState.searchAppBarState,
                searchTextState = searchUiState.searchText,
                onDefaultSearchClicked = onDefaultSearchClicked,
                onSearchTextChanged = onSearchTextChanged,
                onCloseClicked = onCloseClicked
            )
        },

        content = {
            Box(modifier = Modifier.padding(it)) {
                CancellationListContent(
                    cancellationUiStates = cancellationsUiStates,
                    todayFilterSelected = todayFilterSelected,
                    navigateToCancellationDetailScreen = navigateToCancellationDetailScreen,
                    onTodayFilterChipClicked = onTodayFilterChipClicked
                )
            }
        },

        floatingActionButton = {
            CancellationFab(navigateToCancellationEditScreen = navigateToCancellationEditScreen)
        }
    )
}

@Composable
private fun CancellationFab(
    navigateToCancellationEditScreen: (cancellationId: String) -> Unit
) {
    FloatingActionButton(
        onClick = {
            navigateToCancellationEditScreen("")
        }) {
        Icon(
            imageVector = Icons.Default.Add,
            contentDescription = stringResource(id = R.string.AddCancellation)
        )
    }
}

@Composable
@Preview(name = "Light Theme", showBackground = true)
@Preview(name = "Dark Theme", showBackground = true, uiMode = UI_MODE_NIGHT_YES)
private fun CancellationListScreenPreview() {
    PenaltyCatalogTheme {
        CancellationListScaffold(
            searchUiState = SearchUiState(),
            onSearchTextChanged = { },
            onDefaultSearchClicked = { },
            onCloseClicked = { },
            cancellationsUiStates = listOf(
                cancellationUiStateExample1(),
                cancellationUiStateExample2(),
                cancellationUiStateExample3()
            ),
            todayFilterSelected = false,
            navigateToCancellationDetailScreen = { },
            navigateToCancellationEditScreen = { },
            onTodayFilterChipClicked = { }
        )
    }
}

@Composable
@Preview(name = "Light Theme", showBackground = true)
@Preview(name = "Dark Theme", showBackground = true, uiMode = UI_MODE_NIGHT_YES)
private fun CancellationFabPreview() {
    PenaltyCatalogTheme {
        CancellationFab(
            navigateToCancellationEditScreen = { }
        )
    }
}
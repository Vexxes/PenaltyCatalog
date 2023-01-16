package de.vexxes.penaltycatalog.presentation.screen.penaltyHistory

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.Chip
import androidx.compose.material.ChipColors
import androidx.compose.material.ChipDefaults
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.filled.MoneyOff
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import de.vexxes.penaltycatalog.R
import de.vexxes.penaltycatalog.component.GeneralTopBar
import de.vexxes.penaltycatalog.domain.model.ApiResponse
import de.vexxes.penaltycatalog.domain.model.PenaltyReceived
import de.vexxes.penaltycatalog.domain.enums.SortOrder
import de.vexxes.penaltycatalog.domain.model.penaltyReceivedExample1
import de.vexxes.penaltycatalog.domain.model.penaltyReceivedExample2
import de.vexxes.penaltycatalog.domain.model.penaltyReceivedExample3
import de.vexxes.penaltycatalog.domain.uievent.SearchUiEvent
import de.vexxes.penaltycatalog.domain.uistate.SearchUiState
import de.vexxes.penaltycatalog.ui.theme.Typography
import de.vexxes.penaltycatalog.ui.theme.colorSchemeSegButtons
import de.vexxes.penaltycatalog.util.FilterPaidState
import de.vexxes.penaltycatalog.util.RequestState
import de.vexxes.penaltycatalog.util.SearchAppBarState
import de.vexxes.penaltycatalog.viewmodels.PenaltyHistoryViewModel
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun PenaltyHistoryListScreen(
    penaltyHistoryViewModel: PenaltyHistoryViewModel,
    navigateToPenaltyHistoryDetailScreen: (penaltyHistoryId: String) -> Unit,
    navigateToPenaltyHistoryEditScreen: (penaltyHistoryId: String) -> Unit
) {
    val penaltyHistory by penaltyHistoryViewModel.penaltyReceived
    val apiResponse by penaltyHistoryViewModel.lastResponse
    val searchUiState by penaltyHistoryViewModel.searchUiState
    
    val refreshPenaltyHistory = { penaltyHistoryViewModel.getAllPenaltyHistory() }
    val refreshing = penaltyHistoryViewModel.apiResponse.value is RequestState.Loading
    val pullRefreshState = rememberPullRefreshState(refreshing = refreshing, onRefresh = { refreshPenaltyHistory() })

    LaunchedEffect(key1 = true) {
        refreshPenaltyHistory()
    }

    Box(modifier = Modifier.pullRefresh(pullRefreshState)) {
        PenaltyHistoryListScaffold(
            searchUiState = searchUiState,
            onSearchTextChanged = { penaltyHistoryViewModel.onSearchUiEvent(SearchUiEvent.SearchTextChanged(it)) },
            onDefaultSearchClicked = { penaltyHistoryViewModel.onSearchUiEvent(SearchUiEvent.SearchAppBarStateChanged(SearchAppBarState.OPENED)) },
            onSortClicked = { /*TODO Implement order function with different sort orders like date, name, etc...*/},
            onCloseClicked = {
                penaltyHistoryViewModel.onSearchUiEvent(SearchUiEvent.SearchAppBarStateChanged(SearchAppBarState.CLOSED))
                penaltyHistoryViewModel.onSearchUiEvent(SearchUiEvent.SearchTextChanged(""))
            },
            penaltyReceived = penaltyHistory,
            apiResponse = apiResponse,
            resetApiResponse = { penaltyHistoryViewModel.resetLastResponse() },
            navigateToPenaltyHistoryDetailScreen = navigateToPenaltyHistoryDetailScreen,
            navigateToPenaltyHistoryEditScreen = navigateToPenaltyHistoryEditScreen
        )

        PullRefreshIndicator(refreshing = refreshing, state = pullRefreshState, modifier = Modifier.align(Alignment.TopCenter))
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PenaltyHistoryListScaffold(
    searchUiState: SearchUiState,
    onSearchTextChanged: (String) -> Unit,
    onDefaultSearchClicked: () -> Unit,
    onSortClicked: (SortOrder) -> Unit,
    onCloseClicked: () -> Unit,
    penaltyReceived: List<PenaltyReceived>,
    apiResponse: ApiResponse,
    resetApiResponse: () -> Unit,
    navigateToPenaltyHistoryDetailScreen: (String) -> Unit,
    navigateToPenaltyHistoryEditScreen: (String) -> Unit
) {

    var filterPaidState by remember { mutableStateOf(FilterPaidState.OFF) }

    Scaffold(
        topBar = {
            GeneralTopBar(
                searchAppBarState = searchUiState.searchAppBarState,
                searchTextState = searchUiState.searchText,
                onDefaultSearchClicked = onDefaultSearchClicked,
                onSortClicked = onSortClicked,
                onSearchTextChanged = onSearchTextChanged,
                onCloseClicked = onCloseClicked
            )
        },

        content = {

            Box(modifier = Modifier.padding(it)) {
                Column {
                    PenaltyHistoryChips(
                        filterPaidState = filterPaidState,
                        onFilterStateChanged = { filterPaidStateNew ->
                            filterPaidState = filterPaidStateNew
                        }
                    )
                    PenaltyHistoryListContent(
                        penaltyReceived = penaltyReceived,
                        filterPaidState = filterPaidState,
                        navigateToPenaltyHistoryDetailScreen = navigateToPenaltyHistoryDetailScreen
                    )
                }

            }
        },

        floatingActionButton = {
            PenaltyHistoryFab(navigateToPenaltyHistoryEditScreen = navigateToPenaltyHistoryEditScreen)
        },

        snackbarHost = {
            PenaltyHistoryListSnackbar(
                apiResponse = apiResponse,
                resetApiResponse = resetApiResponse
            )
        }
    )
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun PenaltyHistoryChips(
    filterPaidState: FilterPaidState,
    onFilterStateChanged: (FilterPaidState) -> Unit
) {
    val chipColorsPaid = ChipDefaults.chipColors(
        backgroundColor = colorSchemeSegButtons().backgroundPaid,
        leadingIconContentColor = colorSchemeSegButtons().foregroundPaid,
        contentColor = colorSchemeSegButtons().foregroundPaid
    )
    val chipColorsNotPaid = ChipDefaults.chipColors(
        backgroundColor = colorSchemeSegButtons().backgroundNotPaid,
        leadingIconContentColor = colorSchemeSegButtons().foregroundNotPaid,
        contentColor = colorSchemeSegButtons().foregroundNotPaid
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface
        ),
        horizontalArrangement = Arrangement.Center,
    ) {
        PenaltyHistoryChip(
            imageVector = Icons.Default.AttachMoney,
            text = stringResource(id = R.string.Paid),
            onClicked = {
                if (filterPaidState == FilterPaidState.PAID)
                    onFilterStateChanged(FilterPaidState.OFF)
                else
                    onFilterStateChanged(FilterPaidState.PAID)
            },
            chipColors = if (filterPaidState == FilterPaidState.PAID) chipColorsPaid else ChipDefaults.chipColors()
        )

        PenaltyHistoryChip(
            imageVector = Icons.Default.MoneyOff,
            text = stringResource(id = R.string.NotPaid),
            onClicked = {
                if (filterPaidState == FilterPaidState.NOT_PAID)
                    onFilterStateChanged(FilterPaidState.OFF)
                else
                    onFilterStateChanged(FilterPaidState.NOT_PAID)
            },
            chipColors = if (filterPaidState == FilterPaidState.NOT_PAID) chipColorsNotPaid else ChipDefaults.chipColors()
        )
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun PenaltyHistoryChip(
    imageVector: ImageVector,
    text: String,
    onClicked: () -> Unit,
    chipColors: ChipColors
) {
    Chip(
        modifier = Modifier
            .padding(8.dp),
        onClick = { onClicked() },
        shape = MaterialTheme.shapes.large.copy(CornerSize(percent = 20)),
        leadingIcon = {
            Icon(
                imageVector = imageVector,
                contentDescription = "",
                tint = chipColors.leadingIconContentColor(enabled = true).value
            )
        },
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline),
        colors = chipColors
    ) {
        Text(
            text = text,
            style = Typography.labelLarge,
            color = chipColors.contentColor(enabled = true).value
        )
    }
}

@Composable
fun PenaltyHistoryFab(
    navigateToPenaltyHistoryEditScreen: (String) -> Unit
) {
    FloatingActionButton(
        onClick = {
            navigateToPenaltyHistoryEditScreen("-1")
        }) {
        Icon(
            imageVector = Icons.Default.Add,
            contentDescription = stringResource(id = R.string.AddPenalty)
        )
    }
}

@Composable
fun PenaltyHistoryListSnackbar(
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

@Composable
@Preview(showBackground = true)
private fun PenaltyHistoryListScreenPreview() {
    PenaltyHistoryListScaffold(
        searchUiState = SearchUiState(),
        onSearchTextChanged = { },
        onDefaultSearchClicked = { },
        onSortClicked = { },
        onCloseClicked = { },
        penaltyReceived = listOf(
            penaltyReceivedExample1(),
            penaltyReceivedExample2(),
            penaltyReceivedExample3()
        ),
        apiResponse = ApiResponse(),
        resetApiResponse = { },
        navigateToPenaltyHistoryDetailScreen = { },
        navigateToPenaltyHistoryEditScreen = { }
    )
}

@Composable
@Preview
fun PenaltyHistoryFabPreview() {
    PenaltyHistoryFab(
        navigateToPenaltyHistoryEditScreen = { }
    )
}

@Composable
@Preview(showBackground = true)
private fun PenaltyHistoryChipsPreview() {
    Column {
        PenaltyHistoryChips(
            filterPaidState = FilterPaidState.OFF,
            onFilterStateChanged = { }
        )

        PenaltyHistoryChips(
            filterPaidState = FilterPaidState.PAID,
            onFilterStateChanged = { }
        )

        PenaltyHistoryChips(
            filterPaidState = FilterPaidState.NOT_PAID,
            onFilterStateChanged = { }
        )
    }
}
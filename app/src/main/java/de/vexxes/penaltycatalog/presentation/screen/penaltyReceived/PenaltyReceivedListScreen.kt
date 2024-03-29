package de.vexxes.penaltycatalog.presentation.screen.penaltyReceived

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.filled.MoneyOff
import androidx.compose.material.icons.filled.Sort
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import de.vexxes.penaltycatalog.domain.enums.FilterPaidState
import de.vexxes.penaltycatalog.domain.enums.PenaltyReceivedSort
import de.vexxes.penaltycatalog.domain.enums.SearchAppBarState
import de.vexxes.penaltycatalog.domain.uievent.SearchUiEvent
import de.vexxes.penaltycatalog.domain.uistate.PenaltyReceivedUiState
import de.vexxes.penaltycatalog.domain.uistate.SearchUiState
import de.vexxes.penaltycatalog.domain.uistate.penaltyReceivedUiStateExample1
import de.vexxes.penaltycatalog.domain.uistate.penaltyReceivedUiStateExample2
import de.vexxes.penaltycatalog.domain.uistate.penaltyReceivedUiStateExample3
import de.vexxes.penaltycatalog.ui.theme.PenaltyCatalogTheme
import de.vexxes.penaltycatalog.ui.theme.Typography
import de.vexxes.penaltycatalog.ui.theme.colorSchemeSegButtons
import de.vexxes.penaltycatalog.util.RequestState
import de.vexxes.penaltycatalog.viewmodels.PenaltyReceivedViewModel

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun PenaltyReceivedListScreen(
    penaltyReceivedViewModel: PenaltyReceivedViewModel,
    navigateToPenaltyReceivedDetailScreen: (penaltyHistoryId: String) -> Unit,
    navigateToPenaltyReceivedEditScreen: (penaltyHistoryId: String) -> Unit
) {
    val penaltyReceivedUiStates by penaltyReceivedViewModel.penaltyReceivedUiStates
    val requestState by penaltyReceivedViewModel.requestState
    val searchUiState by penaltyReceivedViewModel.searchUiState
    
    val refreshPenaltyReceived = { penaltyReceivedViewModel.updateLists() }
    val refreshing = requestState is RequestState.Loading
    val pullRefreshState = rememberPullRefreshState(refreshing = refreshing, onRefresh = { refreshPenaltyReceived() })

    Box(modifier = Modifier.pullRefresh(pullRefreshState)) {
        PenaltyReceivedListScaffold(
            searchUiState = searchUiState,
            onSearchTextChanged = { penaltyReceivedViewModel.onSearchUiEvent(SearchUiEvent.SearchTextChanged(it)) },
            onDefaultSearchClicked = { penaltyReceivedViewModel.onSearchUiEvent(SearchUiEvent.SearchAppBarStateChanged(SearchAppBarState.OPENED)) },
            onCloseClicked = {
                penaltyReceivedViewModel.onSearchUiEvent(SearchUiEvent.SearchAppBarStateChanged(SearchAppBarState.CLOSED))
                penaltyReceivedViewModel.onSearchUiEvent(SearchUiEvent.SearchTextChanged(""))
            },
            onSortClicked = {
                penaltyReceivedViewModel.onSortEvent(it)
            },
            penaltyReceivedUiStateList = penaltyReceivedUiStates,
            navigateToPenaltyReceivedDetailScreen = navigateToPenaltyReceivedDetailScreen,
            navigateToPenaltyReceivedEditScreen = navigateToPenaltyReceivedEditScreen
        )

        PullRefreshIndicator(refreshing = refreshing, state = pullRefreshState, modifier = Modifier.align(Alignment.TopCenter))
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun PenaltyReceivedListScaffold(
    searchUiState: SearchUiState,
    onSearchTextChanged: (String) -> Unit,
    onDefaultSearchClicked: () -> Unit,
    onSortClicked: (PenaltyReceivedSort) -> Unit,
    onCloseClicked: () -> Unit,
    penaltyReceivedUiStateList: List<PenaltyReceivedUiState>,
    navigateToPenaltyReceivedDetailScreen: (String) -> Unit,
    navigateToPenaltyReceivedEditScreen: (String) -> Unit
) {

    var filterPaidState by remember { mutableStateOf(FilterPaidState.OFF) }

    Scaffold(
        topBar = {
            GeneralTopBar(
                searchAppBarState = searchUiState.searchAppBarState,
                searchTextState = searchUiState.searchText,
                onDefaultSearchClicked = onDefaultSearchClicked,
                onSearchTextChanged = onSearchTextChanged,
                onCloseClicked = onCloseClicked,
                sortIcon = {
                    PenaltyReceivedSortIcon(onSortClicked = onSortClicked)
                }
            )
        },

        content = {
            Box(modifier = Modifier.padding(it)) {
                Column {
                    PenaltyReceivedChips(
                        filterPaidState = filterPaidState,
                        onFilterStateChanged = { filterPaidStateNew ->
                            filterPaidState = filterPaidStateNew
                        }
                    )
                    PenaltyReceivedListContent(
                        penaltyReceivedUiStateList = penaltyReceivedUiStateList,
                        filterPaidState = filterPaidState,
                        navigateToPenaltyReceivedDetailScreen = navigateToPenaltyReceivedDetailScreen
                    )
                }
            }
        },

        floatingActionButton = {
            PenaltyReceivedFab(navigateToPenaltyReceivedEditScreen = navigateToPenaltyReceivedEditScreen)
        }
    )
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun PenaltyReceivedChips(
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
            .background(
                MaterialTheme.colorScheme.surface
            ),
        horizontalArrangement = Arrangement.Center,
    ) {
        PenaltyReceivedChip(
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

        PenaltyReceivedChip(
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
private fun PenaltyReceivedChip(
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
private fun PenaltyReceivedSortIcon(
    onSortClicked: (PenaltyReceivedSort) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    DropdownMenu(
        expanded = expanded,
        onDismissRequest = { expanded = false }
    ) {
        PenaltyReceivedSort.values().forEach { penaltyReceivedSort ->
            DropdownMenuItem(
                onClick = { onSortClicked(penaltyReceivedSort) },
                leadingIcon = { Icon(imageVector = penaltyReceivedSort.image, contentDescription = "")},
                text = { Text(text = stringResource(id = penaltyReceivedSort.nameId)) }
            )
        }
    }

    IconButton(
        onClick = { expanded = true }
    ) {
        Icon(
            imageVector = Icons.Default.Sort,
            contentDescription = stringResource(id = R.string.SortTopBar)
        )
    }
}

@Composable
private fun PenaltyReceivedFab(
    navigateToPenaltyReceivedEditScreen: (String) -> Unit
) {
    FloatingActionButton(
        onClick = {
            navigateToPenaltyReceivedEditScreen("")
        }) {
        Icon(
            imageVector = Icons.Default.Add,
            contentDescription = stringResource(id = R.string.AddPenalty)
        )
    }
}

@Composable
@Preview(name = "Light Theme", showBackground = true)
@Preview(name = "Dark Theme", showBackground = true, uiMode = UI_MODE_NIGHT_YES)
private fun PenaltyReceivedListScreenPreview() {
    PenaltyCatalogTheme {
        PenaltyReceivedListScaffold(
            searchUiState = SearchUiState(),
            onSearchTextChanged = { },
            onDefaultSearchClicked = { },
            onSortClicked = { },
            onCloseClicked = { },
            penaltyReceivedUiStateList = listOf(
                penaltyReceivedUiStateExample1(),
                penaltyReceivedUiStateExample2(),
                penaltyReceivedUiStateExample3()
            ),
            navigateToPenaltyReceivedDetailScreen = { },
            navigateToPenaltyReceivedEditScreen = { }
        )
    }
}

@Composable
@Preview(name = "Light Theme", showBackground = true)
@Preview(name = "Dark Theme", showBackground = true, uiMode = UI_MODE_NIGHT_YES)
private fun PenaltyReceivedFabPreview() {
    PenaltyCatalogTheme {
        PenaltyReceivedFab(
            navigateToPenaltyReceivedEditScreen = { }
        )
    }
}

@Composable
@Preview(name = "Light Theme", showBackground = true)
@Preview(name = "Dark Theme", showBackground = true, uiMode = UI_MODE_NIGHT_YES)
private fun PenaltyReceivedChipsPreview() {
    PenaltyCatalogTheme {
        Column {
            PenaltyReceivedChips(
                filterPaidState = FilterPaidState.OFF,
                onFilterStateChanged = { }
            )

            PenaltyReceivedChips(
                filterPaidState = FilterPaidState.PAID,
                onFilterStateChanged = { }
            )

            PenaltyReceivedChips(
                filterPaidState = FilterPaidState.NOT_PAID,
                onFilterStateChanged = { }
            )
        }
    }
}
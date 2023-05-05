package de.vexxes.penaltycatalog.presentation.screen.events

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import de.vexxes.penaltycatalog.R
import de.vexxes.penaltycatalog.component.GeneralTopBar
import de.vexxes.penaltycatalog.data.preferences.EventPreferences
import de.vexxes.penaltycatalog.domain.enums.SearchAppBarState
import de.vexxes.penaltycatalog.domain.model.Event
import de.vexxes.penaltycatalog.domain.model.eventExample1
import de.vexxes.penaltycatalog.domain.model.eventExample2
import de.vexxes.penaltycatalog.domain.model.eventExample3
import de.vexxes.penaltycatalog.domain.uievent.SearchUiEvent
import de.vexxes.penaltycatalog.domain.uistate.SearchUiState
import de.vexxes.penaltycatalog.ui.theme.PenaltyCatalogTheme
import de.vexxes.penaltycatalog.util.RequestState
import de.vexxes.penaltycatalog.viewmodels.EventViewModel
import kotlinx.coroutines.runBlocking

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun EventListScreen(
    eventViewModel: EventViewModel,
    navigateToEventDetailScreen: (eventId: String) -> Unit,
    navigateToEventEditScreen: (eventId: String) -> Unit
) {
    val events by eventViewModel.events
    val requestState by eventViewModel.requestState
    val searchUiState by eventViewModel.searchUiState

    val currentContext = LocalContext.current
    val eventPreferences = EventPreferences(currentContext)
    val showPastEvents = eventPreferences.showPastEvents.collectAsState(initial = false).value

    val refreshEvents = { eventViewModel.getAllEvents() }
    val refreshing = requestState is RequestState.Loading
    val pullRefreshState =
        rememberPullRefreshState(refreshing = refreshing, onRefresh = { refreshEvents() })

    Box(modifier = Modifier.pullRefresh(pullRefreshState)) {
        EventListScaffold(
            searchUiState = searchUiState,
            onSearchTextChanged = {
                eventViewModel.onSearchUiEvent(
                    SearchUiEvent.SearchTextChanged(
                        it
                    )
                )
            },
            onDefaultSearchClicked = {
                eventViewModel.onSearchUiEvent(
                    SearchUiEvent.SearchAppBarStateChanged(
                        SearchAppBarState.OPENED
                    )
                )
            },
            onCloseClicked = {
                eventViewModel.onSearchUiEvent(
                    SearchUiEvent.SearchAppBarStateChanged(
                        SearchAppBarState.CLOSED
                    )
                )
                eventViewModel.onSearchUiEvent(SearchUiEvent.SearchTextChanged(""))
            },
            onShowPastEventsClicked = { runBlocking { eventPreferences.saveShowPastEvents(!showPastEvents, currentContext) }  },
            events = events,
            showPastEvents = showPastEvents,
            navigateToEventDetailScreen = navigateToEventDetailScreen,
            navigateToEventEditScreen = navigateToEventEditScreen
        )

        PullRefreshIndicator(
            refreshing = refreshing,
            state = pullRefreshState,
            modifier = Modifier.align(Alignment.TopCenter)
        )
    }
}

// TODO Create on sort function
@Composable
private fun EventListScaffold(
    searchUiState: SearchUiState,
    onSearchTextChanged: (String) -> Unit,
    onDefaultSearchClicked: () -> Unit,
    onCloseClicked: () -> Unit,
    onShowPastEventsClicked: () -> Unit,
    events: List<Event>,
    showPastEvents: Boolean,
    navigateToEventDetailScreen: (String) -> Unit,
    navigateToEventEditScreen: (String) -> Unit
) {
    Scaffold(
        topBar = {
            GeneralTopBar(
                searchAppBarState = searchUiState.searchAppBarState,
                searchTextState = searchUiState.searchText,
                onDefaultSearchClicked = onDefaultSearchClicked,
                onSearchTextChanged = onSearchTextChanged,
                onCloseClicked = onCloseClicked,
                moreVertIcon = {
                    EventMoreVertIcon(
                        showPastEvents = showPastEvents,
                        onShowPastEventsClicked = onShowPastEventsClicked
                    )
                }
            )
        },

        content = {
            Box(modifier = Modifier.padding(it)) {
                EventListContent(
                    events = events,
                    showPastEvents = showPastEvents,
                    navigateToEventDetailScreen = navigateToEventDetailScreen
                )
            }
        },

        floatingActionButton = {
            EventFab(navigateToEventEditScreen = navigateToEventEditScreen)
        }
    )
}

@Composable
private fun EventMoreVertIcon(
    showPastEvents: Boolean,
    onShowPastEventsClicked: () -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    DropdownMenu(
        expanded = expanded,
        onDismissRequest = { expanded = false }
    ) {
        DropdownMenuItem(
            onClick = { onShowPastEventsClicked() },
            leadingIcon = { if(showPastEvents) Icon(imageVector = Icons.Outlined.Check, contentDescription = "") },
            text = { Text(text = stringResource(R.string.ShowPastEvents)) }
        )
    }

    IconButton(
        onClick = { expanded = true }
    ) {
        Icon(
            imageVector = Icons.Default.MoreVert,
            contentDescription = stringResource(id = R.string.MoreVertTopBar)
        )
    }
}

@Composable
private fun EventFab(
    navigateToEventEditScreen: (eventId: String) -> Unit
) {
    FloatingActionButton(
        onClick = {
            navigateToEventEditScreen("")
        }) {
        Icon(
            imageVector = Icons.Default.Add,
            contentDescription = stringResource(id = R.string.AddEvent)
        )
    }
}

@Composable
@Preview(name = "Light Theme", showBackground = true)
@Preview(name = "Dark Theme", showBackground = true, uiMode = UI_MODE_NIGHT_YES)
private fun EventListScreenPreview() {
    PenaltyCatalogTheme {
        EventListScaffold(
            searchUiState = SearchUiState(),
            onSearchTextChanged = { },
            onDefaultSearchClicked = { },
            onCloseClicked = { },
            onShowPastEventsClicked = { },
            events = listOf(
                eventExample1(),
                eventExample2(),
                eventExample3()
            ),
            showPastEvents = false,
            navigateToEventDetailScreen = { },
            navigateToEventEditScreen = { }
        )
    }
}

@Composable
@Preview(name = "Light Theme", showBackground = true)
@Preview(name = "Dark Theme", showBackground = true, uiMode = UI_MODE_NIGHT_YES)
private fun EventFabPreview() {
    PenaltyCatalogTheme {
        EventFab(
            navigateToEventEditScreen = { }
        )
    }
}
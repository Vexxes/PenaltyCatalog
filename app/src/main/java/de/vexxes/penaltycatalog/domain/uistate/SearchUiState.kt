package de.vexxes.penaltycatalog.domain.uistate

import de.vexxes.penaltycatalog.domain.enums.SortOrder
import de.vexxes.penaltycatalog.util.SearchAppBarState

data class SearchUiState(
    val searchAppBarState: SearchAppBarState = SearchAppBarState.CLOSED,
    var searchText: String = ""
)
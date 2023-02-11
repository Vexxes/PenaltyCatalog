package de.vexxes.penaltycatalog.domain.uistate

import de.vexxes.penaltycatalog.domain.enums.SearchAppBarState

data class SearchUiState(
    val searchAppBarState: SearchAppBarState = SearchAppBarState.CLOSED,
    var searchText: String = ""
)
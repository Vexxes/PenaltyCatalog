package de.vexxes.penaltycatalog.domain.uievent

import de.vexxes.penaltycatalog.domain.enums.SearchAppBarState

sealed class SearchUiEvent {
    data class SearchAppBarStateChanged(val searchAppBarState: SearchAppBarState): SearchUiEvent()
    data class SearchTextChanged(val searchText: String): SearchUiEvent()
}
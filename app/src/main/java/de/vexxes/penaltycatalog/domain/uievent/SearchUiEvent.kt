package de.vexxes.penaltycatalog.domain.uievent

import de.vexxes.penaltycatalog.domain.enums.SortOrder
import de.vexxes.penaltycatalog.util.SearchAppBarState

sealed class SearchUiEvent {
    data class SortOrderChanged(val sortOrder: SortOrder): SearchUiEvent()
    data class SearchAppBarStateChanged(val searchAppBarState: SearchAppBarState): SearchUiEvent()
    data class SearchTextChanged(val searchText: String): SearchUiEvent()
}
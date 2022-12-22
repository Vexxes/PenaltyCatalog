package de.vexxes.penaltycatalog.domain.uistate

import de.vexxes.penaltycatalog.domain.model.SortOrder
import de.vexxes.penaltycatalog.util.SearchAppBarState

data class SearchUiState(
    val sortOrder: SortOrder = SortOrder.ASCENDING,
    val searchAppBarState: SearchAppBarState = SearchAppBarState.CLOSED,
    var searchText: String = ""
)
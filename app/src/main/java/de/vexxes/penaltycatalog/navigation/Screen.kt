package de.vexxes.penaltycatalog.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.ui.graphics.vector.ImageVector

sealed class ScreenNavigation(val route: String, var name: String, val iconSelected: ImageVector, val iconUnselected: ImageVector) {

    object Penalties : ScreenNavigation(
        route = "penalties_screen",
        name = "",
        iconSelected = Icons.Filled.Article,
        iconUnselected = Icons.Outlined.Article
    )

    object Players : ScreenNavigation(
        route = "player_screen",
        name = "",
        iconSelected = Icons.Filled.Groups,
        iconUnselected = Icons.Outlined.Groups
    )

    object PenaltyHistory : ScreenNavigation(
        route = "penalty_history_screen",
        name = "",
        iconSelected = Icons.Filled.History,
        iconUnselected = Icons.Outlined.History
    )

    object Cancellations : ScreenNavigation(
        route = "cancellations_screen",
        name = "",
        iconSelected = Icons.Filled.EventBusy,
        iconUnselected = Icons.Outlined.EventBusy
    )

    object Events : ScreenNavigation(
        route = "events_screen",
        name = "",
        iconSelected = Icons.Filled.CalendarMonth,
        iconUnselected = Icons.Outlined.CalendarMonth
    )

}

sealed class Screen(val route: String, val argument: String = "") {
    object PenaltyDetail: Screen(
        route = "penalty_detail_screen",
        argument = "/{penaltyId}"
    )

    object PenaltyEdit: Screen(
        route = "penalty_edit_screen",
        argument = "/{penaltyId}"
    )
    object PlayerDetail: Screen(
        route = "player_detail_screen",
        argument = "/{playerId}"
    )
    object PlayerEdit: Screen(
        route = "player_edit_screen",
        argument = "/{playerId}"
    )

    object PenaltyHistoryDetail: Screen(
        route = "penaltyHistory_detail_screen",
        argument = "/{penaltyHistoryId}"
    )
    object PenaltyHistoryEdit: Screen(
        route = "penaltyHistory_edit_screen",
        argument = "/{penaltyHistoryId}"
    )
}
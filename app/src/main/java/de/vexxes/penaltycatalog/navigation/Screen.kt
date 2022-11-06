package de.vexxes.penaltycatalog.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(val route: String, var name: String, val iconSelected: ImageVector, val iconUnselected: ImageVector) {

    object Penalties : Screen(
        route = "penalties_screen",
        name = "",
        iconSelected = Icons.Filled.Article,
        iconUnselected = Icons.Outlined.Article
    )

    object Players : Screen(
        route = "player_screen",
        name = "",
        iconSelected = Icons.Filled.Groups,
        iconUnselected = Icons.Outlined.Groups
    )

    object PenaltyHistory : Screen(
        route = "penalty_history_screen",
        name = "",
        iconSelected = Icons.Filled.History,
        iconUnselected = Icons.Outlined.History
    )

    object Cancellations : Screen(
        route = "cancellations_screen",
        name = "",
        iconSelected = Icons.Filled.EventBusy,
        iconUnselected = Icons.Outlined.EventBusy
    )

    object Events : Screen(
        route = "events_screen",
        name = "",
        iconSelected = Icons.Filled.CalendarMonth,
        iconUnselected = Icons.Outlined.CalendarMonth
    )

}
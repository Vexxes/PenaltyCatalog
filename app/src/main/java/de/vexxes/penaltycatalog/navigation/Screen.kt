package de.vexxes.penaltycatalog.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.ui.graphics.vector.ImageVector
import de.vexxes.penaltycatalog.util.CANCELLATION_ID
import de.vexxes.penaltycatalog.util.EVENT_ID
import de.vexxes.penaltycatalog.util.PENALTY_RECEIVED_ID
import de.vexxes.penaltycatalog.util.PENALTY_TYPE_ID
import de.vexxes.penaltycatalog.util.PLAYER_ID

sealed class Screen(val route: String, var name: String = "", val iconSelected: ImageVector? = null, val iconUnselected: ImageVector? = null) {

    object Penalties : Screen(
        route = "penalties_screen",
        name = "",
        iconSelected = Icons.Filled.Article,
        iconUnselected = Icons.Outlined.Article
    )

    object PenaltyDetail: Screen(
        route = "penalty_detail_screen/$PENALTY_TYPE_ID={$PENALTY_TYPE_ID}"
    ) {
        fun passPenaltyTypeId(penaltyTypeId: String) = "penalty_detail_screen/$PENALTY_TYPE_ID=$penaltyTypeId"
    }

    object PenaltyEdit: Screen(
        route = "penalty_edit_screen?$PENALTY_TYPE_ID={$PENALTY_TYPE_ID}"
    ) {
        fun passPenaltyTypeId(penaltyTypeId: String) = "penalty_edit_screen?$PENALTY_TYPE_ID=$penaltyTypeId"
    }

    object Players : Screen(
        route = "player_screen",
        name = "",
        iconSelected = Icons.Filled.Groups,
        iconUnselected = Icons.Outlined.Groups
    )

    object PlayerDetail: Screen(
        route = "player_detail_screen/$PLAYER_ID={$PLAYER_ID}"
    ) {
        fun passPlayerId(playerId: String) = "player_detail_screen/$PLAYER_ID=$playerId"
    }

    object PlayerEdit: Screen(
        route = "player_edit_screen?$PLAYER_ID={$PLAYER_ID}"
    ) {
        fun passPlayerId(playerId: String) = "player_edit_screen?$PLAYER_ID=$playerId"
    }

    object PenaltyReceived : Screen(
        route = "penalty_received_screen",
        name = "",
        iconSelected = Icons.Filled.History,
        iconUnselected = Icons.Outlined.History
    )

    object PenaltyReceivedDetail: Screen(
        route = "penaltyReceived_detail_screen/$PENALTY_RECEIVED_ID={$PENALTY_RECEIVED_ID}",
    ) {
        fun passPenaltyReceivedId(penaltyReceivedId: String) = "penaltyReceived_detail_screen/$PENALTY_RECEIVED_ID=$penaltyReceivedId"
    }
    object PenaltyReceivedEdit: Screen(
        route = "penaltyReceived_edit_screen?$PENALTY_RECEIVED_ID={$PENALTY_RECEIVED_ID}"
    ) {
        fun passPenaltyReceivedId(penaltyReceivedId: String) = "penaltyReceived_edit_screen?$PENALTY_RECEIVED_ID=$penaltyReceivedId"
    }

    object Cancellations : Screen(
        route = "cancellation_screen",
        name = "",
        iconSelected = Icons.Filled.EventBusy,
        iconUnselected = Icons.Outlined.EventBusy
    )

    object CancellationDetail: Screen(
        route = "cancellation_detail_screen/$CANCELLATION_ID={$CANCELLATION_ID}"
    ) {
        fun passCancellationId(cancellationId: String) = "cancellation_detail_screen/$CANCELLATION_ID=$cancellationId"
    }

    object CancellationEdit: Screen(
        route = "cancellation_edit_screen?$CANCELLATION_ID={$CANCELLATION_ID}"
    ) {
        fun passCancellationId(cancellationId: String) = "cancellation_edit_screen?$CANCELLATION_ID=$cancellationId"
    }

    object Events : Screen(
        route = "event_screen",
        name = "",
        iconSelected = Icons.Filled.CalendarMonth,
        iconUnselected = Icons.Outlined.CalendarMonth
    )

    object EventDetail: Screen(
        route = "event_detail_screen/$EVENT_ID={$EVENT_ID}"
    ) {
        fun passEventId(eventId: String) = "event_detail_screen/$EVENT_ID=$eventId"
    }

    object EventEdit: Screen(
        route = "event_edit_screen?$EVENT_ID={$EVENT_ID}"
    ) {
        fun passEventId(eventId: String) = "event_edit_screen?$EVENT_ID=$eventId"
    }
}
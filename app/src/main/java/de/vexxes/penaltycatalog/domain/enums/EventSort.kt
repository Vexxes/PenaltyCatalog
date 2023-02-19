package de.vexxes.penaltycatalog.domain.enums

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.North
import androidx.compose.material.icons.filled.South
import androidx.compose.ui.graphics.vector.ImageVector
import de.vexxes.penaltycatalog.R

enum class EventSort(
    val nameId: Int,
    val image: ImageVector
) {
    /* TODO: Extend sort abilities */
    START_OF_EVENT_DESCENDING(R.string.StartOfEvent, Icons.Default.South),
    START_OF_EVENT_ASCENDING(R.string.StartOfEvent, Icons.Default.North)
}
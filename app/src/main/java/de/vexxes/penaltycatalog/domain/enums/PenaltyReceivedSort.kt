package de.vexxes.penaltycatalog.domain.enums

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.North
import androidx.compose.material.icons.filled.South
import androidx.compose.ui.graphics.vector.ImageVector
import de.vexxes.penaltycatalog.R

enum class PenaltyReceivedSort(
    val nameId: Int,
    val image: ImageVector
) {
    TIME_OF_PENALTY_ASCENDING(R.string.TimeOfPenalty, Icons.Default.North),
    TIME_OF_PENALTY_DESCENDING(R.string.TimeOfPenalty, Icons.Default.South),
    NAME_ASCENDING(R.string.Player, Icons.Default.North),
    NAME_DESCENDING(R.string.Player, Icons.Default.South)
}
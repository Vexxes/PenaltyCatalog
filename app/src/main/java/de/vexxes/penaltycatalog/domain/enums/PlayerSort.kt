package de.vexxes.penaltycatalog.domain.enums

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.North
import androidx.compose.material.icons.filled.South
import androidx.compose.ui.graphics.vector.ImageVector
import de.vexxes.penaltycatalog.R

enum class PlayerSort(
    val nameId: Int,
    val image: ImageVector
) {
    NUMBER_ASCENDING(R.string.Number, Icons.Default.North),
    NUMBER_DESCENDING(R.string.Number, Icons.Default.South),
    NAME_ASCENDING(R.string.Player, Icons.Default.North),
    NAME_DESCENDING(R.string.Player, Icons.Default.South),
    GOALS_ASCENDING(R.string.Goals, Icons.Default.North),
    GOALS_DESCENDING(R.string.Goals, Icons.Default.South)
}

/*
sealed class Sort {
    data class PlayerNameAscending(val nameId: Int = R.string.Player, val image: ImageVector = Icons.Default.North): Sort()

}*/

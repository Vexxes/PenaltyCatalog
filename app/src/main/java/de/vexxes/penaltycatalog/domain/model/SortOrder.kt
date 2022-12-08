package de.vexxes.penaltycatalog.domain.model

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.North
import androidx.compose.material.icons.filled.South
import androidx.compose.ui.graphics.vector.ImageVector

enum class SortOrder(val imageVector: ImageVector) {
    ASCENDING(imageVector = Icons.Default.North),
    DESCENDING(imageVector = Icons.Default.South);

}

fun SortOrder.toValue(): Int {
    return if(this == SortOrder.ASCENDING) 1 else -1
}
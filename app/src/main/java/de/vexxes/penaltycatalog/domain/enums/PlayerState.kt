package de.vexxes.penaltycatalog.domain.enums

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.QuestionMark
import androidx.compose.material.icons.filled.SportsBar
import androidx.compose.material.icons.filled.ThumbDown
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import de.vexxes.penaltycatalog.ui.theme.Blue40
import de.vexxes.penaltycatalog.ui.theme.Green70
import de.vexxes.penaltycatalog.ui.theme.NeutralVariant30
import de.vexxes.penaltycatalog.ui.theme.Red80
import de.vexxes.penaltycatalog.ui.theme.Yellow50

enum class PlayerState(
    val icon: ImageVector,
    val tintColor: Color
) {
    UNDEFINED(Icons.Default.QuestionMark, NeutralVariant30),
    PRESENT(Icons.Default.ThumbUp, Green70),
    CANCELED(Icons.Default.ThumbDown, Yellow50),
    PAID_BEER(Icons.Default.SportsBar, Blue40),
    NOT_PRESENT(Icons.Default.ThumbDown, Red80)
}
package de.vexxes.penaltycatalog.domain.enums

import de.vexxes.penaltycatalog.R
import kotlinx.serialization.Serializable

@Serializable
enum class EventType(
    val nameId: Int
) {
    TRAINING(R.string.Training),
    GAME(R.string.Game),
    TESTGAME(R.string.TestGame),
    MISCELLANEOUS(R.string.Miscellaneous)
}
package de.vexxes.penaltycatalog.domain.enums

import de.vexxes.penaltycatalog.R

enum class EventType(
    val nameId: Int
) {
    TRAINING(R.string.Training),
    GAME(R.string.Game),
    TESTGAME(R.string.TestGame),
    MISCELLANEOUS(R.string.Miscellaneous)
}
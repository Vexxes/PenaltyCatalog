package de.vexxes.penaltycatalog.domain.model

import kotlinx.serialization.Serializable
import java.time.LocalDate

@Serializable
data class PenaltyHistory(
    val _id: String = "",
    val penaltyName: String = "",
    val playerName: String = "",
    val penaltyValue: String = "",
    val penaltyIsBeer: Boolean = false,
    val timeOfPenalty: String = "",
    val penaltyPaid: Boolean = false
)

fun penaltyHistoryExample1(): PenaltyHistory {
    return PenaltyHistory(
        _id = "",
        penaltyName = "Getränke zur Besprechung",
        playerName = "Mustermann, Max",
        penaltyValue = "1",
        penaltyIsBeer = true,
        timeOfPenalty = LocalDate.now().toString()
    )
}

fun penaltyHistoryExample2(): PenaltyHistory {
    return PenaltyHistory(
        _id = "",
        penaltyName = "Verspätete Zahlung des Monatsbeitrag",
        playerName = "Mustermann, Max",
        penaltyValue = "500",
        penaltyIsBeer = false,
        timeOfPenalty = LocalDate.now().toString()
    )
}

fun penaltyHistoryExample3(): PenaltyHistory {
    return PenaltyHistory(
        _id = "",
        penaltyName = "Verspätete Zahlung des Monatsbeitrag",
        playerName = "Mustermann, Max",
        penaltyValue = "1500",
        penaltyIsBeer = false,
        timeOfPenalty = LocalDate.now().toString(),
        penaltyPaid = true
    )
}
package de.vexxes.penaltycatalog.domain.model

import kotlinx.serialization.Serializable
import java.time.LocalDate

@Serializable
data class PenaltyReceived(
    val _id: String = "",
    val penaltyName: String = "",
    val playerName: String = "",
    val penaltyValue: String = "",
    val penaltyIsBeer: Boolean = false,
    val timeOfPenalty: String = "",
    val penaltyPaid: Boolean = false
)

fun penaltyReceivedExample1(): PenaltyReceived {
    return PenaltyReceived(
        _id = "",
        penaltyName = "Getränke zur Besprechung",
        playerName = "Mustermann, Max",
        penaltyValue = "1",
        penaltyIsBeer = true,
        timeOfPenalty = LocalDate.now().toString()
    )
}

fun penaltyReceivedExample2(): PenaltyReceived {
    return PenaltyReceived(
        _id = "",
        penaltyName = "Verspätete Zahlung des Monatsbeitrag",
        playerName = "Mustermann, Max",
        penaltyValue = "500",
        penaltyIsBeer = false,
        timeOfPenalty = LocalDate.now().toString()
    )
}

fun penaltyReceivedExample3(): PenaltyReceived {
    return PenaltyReceived(
        _id = "",
        penaltyName = "Verspätete Zahlung des Monatsbeitrag",
        playerName = "Mustermann, Max",
        penaltyValue = "1500",
        penaltyIsBeer = false,
        timeOfPenalty = LocalDate.now().toString(),
        penaltyPaid = true
    )
}
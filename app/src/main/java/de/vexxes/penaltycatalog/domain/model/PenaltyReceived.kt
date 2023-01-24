package de.vexxes.penaltycatalog.domain.model

import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable

@Serializable
data class PenaltyReceived(
    val id: String = "",
    val penaltyTypeId: String = "",
    val playerId: String = "",
    val timeOfPenalty: LocalDate,
    val timeOfPenaltyPaid: LocalDate? = null
)

fun penaltyReceivedExample1(): PenaltyReceived {
    return PenaltyReceived(
        id = "",
        penaltyTypeId = "",
        playerId = "",
        timeOfPenalty = LocalDate.parse("2023-01-23")
    )
}

fun penaltyReceivedExample2(): PenaltyReceived {
    return PenaltyReceived(
        id = "",
        penaltyTypeId = "",
        playerId = "",
        timeOfPenalty = LocalDate.parse("2023-01-23")
    )
}

fun penaltyReceivedExample3(): PenaltyReceived {
    return PenaltyReceived(
        id = "",
        penaltyTypeId = "",
        playerId = "",
        timeOfPenalty = LocalDate.parse("2023-01-23"),
        timeOfPenaltyPaid = LocalDate.parse("2023-01-23")
    )
}

/*
fun penaltyReceivedExample1(): PenaltyReceived {
    return PenaltyReceived(
        id = "",
        penaltyTypeId = "",
        playerId = "",
        timeOfPenalty = "",
        timeOfPenaltyPaid = ""
    )
}

fun penaltyReceivedExample2(): PenaltyReceived {
    return PenaltyReceived(
        id = "",
        penaltyTypeId = "",
        playerId = "",
        timeOfPenalty = "",
        timeOfPenaltyPaid = ""
    )
}

fun penaltyReceivedExample3(): PenaltyReceived {
    return PenaltyReceived(
        id = "",
        penaltyTypeId = "",
        playerId = "",
        timeOfPenalty = "",
        timeOfPenaltyPaid = ""
    )
}*/

package de.vexxes.penaltycatalog.domain.model

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import java.time.LocalDate

@Serializable
data class PenaltyReceived(
    val id: String = "",
    val penaltyId: String = "",
    val playerId: String = "",
    @Contextual val timeOfPenalty: LocalDate = LocalDate.now(),
    @Contextual val penaltyPaid: LocalDate = LocalDate.now()
)

fun penaltyReceivedExample1(): PenaltyReceived {
    return PenaltyReceived(
        id = "",
        penaltyId = "",
        playerId = "",
        timeOfPenalty = LocalDate.now(),
        penaltyPaid = LocalDate.now()
    )
}

fun penaltyReceivedExample2(): PenaltyReceived {
    return PenaltyReceived(
        id = "",
        penaltyId = "",
        playerId = "",
        timeOfPenalty = LocalDate.now(),
        penaltyPaid = LocalDate.now()
    )
}

fun penaltyReceivedExample3(): PenaltyReceived {
    return PenaltyReceived(
        id = "",
        penaltyId = "",
        playerId = "",
        timeOfPenalty = LocalDate.now(),
        penaltyPaid = LocalDate.now()
    )
}
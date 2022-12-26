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
) {
    companion object {
        fun exampleData1(): PenaltyHistory {
            return PenaltyHistory(
                _id = "",
                penaltyName = "Getränke zur Besprechung",
                playerName = "Mustermann, Max",
                timeOfPenalty = LocalDate.now().toString(),
                penaltyValue = "500"
            )
        }

        fun exampleData2(): PenaltyHistory {
            return PenaltyHistory(
                _id = "",
                penaltyName = "Verspätete Zahlung des Monatsbeitrag",
                playerName = "Mustermann, Max",
                penaltyValue = "500",
                penaltyIsBeer = false,
                timeOfPenalty = LocalDate.now().toString()
            )
        }

        fun exampleData3(): PenaltyHistory {
            return PenaltyHistory(
                _id = "",
                penaltyName = "Verspätete Zahlung des Monatsbeitrag",
                playerName = "Mustermann, Max",
                penaltyValue = "500",
                penaltyIsBeer = false,
                timeOfPenalty = LocalDate.now().toString(),
                penaltyPaid = true
            )
        }
    }
}
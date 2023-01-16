package de.vexxes.penaltycatalog.domain.model

import de.vexxes.penaltycatalog.domain.uistate.PenaltyTypeUiState
import kotlinx.serialization.Serializable

@Serializable
data class PenaltyType(
    val id: String = "",
    val name: String = "",
    val description: String = "",
    val isBeer: Boolean = false,
    val value: Double = 0.0,
)

fun PenaltyType.convertToPenaltyTypeUiState(): PenaltyTypeUiState {
    return PenaltyTypeUiState(
        id = this.id,
        name = this.name,
        description = this.description,
        isBeer = this.isBeer,
        value = this.value.toInt().toString()
    )
}

fun penaltyExample1(): PenaltyType {
    return PenaltyType(
        id = "",
        name = "Rote Karte wegen Meckern",
        description = "",
        isBeer = false,
        value = 15.0
    )
}

fun penaltyExample2(): PenaltyType {
    return PenaltyType(
        id = "",
        name = "Verspätete Zahlung des Monatsbeitrag",
        description = "zzgl. pro Monat",
        isBeer = false,
        value = 5.0
    )
}

fun penaltyExample3(): PenaltyType {
    return PenaltyType(
        id = "",
        name = "Getränke zur Besprechung",
        description = "Mitzubringen in alphabetischer Reihenfolge nach dem Freitagstraining",
        isBeer = true,
        value = 5.0
    )
}
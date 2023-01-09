package de.vexxes.penaltycatalog.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Penalty(
    val id: String = "",
    val name: String = "",
    val description: String = "",
    val isBeer: Boolean = false,
    val value: String = "",
)

fun penaltyExample1(): Penalty {
    return Penalty(
        id = "",
        name = "Rote Karte wegen Meckern",
        description = "",
        isBeer = false,
        value = "1500"
    )
}

fun penaltyExample2(): Penalty {
    return Penalty(
        id = "",
        name = "Verspätete Zahlung des Monatsbeitrag",
        description = "zzgl. pro Monat",
        isBeer = false,
        value = "500"
    )
}

fun penaltyExample3(): Penalty {
    return Penalty(
        id = "",
        name = "Getränke zur Besprechung",
        description = "Mitzubringen in alphabetischer Reihenfolge nach dem Freitagstraining",
        isBeer = true,
        value = "1"
    )
}
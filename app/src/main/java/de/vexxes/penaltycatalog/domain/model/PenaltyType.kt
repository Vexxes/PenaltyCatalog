package de.vexxes.penaltycatalog.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Penalty(
    val _id: String = "",
    val name: String = "",
    val categoryName: String = "",
    val description: String = "",
    val isBeer: Boolean = false,
    val value: String = "",
    val index: Int = 0
)

fun penaltyExample1(): Penalty {
    return Penalty(
        _id = "",
        name = "Rote Karte wegen Meckern",
        categoryName = "Grob mannschaftsschädigendes Verhalten",
        description = "",
        isBeer = false,
        value = "1500"
    )
}

fun penaltyExample2(): Penalty {
    return Penalty(
        _id = "",
        name = "Verspätete Zahlung des Monatsbeitrag",
        categoryName = "Monatsbeitrag",
        description = "zzgl. pro Monat",
        isBeer = false,
        value = "500"
    )
}

fun penaltyExample3(): Penalty {
    return Penalty(
        _id = "",
        name = "Getränke zur Besprechung",
        categoryName = "Sonstiges",
        description = "Mitzubringen in alphabetischer Reihenfolge nach dem Freitagstraining",
        isBeer = true,
        value = "1"
    )
}
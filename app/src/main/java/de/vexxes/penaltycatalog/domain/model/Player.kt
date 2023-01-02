package de.vexxes.penaltycatalog.domain.model

import kotlinx.serialization.Serializable
import net.datafaker.Faker
import java.util.Locale

@Serializable
data class Player(
    val _id: String = "",
    val number: Int = 0,
    val firstName: String = "",
    val lastName: String = "",
    val birthday: String = "",
    val street: String = "",
    val zipcode: Int = 0,
    val city: String = "",
    val playedGames: Int = 0,
    var goals: Int = 0,
    val yellowCards: Int = 0,
    val twoMinutes: Int = 0,
    val redCards: Int = 0
)

fun playerExample(): Player {
    val faker = Faker(Locale("de"))
    return Player(
        _id = faker.idNumber().toString(),
        number = faker.number().numberBetween(1, 99),
        firstName = faker.name().firstName(),
        lastName = faker.name().lastName(),
        birthday = faker.date().birthday("yyyy-mm-dd"),
        street = faker.address().streetName(),
        zipcode = faker.address().zipCode().toInt(),
        city = faker.address().city(),
        playedGames = faker.number().numberBetween(0, 999),
        goals = faker.number().numberBetween(0, 999),
        yellowCards = faker.number().numberBetween(0, 99),
        twoMinutes = faker.number().numberBetween(0, 99),
        redCards = faker.number().numberBetween(0, 99)
    )
}
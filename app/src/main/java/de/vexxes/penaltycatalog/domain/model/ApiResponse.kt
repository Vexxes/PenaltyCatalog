package de.vexxes.penaltycatalog.domain.model

import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient
import java.lang.Exception

@Serializable
data class ApiResponse(
    val success: Boolean,
    val Player: Player? = null,
    val message: String? = null,
    @Transient
    val error: Exception? = null
)
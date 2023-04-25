package de.vexxes.penaltycatalog.domain.uievent

import kotlinx.datetime.LocalDateTime

sealed class CancellationUiEvent {
    data class PlayerIdChanged(val playerId: String): CancellationUiEvent()
    data class EventIdChanged(val eventId: String): CancellationUiEvent()
    data class TimeOfCancellationChanged(val timeOfCancellation: LocalDateTime): CancellationUiEvent()
}
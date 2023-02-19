package de.vexxes.penaltycatalog.domain.uievent

import de.vexxes.penaltycatalog.domain.model.EventPlayerAvailability
import kotlinx.datetime.LocalDateTime

sealed class EventUiEvent {
    data class TitleChanged(val title: String): EventUiEvent()
    data class StartOfEventChanged(val startOfEvent: LocalDateTime): EventUiEvent()
    data class StartOfMeetingChanged(val startOfMeeting: LocalDateTime): EventUiEvent()
    data class AddressChanged(val address: String): EventUiEvent()
    data class DescriptionChanged(val description: String): EventUiEvent()
    data class PlayersChanged(val players: List<EventPlayerAvailability>): EventUiEvent()
}

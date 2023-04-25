package de.vexxes.penaltycatalog.viewmodels

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import de.vexxes.penaltycatalog.domain.enums.EventType
import de.vexxes.penaltycatalog.domain.enums.PlayerState
import de.vexxes.penaltycatalog.domain.model.Event
import de.vexxes.penaltycatalog.domain.model.EventPlayerAvailability
import de.vexxes.penaltycatalog.domain.model.Player
import de.vexxes.penaltycatalog.domain.repository.EventRepository
import de.vexxes.penaltycatalog.domain.repository.PlayerRepository
import de.vexxes.penaltycatalog.domain.uievent.EventUiEvent
import de.vexxes.penaltycatalog.domain.uievent.SearchUiEvent
import de.vexxes.penaltycatalog.domain.uistate.EventUiState
import de.vexxes.penaltycatalog.domain.uistate.SearchUiState
import de.vexxes.penaltycatalog.util.RequestState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.datetime.toJavaLocalDateTime
import kotlinx.datetime.toKotlinLocalDateTime
import javax.inject.Inject

@HiltViewModel
class EventViewModel @Inject constructor(
    private val eventRepository: EventRepository,
    private val playerRepository: PlayerRepository
): ViewModel() {

    // TODO: Puzzle all players corresponding to players together, so not every player is shown in the detail view
    val events: MutableState<List<Event>> = mutableStateOf(emptyList())
    private val allAvailablePlayers: MutableState<List<Player>> = mutableStateOf(emptyList())

    var eventUiState: MutableState<EventUiState> = mutableStateOf(EventUiState())
        private set

    var searchUiState: MutableState<SearchUiState> = mutableStateOf(SearchUiState())
        private set

    var requestState: MutableState<RequestState> = mutableStateOf(RequestState.Idle)

    var postEvent: MutableState<Boolean> = mutableStateOf(false)
    var updateEvent: MutableState<Boolean> = mutableStateOf(false)

    init {
        getAllEvents()
        getAllPlayers()
    }

    private fun convertResponseToEvent(event: Event) {
        val eventAvailablePlayers: MutableList<Player> = mutableListOf()

        event.players.forEach { player ->
            allAvailablePlayers.value.find { player.playerId == it.id }?.let {
                eventAvailablePlayers.add(it)
            }
        }

        eventUiState.value = eventUiState.value.copy(
            id = event.id,
            title = event.title,
            startOfEvent = event.startOfEvent,
            startOfMeeting = event.startOfMeeting,
            address = event.address,
            description = event.description,
            eventAvailablePlayers = eventAvailablePlayers.sortedWith(compareBy({ it.lastName }, { it.firstName } )),
            playerAvailability = event.players,
            type = event.type
        )
    }

    private fun createEvent(): Event {
        // Create a list with all players, that exist at the moment of creation
        val playerAvailability: MutableList<EventPlayerAvailability> = mutableListOf()
        allAvailablePlayers.value.forEach { player ->
            playerAvailability.add(
                EventPlayerAvailability(
                    playerId = player.id
                )
            )
        }

        // Copy the playerAvailabilityList
            eventUiState.value = eventUiState.value.copy(
            playerAvailability = playerAvailability
        )

        // Create a event, that will be returned
        return Event(
            id = eventUiState.value.id,
            title = eventUiState.value.title,
            startOfEvent = eventUiState.value.startOfEvent,
            startOfMeeting = eventUiState.value.startOfMeeting,
            address = eventUiState.value.address,
            description = eventUiState.value.description,
            players = eventUiState.value.playerAvailability,
            type = eventUiState.value.type
        )
    }

    private fun getAllPlayers() {
        requestState.value = RequestState.Loading

        viewModelScope.launch {
            try {
                val response = withContext(Dispatchers.IO) {
                    playerRepository.getAllPlayers()
                }

                if (response.isNotEmpty()) {
                    requestState.value = RequestState.Success
                    allAvailablePlayers.value = response
                    allAvailablePlayers.value = allAvailablePlayers.value.sortedBy { it.lastName }
                } else {
                    requestState.value = RequestState.Idle
                }
                Log.d("EventViewModel", response.toString())
            } catch (e: Exception) {
                requestState.value = RequestState.Error
                Log.d("EventViewModel", e.toString())
            }
        }
    }

    private fun verifyEvent(): Boolean {
        val titleResult = eventUiState.value.title.isEmpty()

        eventUiState.value = eventUiState.value.copy(
            titleError = titleResult
        )

        return !(titleResult)
    }

    fun getAllEvents() {
        requestState.value = RequestState.Loading

        viewModelScope.launch {
            try {
                val response = withContext(Dispatchers.IO) {
                    eventRepository.getAllEvents()
                }

                if (response.isNotEmpty()) {
                    requestState.value = RequestState.Success
                    events.value = response
                    events.value = events.value.sortedBy { it.startOfEvent }
                } else {
                    requestState.value = RequestState.Idle
                    events.value = emptyList()
                }
                Log.d("EventViewModel", "$response")
            } catch (e: Exception) {
                requestState.value = RequestState.Error
                Log.d("EventViewModel", e.toString())
            }
        }
    }

    fun getEventById(eventId: String) {
        requestState.value = RequestState.Loading

        viewModelScope.launch {
            try {
                val response = withContext(Dispatchers.IO) {
                    eventRepository.getEventById(eventId = eventId)
                }

                if (response != null) {
                    requestState.value = RequestState.Success
                    convertResponseToEvent(event = response)
                } else {
                    eventUiState.value = EventUiState()
                }
                Log.d("EventViewModel", response.toString())
            } catch (e: Exception) {
                requestState.value = RequestState.Error
                Log.d("EventViewModel", e.toString())
            }
        }
    }

    /* TODO: Create a function for search for events?*/

    fun postEvent() {
        requestState.value = RequestState.Loading

        viewModelScope.launch {
            try {
                if (verifyEvent()) {
                    val response = withContext(Dispatchers.IO) {
                        Log.d("EventViewModel", createEvent().toString())
                        eventRepository.postEvent(
                            event = createEvent()
                        )
                    }

                    if (response != null) {
                        requestState.value = RequestState.Success
                        postEvent.value = true
                    }
                    Log.d("EventViewModel", response.toString())
                }
            } catch (e: Exception) {
                requestState.value = RequestState.Error
                Log.d("EventViewModel", e.toString())
            }
        }
    }

    fun updateEvent() {
        requestState.value = RequestState.Loading

        viewModelScope.launch {
            try {
                if (verifyEvent()) {
                    val response = withContext(Dispatchers.IO) {
                        eventRepository.updateEvent(
                            eventId = eventUiState.value.id,
                            event = createEvent()
                        )
                    }

                    if (response) {
                        requestState.value = RequestState.Success
                        updateEvent.value = true
                    }
                    Log.d("EventViewModel", "${eventUiState.value.id} ${eventUiState.value.titleError} successfully updated")
                }
            } catch (e: Exception) {
                requestState.value = RequestState.Error
                Log.d("EventViewModel", e.toString())
            }
        }
    }

    private fun updatePlayerAvailability(eventId: String, playerAvailability: EventPlayerAvailability) {
        requestState.value = RequestState.Loading

        viewModelScope.launch {
            try {
                val response = withContext(Dispatchers.IO) {
                    eventRepository.playerEvent(
                        eventId = eventId,
                        playerAvailability = playerAvailability
                    )
                }

                if (response) {
                    requestState.value = RequestState.Success
                    updateEvent.value = true
                    getEventById(eventId = eventUiState.value.id)
                }

                Log.d(
                    "EventViewModel",
                    "${eventUiState.value.id} ${playerAvailability.playerId} -> ${playerAvailability.playerState} successfully updated"
                )
            } catch (e: Exception) {
                requestState.value = RequestState.Error
                Log.d("EventViewModel", e.toString())
            }
        }
    }

    fun deleteEvent() {
        requestState.value = RequestState.Loading

        viewModelScope.launch {
            try {
                val response = withContext(Dispatchers.IO) {
                    eventRepository.deleteEvent(
                        eventId = eventUiState.value.id
                    )
                }

                if (response) {
                    requestState.value = RequestState.Success
                }
                Log.d("EventViewModel", "${eventUiState.value.id} ${eventUiState.value.title} successfully deleted")

            } catch (e: Exception) {
                requestState.value = RequestState.Error
                Log.d("EventViewModel", e.toString())
            }
        }
    }

    fun resetEventUiState() {
        eventUiState.value = EventUiState()
    }

    fun onEventUiEvent(event: EventUiEvent) {
        when(event) {
            is EventUiEvent.TitleChanged -> {
                eventUiState.value = eventUiState.value.copy(
                    title = event.title
                )
            }

            is EventUiEvent.StartOfEventChanged -> {
                // If event type is "Training", subtract 15 minutes from startOfMeeting, cause that's a fixed time. For other type a individual dateTime has to be set
                val startOfMeeting = if (eventUiState.value.type == EventType.TRAINING) event.startOfEvent.toJavaLocalDateTime().minusMinutes(15).toKotlinLocalDateTime() else eventUiState.value.startOfMeeting

                eventUiState.value = eventUiState.value.copy(
                    startOfEvent = event.startOfEvent,
                    startOfMeeting = startOfMeeting
                )
            }

            is EventUiEvent.StartOfMeetingChanged -> {
                eventUiState.value = eventUiState.value.copy(
                    startOfMeeting = event.startOfMeeting
                )
            }

            is EventUiEvent.AddressChanged -> {
                eventUiState.value = eventUiState.value.copy(
                    address = event.address
                )
            }

            is EventUiEvent.DescriptionChanged -> {
                eventUiState.value = eventUiState.value.copy(
                    description = event.description
                )
            }
            is EventUiEvent.PlayerAvailabilityChanged -> {
                eventUiState.value = eventUiState.value.copy(
                    playerAvailability = event.players
                )
            }
            is EventUiEvent.TypeChanged -> {
                eventUiState.value = eventUiState.value.copy(
                    type = event.type
                )
            }
        }
    }

    fun onSearchUiEvent(event: SearchUiEvent) {
        when (event) {
            is SearchUiEvent.SearchAppBarStateChanged -> {
                searchUiState.value = searchUiState.value.copy(
                    searchAppBarState = event.searchAppBarState
                )
            }

            is SearchUiEvent.SearchTextChanged -> {
                searchUiState.value = searchUiState.value.copy(
                    searchText = event.searchText
                )
                /* TODO: Add search feature */
            }
        }
    }

    // Cycle to all possible playerStates  in following order:
    // UNDEFINED -> PRESENT -> CANCELED -> PAID_BEER -> NOT_PRESENT
    fun changePlayerAvailability(playerId: String) {
        val newPlayerState = when(eventUiState.value.playerAvailability.find { it.playerId == playerId }?.playerState) {
            PlayerState.UNDEFINED -> PlayerState.PRESENT
            PlayerState.PRESENT -> PlayerState.CANCELED
            PlayerState.CANCELED -> PlayerState.PAID_BEER
            PlayerState.PAID_BEER -> PlayerState.NOT_PRESENT
            PlayerState.NOT_PRESENT -> PlayerState.UNDEFINED
            null -> PlayerState.UNDEFINED
        }

        updatePlayerAvailability(
            eventId = eventUiState.value.id,
            playerAvailability = EventPlayerAvailability(
                playerId = playerId,
                playerState = newPlayerState
            )
        )
    }
}
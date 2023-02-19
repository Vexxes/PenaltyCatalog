package de.vexxes.penaltycatalog.viewmodels

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import de.vexxes.penaltycatalog.domain.model.Event
import de.vexxes.penaltycatalog.domain.repository.EventRepository
import de.vexxes.penaltycatalog.domain.uievent.EventUiEvent
import de.vexxes.penaltycatalog.domain.uievent.SearchUiEvent
import de.vexxes.penaltycatalog.domain.uistate.EventUiState
import de.vexxes.penaltycatalog.domain.uistate.SearchUiState
import de.vexxes.penaltycatalog.util.RequestState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class EventViewModel @Inject constructor(
    private val eventRepository: EventRepository
): ViewModel() {


    val events: MutableState<List<Event>> = mutableStateOf(emptyList())

    var eventUiState: MutableState<EventUiState> = mutableStateOf(EventUiState())
        private set

    var searchUiState: MutableState<SearchUiState> = mutableStateOf(SearchUiState())
        private set

    var requestState: MutableState<RequestState> = mutableStateOf(RequestState.Idle)

    var postEvent: MutableState<Boolean> = mutableStateOf(false)
    var updateEvent: MutableState<Boolean> = mutableStateOf(false)

    init {
        getAllEvents()
    }

    private fun convertResponseToEvent(event: Event) {
        eventUiState.value = eventUiState.value.copy(
            id = event.id,
            title = event.title,
            startOfEvent = event.startOfEvent,
            startOfMeeting = event.startOfMeeting,
            address = event.address,
            description = event.description,
            players = event.players,
            type = event.type
        )
    }

    private fun createEvent(): Event {
        return Event(
            id = eventUiState.value.id,
            title = eventUiState.value.title,
            startOfEvent = eventUiState.value.startOfEvent,
            startOfMeeting = eventUiState.value.startOfMeeting,
            address = eventUiState.value.address,
            description = eventUiState.value.description,
            players = eventUiState.value.players,
            type = eventUiState.value.type
        )
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
                }
                Log.d("EventViewModel", response.toString())
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
                eventUiState.value = eventUiState.value.copy(
                    startOfEvent = event.startOfEvent
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
            is EventUiEvent.PlayersChanged -> {
                eventUiState.value = eventUiState.value.copy(
                    players = event.players
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
}
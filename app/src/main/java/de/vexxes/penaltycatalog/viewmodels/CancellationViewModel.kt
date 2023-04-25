package de.vexxes.penaltycatalog.viewmodels

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import de.vexxes.penaltycatalog.domain.model.Cancellation
import de.vexxes.penaltycatalog.domain.model.Event
import de.vexxes.penaltycatalog.domain.model.Player
import de.vexxes.penaltycatalog.domain.repository.CancellationRepository
import de.vexxes.penaltycatalog.domain.repository.EventRepository
import de.vexxes.penaltycatalog.domain.repository.PlayerRepository
import de.vexxes.penaltycatalog.domain.uievent.CancellationUiEvent
import de.vexxes.penaltycatalog.domain.uievent.SearchUiEvent
import de.vexxes.penaltycatalog.domain.uistate.CancellationUiState
import de.vexxes.penaltycatalog.domain.uistate.SearchUiState
import de.vexxes.penaltycatalog.util.RequestState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import javax.inject.Inject

@HiltViewModel
class CancellationViewModel @Inject constructor(
    private val cancellationRepository: CancellationRepository,
    private val playerRepository: PlayerRepository,
    private val eventRepository: EventRepository
): ViewModel() {

    private var cancellations: MutableState<List<Cancellation>> = mutableStateOf(emptyList())

    var cancellationUiStates: MutableState<List<CancellationUiState>> = mutableStateOf(emptyList())
        private set

    var cancellationUiState: MutableState<CancellationUiState> = mutableStateOf(CancellationUiState())
        private set

    var searchUiState: MutableState<SearchUiState> = mutableStateOf(SearchUiState())

    val players: MutableState<List<Player>> = mutableStateOf(emptyList())
    val events: MutableState<List<Event>> = mutableStateOf(emptyList())

    var requestState: MutableState<RequestState> = mutableStateOf(RequestState.Idle)

    var postCancellation: MutableState<Boolean> = mutableStateOf(false)
    var updateCancellation: MutableState<Boolean> = mutableStateOf(false)

    var todayFilterSelected: MutableState<Boolean> = mutableStateOf(false)

    init {
        getAllPlayers()
        getAllEvents()
        getAllCancellations()
    }

    private fun convertResponseToCancellation(cancellation: Cancellation) {
        // Search with the id for the player name, if player not found return empty string
        val playerName = players.value.find { it.id == cancellation.playerId }?.let {
            "${it.lastName}, ${it.firstName}"
        } ?: ""

        // Search with the id for the event start time, if not found return null
        val event = events.value.find { it.id == cancellation.eventId }

        val title = event?.title ?: ""
        val eventStartOfEvent = event?.startOfEvent ?: Clock.System.now().toLocalDateTime(TimeZone.UTC)

        cancellationUiState.value = cancellationUiState.value.copy(
            id = cancellation.id,
            playerId = cancellation.playerId,
            playerName = playerName,
            eventId = cancellation.eventId,
            eventTitle = title,
            eventStartOfEvent = eventStartOfEvent,
            timeOfCancellation = cancellation.timeOfCancellation
        )
    }

    private fun convertCancellationsToCancellationUiStates() {
        val cancellationUiStatesTemp: MutableList<CancellationUiState> = mutableListOf()
        var playerName: String
        var eventStartOfEvent: LocalDateTime

        cancellations.value.forEach { cancellation ->
            // Search with the id for the player name, if player not found return empty string
            playerName = players.value.find { it.id == cancellation.playerId }?.let {
                "${it.lastName}, ${it.firstName}"
            } ?: ""

            // Search with the id for the event start time, if not found return null
            eventStartOfEvent = events.value.find { it.id == cancellation.eventId }?.startOfEvent ?: Clock.System.now().toLocalDateTime(TimeZone.UTC)

            cancellationUiStatesTemp.add(
                CancellationUiState(
                    id = cancellation.id,
                    playerId = cancellation.playerId,
                    playerName = playerName,
                    eventId = cancellation.eventId,
                    eventStartOfEvent = eventStartOfEvent,
                    timeOfCancellation = cancellation.timeOfCancellation
                )
            )
        }

        cancellationUiStates.value = cancellationUiStatesTemp
    }

    private fun createCancellation(): Cancellation {
        return Cancellation(
            id = cancellationUiState.value.id,
            playerId = cancellationUiState.value.playerId,
            eventId = cancellationUiState.value.eventId,
            timeOfCancellation = cancellationUiState.value.timeOfCancellation
        )
    }

    private fun verifyCancellation(): Boolean {
        val playerIdResult = cancellationUiState.value.playerId.isEmpty()
        val eventIdResult = cancellationUiState.value.eventId.isEmpty()

        cancellationUiState.value = cancellationUiState.value.copy(
            playerIdError = playerIdResult,
            eventIdError = eventIdResult
        )

        return !(playerIdResult || eventIdResult)
    }

    fun getAllCancellations() {
        requestState.value = RequestState.Loading

        viewModelScope.launch {
            try {
                val response = withContext(Dispatchers.IO) {
                    cancellationRepository.getAllCancellations()
                }

                if (response.isNotEmpty()) {
                    requestState.value = RequestState.Success
                    cancellations.value = response
                    convertCancellationsToCancellationUiStates()
                } else {
                    requestState.value = RequestState.Idle
                    cancellations.value = emptyList()
                    cancellationUiStates.value = emptyList()
                }
                Log.d("CancellationViewModel", "$response")
            } catch (e: Exception) {
                requestState.value = RequestState.Error
                Log.d("CancellationViewModel", e.toString())
            }
        }
    }

    private fun getAllEvents() {
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
                Log.d("CancellationViewModel", "$response")
            } catch (e: Exception) {
                requestState.value = RequestState.Error
                Log.d("CancellationViewModel", e.toString())
            }
        }
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
                    players.value = response
                    players.value = players.value.sortedBy { it.lastName }
                } else {
                    requestState.value = RequestState.Idle
                }
                Log.d("CancellationViewModel", response.toString())
            } catch (e: Exception) {
                requestState.value = RequestState.Error
                Log.d("CancellationViewModel", e.toString())
            }
        }
    }

    fun getCancellationById(cancellationId: String) {
        requestState.value = RequestState.Loading

        viewModelScope.launch {
            try {
                val response = withContext(Dispatchers.IO) {
                    cancellationRepository.getCancellationById(cancellationId = cancellationId)
                }

                if (response != null) {
                    requestState.value = RequestState.Success
                    convertResponseToCancellation(cancellation = response)
                } else {
                    cancellationUiState.value = CancellationUiState()
                }
                Log.d("CancellationViewModel", response.toString())
            } catch (e: Exception) {
                requestState.value = RequestState.Error
                Log.d("CancellationViewModel", e.toString())
            }
        }
    }

    fun postCancellation() {
        requestState.value = RequestState.Loading

        viewModelScope.launch {
            try {
                if (verifyCancellation()) {
                    val response = withContext(Dispatchers.IO) {
                        Log.d("CancellationViewModel", createCancellation().toString())
                        cancellationRepository.postCancellation(
                            cancellation = createCancellation()
                        )
                    }

                    if (response != null) {
                        requestState.value = RequestState.Success
                        postCancellation.value = true
                    }
                    Log.d("CancellationViewModel", response.toString())
                }
            } catch (e: Exception) {
                requestState.value = RequestState.Error
                Log.d("EventViewModel", e.toString())
            }
        }
    }

    fun updateCancellation() {
        requestState.value = RequestState.Loading

        viewModelScope.launch {
            try {
                if (verifyCancellation()) {
                    val response = withContext(Dispatchers.IO) {
                        cancellationRepository.updateCancellation(
                            cancellationId = cancellationUiState.value.id,
                            cancellation = createCancellation()
                        )
                    }

                    if (response) {
                        requestState.value = RequestState.Success
                        updateCancellation.value = true
                    }
                    Log.d("CancellationViewModel", "${cancellationUiState.value.id} successfully updated")
                }
            } catch (e: Exception) {
                requestState.value = RequestState.Error
                Log.d("CancellationViewModel", e.toString())
            }
        }
    }

    fun deleteCancellation() {
        requestState.value = RequestState.Loading

        viewModelScope.launch {
            try {
                val response = withContext(Dispatchers.IO) {
                    cancellationRepository.deleteCancellation(
                        cancellationId = cancellationUiState.value.id
                    )
                }

                if (response) {
                    requestState.value = RequestState.Success
                }
                Log.d("CancellationViewModel", "${cancellationUiState.value.id} successfully deleted")

            } catch (e: Exception) {
                requestState.value = RequestState.Error
                Log.d("CancellationViewModel", e.toString())
            }
        }
    }

    fun resetCancellationUiState() {
        cancellationUiState.value = CancellationUiState()
    }

    fun onCancellationUiEvent(event: CancellationUiEvent) {
        when(event) {

            is CancellationUiEvent.EventIdChanged -> {
                // Get title of event
                val eventTitle = events.value.find { it.id == event.eventId }?.title ?: ""

                cancellationUiState.value = cancellationUiState.value.copy(
                    eventId = event.eventId,
                    eventTitle = eventTitle
                )
            }

            is CancellationUiEvent.PlayerIdChanged -> {
                // Get playerName of player
                val playerName = players.value.find { it.id == event.playerId }?.let { "${it.lastName}, ${it.firstName}" } ?: ""

                cancellationUiState.value = cancellationUiState.value.copy(
                    playerId = event.playerId,
                    playerName = playerName
                )
            }

            is CancellationUiEvent.TimeOfCancellationChanged -> {
                cancellationUiState.value = cancellationUiState.value.copy(
                    timeOfCancellation = event.timeOfCancellation
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

    fun onTodayFilterEvent() {
        todayFilterSelected.value = !todayFilterSelected.value

        val matchingCancellations: MutableList<CancellationUiState> = mutableListOf()
        val today = Clock.System.now().toLocalDateTime(TimeZone.UTC).date

        if (todayFilterSelected.value) {
            cancellationUiStates.value.forEach { cancellationUiState ->
                if (cancellationUiState.eventStartOfEvent.date.compareTo(today) == 0) {
                    matchingCancellations.add(cancellationUiState)
                }
            }
            cancellationUiStates.value = matchingCancellations

        } else {
            getAllCancellations()
        }
    }
}
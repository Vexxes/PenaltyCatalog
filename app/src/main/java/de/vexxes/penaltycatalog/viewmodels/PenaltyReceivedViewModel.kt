package de.vexxes.penaltycatalog.viewmodels

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import de.vexxes.penaltycatalog.domain.model.PenaltyType
import de.vexxes.penaltycatalog.domain.model.PenaltyReceived
import de.vexxes.penaltycatalog.domain.model.Player
import de.vexxes.penaltycatalog.domain.repository.PenaltyReceivedRepository
import de.vexxes.penaltycatalog.domain.repository.PenaltyTypeRepository
import de.vexxes.penaltycatalog.domain.repository.PlayerRepository
import de.vexxes.penaltycatalog.domain.uievent.PenaltyReceivedUiEvent
import de.vexxes.penaltycatalog.domain.uievent.SearchUiEvent
import de.vexxes.penaltycatalog.domain.uistate.PenaltyReceivedUiState
import de.vexxes.penaltycatalog.domain.uistate.SearchUiState
import de.vexxes.penaltycatalog.util.RequestState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.datetime.LocalDate
import javax.inject.Inject

@HiltViewModel
class PenaltyReceivedViewModel @Inject constructor(
    private val penaltyReceivedRepository: PenaltyReceivedRepository,
    private val penaltyTypeRepository: PenaltyTypeRepository,
    private val playerRepository: PlayerRepository
) : ViewModel() {

    val penaltyReceived: MutableState<List<PenaltyReceived>> = mutableStateOf(emptyList())
    var penalties: MutableState<List<PenaltyType>> = mutableStateOf(emptyList())
    var players: MutableState<List<Player>> = mutableStateOf(emptyList())

    var penaltyReceivedUiState: MutableState<PenaltyReceivedUiState> =
        mutableStateOf(PenaltyReceivedUiState())
        private set

    var searchUiState: MutableState<SearchUiState> = mutableStateOf(SearchUiState())
        private set

    var requestState: MutableState<RequestState> = mutableStateOf(RequestState.Idle)

    var postPenaltyReceived: MutableState<Boolean> = mutableStateOf(false)
    var updatePenaltyReceived: MutableState<Boolean> = mutableStateOf(false)

    init {
//        getAllPenaltyReceived()
    }

    private fun convertResponseToPenaltyReceived(penaltyReceived: PenaltyReceived) {
        penaltyReceivedUiState.value = penaltyReceivedUiState.value.copy(
            id = penaltyReceived.id,
            playerId = penaltyReceived.playerId,
            penaltyId = penaltyReceived.penaltyTypeId,
            timeOfPenalty = penaltyReceived.timeOfPenalty,
            timeOfPenaltyPaid = penaltyReceived.timeOfPenaltyPaid
        )
    }

    private fun createPenaltyReceived(): PenaltyReceived {
        return PenaltyReceived(
            id = penaltyReceivedUiState.value.id,
            playerId = penaltyReceivedUiState.value.playerId,
            penaltyTypeId = penaltyReceivedUiState.value.penaltyId,
            timeOfPenalty = penaltyReceivedUiState.value.timeOfPenalty,
            timeOfPenaltyPaid = penaltyReceivedUiState.value.timeOfPenaltyPaid
        )
    }

    fun getAllPenalties() {
        requestState.value = RequestState.Loading

        viewModelScope.launch {
            try {
                val response = withContext(Dispatchers.IO) {
                    penaltyTypeRepository.getAllPenaltyTypes()
                }

                if (response.isNotEmpty()) {
                    requestState.value = RequestState.Success
                    penalties.value = response
                } else {
                    requestState.value = RequestState.Idle
                }
                Log.d("PenaltyReceivedViewModel", response.toString())
            } catch (e: Exception) {
                requestState.value = RequestState.Error
                Log.d("PenaltyReceivedViewModel", e.toString())
            }
        }
    }

    fun getAllPlayers() {
        requestState.value = RequestState.Loading

        viewModelScope.launch {
            try {
                val response = withContext(Dispatchers.IO) {
                    playerRepository.getAllPlayers()
                }

                if (response.isNotEmpty()) {
                    requestState.value = RequestState.Success
                    players.value = response
                } else {
                    requestState.value = RequestState.Idle
                }

                Log.d("PenaltyReceivedViewModel", response.toString())
            } catch (e: Exception) {
                requestState.value = RequestState.Error
                Log.d("PenaltyReceivedViewModel", e.toString())
            }
        }
    }

    /*TODO Implement logic*/
    private fun verifyPenaltyReceived(): Boolean {
        val playerIdResult = penaltyReceivedUiState.value.playerId.isEmpty()
        val penaltyIdResult = penaltyReceivedUiState.value.penaltyId.isEmpty()

        penaltyReceivedUiState.value = penaltyReceivedUiState.value.copy(
            playerIdError = playerIdResult,
            penaltyIdError = penaltyIdResult
        )

        return !(playerIdResult || penaltyIdResult)
    }

    fun getAllPenaltyReceived() {
        requestState.value = RequestState.Loading

        viewModelScope.launch {
            try {
                val response = withContext(Dispatchers.IO) {
                    penaltyReceivedRepository.getAllPenaltyReceived()
                }

                if (response.isNotEmpty()) {
                    requestState.value = RequestState.Success
                    penaltyReceived.value = response
                } else {
                    requestState.value = RequestState.Idle
                }
                Log.d("PenaltyReceivedViewModel", response.toString())
            } catch (e: Exception) {
                requestState.value = RequestState.Error
                Log.d("PenaltyReceivedViewModel", e.toString())
            }
        }
    }

    fun getPenaltyReceivedById(penaltyReceivedId: String) {
        requestState.value = RequestState.Loading

        viewModelScope.launch {
            try {
                val response = withContext(Dispatchers.IO) {
                    penaltyReceivedRepository.getPenaltyReceivedById(penaltyReceivedId = penaltyReceivedId)
                }

                if (response != null) {
                    requestState.value = RequestState.Success
                    convertResponseToPenaltyReceived(penaltyReceived = response)
                } else {
                    penaltyReceivedUiState.value = PenaltyReceivedUiState()
                }
                Log.d("PenaltyReceivedViewModel", response.toString())
            } catch (e: Exception) {
                requestState.value = RequestState.Error
                Log.d("PenaltyReceivedViewModel", e.toString())
            }
        }
    }

    private fun getPenaltyReceivedByPlayerId(playerId: String) {
        requestState.value = RequestState.Loading

        viewModelScope.launch {
            try {
                val response = withContext(Dispatchers.IO) {
                    penaltyReceivedRepository.getPenaltyReceivedByPlayerId(playerId = playerId)
                }

                if (response != null) {
                    requestState.value = RequestState.Success
                    penaltyReceived.value = response
                } else {
                    requestState.value = RequestState.Idle
                }

                Log.d("PenaltyReceivedViewModel", response.toString())
            } catch (e: Exception) {
                requestState.value = RequestState.Error
                Log.d("PenaltyReceivedViewModel", e.toString())
            }
        }
    }

    fun postPenaltyReceived() {
        requestState.value = RequestState.Loading

        viewModelScope.launch {
            try {
                if (verifyPenaltyReceived()) {
                    val response = withContext(Dispatchers.IO) {
                        penaltyReceivedRepository.postPenaltyReceived(
                            penaltyReceived = createPenaltyReceived()
                        )
                    }

                    if (response != null) {
                        RequestState.Success
                        postPenaltyReceived.value = true
                    }

                    Log.d("PenaltyReceivedViewModel", response.toString())
                }
            } catch (e: Exception) {
                requestState.value = RequestState.Error
                Log.d("PenaltyReceivedViewModel", e.toString())
            }
        }
    }

    fun updatePenaltyReceived() {
        requestState.value = RequestState.Loading

        viewModelScope.launch {
            try {
                if (verifyPenaltyReceived()) {
                    val response = withContext(Dispatchers.IO) {
                        penaltyReceivedRepository.updatePenaltyReceived(
                            id = penaltyReceivedUiState.value.id,
                            penaltyReceived = createPenaltyReceived()
                        )
                    }

                    if (response) {
                        requestState.value = RequestState.Success
                        updatePenaltyReceived.value = true
                    }

                    Log.d(
                        "PenaltyReceivedViewModel",
                        "${penaltyReceivedUiState.value.id} successfully updated"
                    )
                }
            } catch (e: Exception) {
                requestState.value = RequestState.Error
                Log.d("PenaltyReceivedViewModel", e.toString())
            }
        }
    }

    fun deletePenaltyReceived() {
        requestState.value = RequestState.Loading

        viewModelScope.launch {
            try {
                val response = withContext(Dispatchers.IO) {
                    penaltyReceivedRepository.deletePenaltyReceived(
                        penaltyReceivedId = penaltyReceivedUiState.value.id
                    )
                }

                if (response) {
                    requestState.value = RequestState.Success
                } else {
                    requestState.value = RequestState.Idle
                }

                Log.d("PenaltyReceivedViewModel", "${penaltyReceivedUiState.value.id} successfully deleted")
            } catch (e: Exception) {
                requestState.value = RequestState.Error
                Log.d("PenaltyReceivedViewModel", e.toString())
            }
        }
    }

    fun onPenaltyHistoryUiEvent(event: PenaltyReceivedUiEvent) {
        when (event) {
            is PenaltyReceivedUiEvent.PenaltyIdChanged -> {
                // Compare id from penalty list with id from event. Only if id's match copy values
                penalties.value.forEach { penalty ->
                    if (penalty.id == event.penaltyId) {
                        penaltyReceivedUiState.value = penaltyReceivedUiState.value.copy(
                            penaltyId = event.penaltyId,
                            penaltyValue = penalty.value.toString(),
                            penaltyIsBeer = penalty.isBeer
                        )
                    }
                }
            }

            is PenaltyReceivedUiEvent.PlayerIdChanged -> {
                penaltyReceivedUiState.value = penaltyReceivedUiState.value.copy(
                    playerId = event.playerId
                )
            }

            is PenaltyReceivedUiEvent.TimeOfPenaltyChanged -> {
                penaltyReceivedUiState.value = penaltyReceivedUiState.value.copy(
                    timeOfPenalty = event.timeOfPenalty
                )
            }

            is PenaltyReceivedUiEvent.PenaltyPaidChanged -> {
                penaltyReceivedUiState.value = penaltyReceivedUiState.value.copy(
                    timeOfPenaltyPaid = event.penaltyPaid
                )
                updatePenaltyReceived()
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
            }
        }
    }

    fun resetPenaltyHistory() {
        penaltyReceivedUiState.value = PenaltyReceivedUiState()
    }
}
package de.vexxes.penaltycatalog.viewmodels

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import de.vexxes.penaltycatalog.domain.enums.PenaltyReceivedSort
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
import javax.inject.Inject

@HiltViewModel
class PenaltyReceivedViewModel @Inject constructor(
    private val penaltyReceivedRepository: PenaltyReceivedRepository,
    private val penaltyTypeRepository: PenaltyTypeRepository,
    private val playerRepository: PlayerRepository
) : ViewModel() {

    val penaltyReceivedUiStates: MutableState<List<PenaltyReceivedUiState>> = mutableStateOf(emptyList())
    val penaltyReceived: MutableState<List<PenaltyReceived>> = mutableStateOf(emptyList())
    val penalties: MutableState<List<PenaltyType>> = mutableStateOf(emptyList())
    val players: MutableState<List<Player>> = mutableStateOf(emptyList())

    var penaltyReceivedUiState: MutableState<PenaltyReceivedUiState> =
        mutableStateOf(PenaltyReceivedUiState())
        private set

    var searchUiState: MutableState<SearchUiState> = mutableStateOf(SearchUiState())
        private set

    private var penaltyReceivedSort: MutableState<PenaltyReceivedSort> = mutableStateOf(PenaltyReceivedSort.TIME_OF_PENALTY_DESCENDING)

    var requestState: MutableState<RequestState> = mutableStateOf(RequestState.Idle)

    var postPenaltyReceived: MutableState<Boolean> = mutableStateOf(false)
    var updatePenaltyReceived: MutableState<Boolean> = mutableStateOf(false)

    init {
        updateLists()
    }

    private fun combinePenaltyReceivedUiState(penaltyReceived: PenaltyReceived): PenaltyReceivedUiState {
        val player = players.value.find { it.id == penaltyReceived.playerId }
        val penaltyType = penalties.value.find { it.id == penaltyReceived.penaltyTypeId }

        // Create default uistate
        var penaltyReceivedUiStateTemp = PenaltyReceivedUiState(
            id = penaltyReceived.id,
            playerId = penaltyReceived.playerId,
            penaltyId = penaltyReceived.penaltyTypeId,
            timeOfPenalty = penaltyReceived.timeOfPenalty,
            timeOfPenaltyPaid = penaltyReceived.timeOfPenaltyPaid
        )

        // Add player information to uistate if available
        penaltyReceivedUiStateTemp = if (player != null) {
            penaltyReceivedUiStateTemp.copy(
                playerName = "${player.lastName}, ${player.firstName}"
            )
        } else {
            penaltyReceivedUiStateTemp.copy(
                playerName = penaltyReceived.playerId
            )
        }

        // Add penaltyType information to uistate if available
        penaltyReceivedUiStateTemp = if (penaltyType != null) {
            penaltyReceivedUiStateTemp.copy(
                penaltyName = penaltyType.name,
                penaltyValue = penaltyType.value.toInt().toString(),
                penaltyIsBeer = penaltyType.isBeer,
            )
        } else {
            penaltyReceivedUiStateTemp.copy(
                penaltyName = penaltyReceived.penaltyTypeId
            )
        }

        return penaltyReceivedUiStateTemp
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

    private fun getAllPenalties() {
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

    private fun getAllPenaltyReceived() {
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

    private fun mergeLists() {
        val tempList = mutableStateListOf<PenaltyReceivedUiState>()

        penaltyReceived.value.forEach { penaltyReceived ->
            combinePenaltyReceivedUiState(penaltyReceived = penaltyReceived).let {
                tempList.add(it)
            }
        }

        penaltyReceivedUiStates.value = tempList.sortedByDescending { it.timeOfPenalty }

        Log.d("PenaltyReceivedViewModel", "${tempList.toList()}")
    }

    private fun verifyPenaltyReceived(): Boolean {
        val playerIdResult = penaltyReceivedUiState.value.playerId.isEmpty()
        val penaltyIdResult = penaltyReceivedUiState.value.penaltyId.isEmpty()

        penaltyReceivedUiState.value = penaltyReceivedUiState.value.copy(
            playerIdError = playerIdResult,
            penaltyIdError = penaltyIdResult
        )

        return !(playerIdResult || penaltyIdResult)
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
                    penaltyReceivedUiState.value = combinePenaltyReceivedUiState(penaltyReceived = response)
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

    /* TODO: Navigate from PlayerDetail Screen to filtered history */
    fun getPenaltyReceivedByPlayerId(playerId: String) {
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

    fun updateLists() {
        getAllPenaltyReceived()
        getAllPenalties()
        getAllPlayers()
        mergeLists()
        requestState.value = RequestState.Success
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

                    Log.d("PenaltyReceivedViewModel", "${penaltyReceivedUiState.value.id} successfully updated"
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

    fun resetPenaltyReceivedUiState() {
        penaltyReceivedUiState.value = PenaltyReceivedUiState()
    }

    fun onPenaltyReceivedUiEvent(event: PenaltyReceivedUiEvent) {
        when (event) {
            is PenaltyReceivedUiEvent.PenaltyIdChanged -> {
                // Compare id from penalty list with id from event. Only if id's match copy values
                penalties.value.forEach { penalty ->
                    if (penalty.id == event.penaltyId) {
                        penaltyReceivedUiState.value = penaltyReceivedUiState.value.copy(
                            penaltyId = event.penaltyId,
                            penaltyName = penalty.name,
                            penaltyValue = penalty.value.toString(),
                            penaltyIsBeer = penalty.isBeer
                        )
                    }
                }
            }

            is PenaltyReceivedUiEvent.PlayerIdChanged -> {
                players.value.forEach { player ->
                    if (player.id == event.playerId) {
                        penaltyReceivedUiState.value = penaltyReceivedUiState.value.copy(
                            playerId = event.playerId,
                            playerName = "${player.lastName}, ${player.firstName}"
                        )
                    }
                }
            }

            is PenaltyReceivedUiEvent.TimeOfPenaltyChanged -> {
                penaltyReceivedUiState.value = penaltyReceivedUiState.value.copy(
                    timeOfPenalty = event.timeOfPenalty
                )
            }

            is PenaltyReceivedUiEvent.PenaltyPaidChanged -> {
                penaltyReceivedUiState.value = penaltyReceivedUiState.value.copy(
                    timeOfPenaltyPaid = event.timeOfPenaltyPaid
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

    fun onSortEvent(penaltyReceivedSort: PenaltyReceivedSort) {
        this.penaltyReceivedSort.value = penaltyReceivedSort

        when(penaltyReceivedSort) {
            PenaltyReceivedSort.TIME_OF_PENALTY_ASCENDING -> penaltyReceivedUiStates.value = penaltyReceivedUiStates.value.sortedBy { it.timeOfPenalty }
            PenaltyReceivedSort.TIME_OF_PENALTY_DESCENDING -> penaltyReceivedUiStates.value = penaltyReceivedUiStates.value.sortedByDescending { it.timeOfPenalty }
            PenaltyReceivedSort.NAME_ASCENDING -> penaltyReceivedUiStates.value = penaltyReceivedUiStates.value.sortedWith(compareBy({ it.playerName }, { it.timeOfPenalty } ))
            PenaltyReceivedSort.NAME_DESCENDING -> penaltyReceivedUiStates.value = penaltyReceivedUiStates.value.sortedWith(compareByDescending<PenaltyReceivedUiState>{ it.playerName }.thenByDescending { it.timeOfPenalty })
        }
    }
}
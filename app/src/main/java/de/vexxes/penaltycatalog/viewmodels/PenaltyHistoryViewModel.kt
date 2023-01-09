package de.vexxes.penaltycatalog.viewmodels

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import de.vexxes.penaltycatalog.domain.model.ApiResponse
import de.vexxes.penaltycatalog.domain.model.Penalty
import de.vexxes.penaltycatalog.domain.model.PenaltyReceived
import de.vexxes.penaltycatalog.domain.model.Player
import de.vexxes.penaltycatalog.domain.enums.toValue
import de.vexxes.penaltycatalog.domain.repository.Repository
import de.vexxes.penaltycatalog.domain.uievent.PenaltyHistoryUiEvent
import de.vexxes.penaltycatalog.domain.uievent.SearchUiEvent
import de.vexxes.penaltycatalog.domain.uistate.PenaltyHistoryUiState
import de.vexxes.penaltycatalog.domain.uistate.SearchUiState
import de.vexxes.penaltycatalog.util.RequestState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class PenaltyHistoryViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    val penaltyReceived: MutableState<List<PenaltyReceived>> = mutableStateOf(emptyList())
    var penalties: MutableState<List<Penalty>> = mutableStateOf(emptyList())
    var players: MutableState<List<Player>> = mutableStateOf(emptyList())

    var penaltyHistoryUiState: MutableState<PenaltyHistoryUiState> = mutableStateOf(PenaltyHistoryUiState())
        private set

    var searchUiState: MutableState<SearchUiState> = mutableStateOf(SearchUiState())
        private set

    var apiResponse: MutableState<RequestState> = mutableStateOf(RequestState.Idle)
    var lastResponse: MutableState<ApiResponse> = mutableStateOf(ApiResponse())

    init {
        getAllPenaltyHistory()
    }

    private fun convertResponseToPenaltyHistory(penaltyReceived: PenaltyReceived) {
        penaltyHistoryUiState.value = penaltyHistoryUiState.value.copy(
            id = penaltyReceived.id,
            playerId = penaltyReceived.playerId,
            penaltyId = penaltyReceived.penaltyId,
            timeOfPenalty = penaltyReceived.timeOfPenalty,
            penaltyPaid = penaltyReceived.penaltyPaid
        )
    }

    private fun createPenaltyHistory(): PenaltyReceived {
        return PenaltyReceived(
            id = penaltyHistoryUiState.value.id,
            playerId = penaltyHistoryUiState.value.playerId,
            penaltyId = penaltyHistoryUiState.value.penaltyId,
            timeOfPenalty = penaltyHistoryUiState.value.timeOfPenalty,
            penaltyPaid = penaltyHistoryUiState.value.penaltyPaid
        )
    }

    fun getAllPenalties() {
        apiResponse.value = RequestState.Loading

        viewModelScope.launch {
            try {
                val response = withContext(Dispatchers.IO) {
                    repository.getAllPenalties()
                }

                apiResponse.value = RequestState.Success

                if(response.penalty != null) {
                    penalties.value = response.penalty
                }
                Log.d("PenaltyViewModel", response.toString())
            }
            catch (e: Exception) {
                apiResponse.value = RequestState.Error
                Log.d("PenaltyViewModel", e.toString())
            }
        }
    }

    fun getAllPlayers() {
        apiResponse.value = RequestState.Loading

        viewModelScope.launch {
            try {
/*                val response = withContext(Dispatchers.IO) {
                    repository.getAllPlayers(sortOrder = searchUiState.value.sortOrder.toValue())
                }*/
//                players.value = response
                /*
                apiResponse.value = RequestState.Success(response)

                if(response.player != null) {
                    players.value = response.player
                }
                 */
//                Log.d("PlayerViewModel", response.toString())
            }
            catch (e: Exception) {
                apiResponse.value = RequestState.Error
                Log.d("PlayerViewModel", e.toString())
            }
        }
    }

    /*TODO Implement logic*/
    private fun verifyPenaltyHistory(): Boolean {
        val playerIdResult = penaltyHistoryUiState.value.playerId.isEmpty()
        val penaltyIdResult = penaltyHistoryUiState.value.penaltyId.isEmpty()

        penaltyHistoryUiState.value = penaltyHistoryUiState.value.copy(
            playerIdError = playerIdResult,
            penaltyIdError = penaltyIdResult
        )

        return !(playerIdResult || penaltyIdResult)
    }

    fun getAllPenaltyHistory() {
        apiResponse.value = RequestState.Loading

        viewModelScope.launch {
            try {
                val response = withContext(Dispatchers.IO) {
                    repository.getAllPenaltyHistory()
                }

                apiResponse.value = RequestState.Success

                if (response.penaltyReceived != null) {
                    penaltyReceived.value = response.penaltyReceived
                }
                Log.d("PenaltyHistoryViewModel", response.toString())
            } catch (e: Exception) {
                apiResponse.value = RequestState.Error
                Log.d("PenaltyHistoryViewModel", e.toString())
            }
        }
    }

    fun getPenaltyHistoryById(penaltyHistoryId: String) {
        apiResponse.value = RequestState.Loading

        viewModelScope.launch {
            try {
                val response = withContext(Dispatchers.IO) {
                    repository.getPenaltyHistoryById(penaltyHistoryId = penaltyHistoryId)
                }
                apiResponse.value = RequestState.Success

                if (response.penaltyReceived != null) {
                    convertResponseToPenaltyHistory(penaltyReceived = response.penaltyReceived.first())
                } else {
                    penaltyHistoryUiState.value = PenaltyHistoryUiState()
                }
                Log.d("PenaltyHistoryViewModel", response.toString())
            }
            catch (e: Exception) {
                apiResponse.value = RequestState.Error
                Log.d("PenaltyHistoryViewModel", e.toString())
            }
        }
    }

    private fun getPenaltyHistoryBySearch() {
        apiResponse.value = RequestState.Loading

        viewModelScope.launch {
            try {
                val response = withContext(Dispatchers.IO) {
                    repository.getPenaltyHistoryBySearch(searchText = searchUiState.value.searchText)
                }
                apiResponse.value = RequestState.Success

                if(response.penaltyReceived != null) {
                    penaltyReceived.value = response.penaltyReceived
                }
                Log.d("PenaltyHistoryViewModel", response.toString())
            }
            catch (e: Exception) {
                apiResponse.value = RequestState.Error
                Log.d("PenaltyHistoryViewModel", e.toString())
            }
        }
    }

    fun updatePenaltyHistory(): Boolean {
        apiResponse.value = RequestState.Loading
        viewModelScope.launch {
            try {
                if (verifyPenaltyHistory()) {

                    lastResponse.value = repository.updatePenaltyHistory(
                        penaltyReceived = createPenaltyHistory()
                    )
                    apiResponse.value = RequestState.Success
                    Log.d("PenaltyHistoryViewModel", lastResponse.toString())
                }
            } catch (e: Exception) {
                apiResponse.value = RequestState.Error
                Log.d("PenaltyHistoryViewModel", e.toString())
            }
        }

        return if(lastResponse.value.success) {
            getAllPenaltyHistory()
            true
        } else
            false
    }

    fun deletePenaltyHistory(): Boolean {
        apiResponse.value = RequestState.Loading
        viewModelScope.launch {
            try {
                lastResponse.value = repository.deletePenaltyHistory(penaltyHistoryId = penaltyHistoryUiState.value.id)
                apiResponse.value = RequestState.Success
                Log.d("PenaltyHistoryViewModel", lastResponse.toString())
            } catch (e: Exception) {
                apiResponse.value = RequestState.Error
                lastResponse.value = ApiResponse(success = false, error = e)
                Log.d("PenaltyHistoryViewModel", e.toString())
            }
        }

        return if(lastResponse.value.success) {
            getAllPenaltyHistory()
            true
        } else
            false
    }

    fun onPenaltyHistoryUiEvent(event: PenaltyHistoryUiEvent) {
        when (event) {
            is PenaltyHistoryUiEvent.PenaltyIdChanged -> {
                // Compare id from penalty list with id from event. Only if id's match copy values
                penalties.value.forEach { penalty ->
                    if (penalty.id == event.penaltyId) {
                        penaltyHistoryUiState.value = penaltyHistoryUiState.value.copy(
                            penaltyId = event.penaltyId,
                            penaltyValue = penalty.value,
                            penaltyIsBeer = penalty.isBeer
                        )
                    }
                }
            }

            is PenaltyHistoryUiEvent.PlayerIdChanged -> {
                penaltyHistoryUiState.value = penaltyHistoryUiState.value.copy(
                    playerId = event.playerId
                )
            }

            is PenaltyHistoryUiEvent.TimeOfPenaltyChanged -> {
                penaltyHistoryUiState.value = penaltyHistoryUiState.value.copy(
                    timeOfPenalty = event.timeOfPenalty
                )
            }

            is PenaltyHistoryUiEvent.PenaltyPaidChanged -> {
                penaltyHistoryUiState.value = penaltyHistoryUiState.value.copy(
                    penaltyPaid = event.penaltyPaid
                )
                updatePenaltyHistory()
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
                getPenaltyHistoryBySearch()
            }
        }
    }

    fun resetPenaltyHistory() {
        penaltyHistoryUiState.value = PenaltyHistoryUiState()
    }

    fun resetLastResponse() {
        lastResponse.value = ApiResponse()
    }

}
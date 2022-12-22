package de.vexxes.penaltycatalog.viewmodels

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import de.vexxes.penaltycatalog.domain.model.ApiResponse
import de.vexxes.penaltycatalog.domain.model.Penalty
import de.vexxes.penaltycatalog.domain.model.PenaltyHistory
import de.vexxes.penaltycatalog.domain.model.Player
import de.vexxes.penaltycatalog.domain.model.toValue
import de.vexxes.penaltycatalog.domain.repository.Repository
import de.vexxes.penaltycatalog.domain.uievent.PenaltyHistoryUiEvent
import de.vexxes.penaltycatalog.domain.uievent.SearchUiEvent
import de.vexxes.penaltycatalog.domain.uistate.PenaltyHistoryUiState
import de.vexxes.penaltycatalog.domain.uistate.SearchUiState
import de.vexxes.penaltycatalog.util.RequestState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class PenaltyHistoryViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    val penaltyHistory: MutableState<List<PenaltyHistory>> = mutableStateOf(emptyList())
    var penalties: List<Penalty> = emptyList()
    var players: List<Player> = emptyList()

    var penaltyHistoryUiState: MutableState<PenaltyHistoryUiState> = mutableStateOf(PenaltyHistoryUiState())
        private set

    var searchUiState: MutableState<SearchUiState> = mutableStateOf(SearchUiState())
        private set

    var apiResponse: MutableState<RequestState<ApiResponse>> = mutableStateOf(RequestState.Idle)
    var lastResponse: MutableState<ApiResponse> = mutableStateOf(ApiResponse())

    init {
        getAllPenaltyHistory()
        getAllPenalties()
        getAllPlayers()
    }

    private fun convertResponseToPenaltyHistory(penaltyHistory: PenaltyHistory) {
        penaltyHistoryUiState.value = penaltyHistoryUiState.value.copy(
            id = penaltyHistory._id,
            penaltyName = penaltyHistory.penaltyName,
            playerName = penaltyHistory.playerName,
            penaltyValue = penaltyHistory.penaltyValue.toString(),
            penaltyIsBeer = penaltyHistory.penaltyIsBeer,
            timeOfPenalty = penaltyHistory.timeOfPenalty,
            penaltyPaid = penaltyHistory.penaltyPaid
        )
    }

    private fun createPenaltyHistory(): PenaltyHistory {
        return PenaltyHistory(
            _id = penaltyHistoryUiState.value.id,
            penaltyName = penaltyHistoryUiState.value.penaltyName,
            playerName = penaltyHistoryUiState.value.playerName,
            penaltyValue = penaltyHistoryUiState.value.penaltyValue.toInt(),
            penaltyIsBeer = penaltyHistoryUiState.value.penaltyIsBeer,
            timeOfPenalty = penaltyHistoryUiState.value.timeOfPenalty,
            penaltyPaid = penaltyHistoryUiState.value.penaltyPaid
        )
    }

    private fun getAllPenalties() {
        apiResponse.value = RequestState.Loading

        viewModelScope.launch {
            try {
                val response = withContext(Dispatchers.IO) {
                    repository.getAllPenalties()
                }

                apiResponse.value = RequestState.Success(response)

                if(response.penalty != null) {
                    penalties = response.penalty
                }
                Log.d("PenaltyViewModel", response.toString())
            }
            catch (e: Exception) {
                apiResponse.value = RequestState.Error(e)
                Log.d("PenaltyViewModel", e.toString())
            }
        }
    }

    private fun getAllPlayers() {
        apiResponse.value = RequestState.Loading

        viewModelScope.launch {
            try {
                val response = withContext(Dispatchers.IO) {
                    repository.getAllPlayers(sortOrder = searchUiState.value.sortOrder.toValue())
                }

                apiResponse.value = RequestState.Success(response)

                if(response.player != null) {
                    players = response.player
                }
                Log.d("PlayerViewModel", response.toString())
            }
            catch (e: Exception) {
                apiResponse.value = RequestState.Error(e)
                Log.d("PlayerViewModel", e.toString())
            }
        }
    }

    /*TODO Implement logic*/
    private fun verifyPenaltyHistory(): Boolean {

        return false
    }

    fun getAllPenaltyHistory() {
        apiResponse.value = RequestState.Loading

        viewModelScope.launch {
            try {
                val response = withContext(Dispatchers.IO) {
                    repository.getAllPenaltyHistory()
                }

                apiResponse.value = RequestState.Success(response)

                if (response.penaltyHistory != null) {
                    penaltyHistory.value = response.penaltyHistory
                }
                Log.d("PenaltyHistoryViewModel", response.toString())
            } catch (e: Exception) {
                apiResponse.value = RequestState.Error(e)
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
                apiResponse.value = RequestState.Success(response)

                if (response.penaltyHistory != null) {
                    convertResponseToPenaltyHistory(penaltyHistory = response.penaltyHistory.first())
                } else {
                    penaltyHistoryUiState.value = PenaltyHistoryUiState()
                }
                Log.d("PenaltyHistoryViewModel", response.toString())
            }
            catch (e: Exception) {
                apiResponse.value = RequestState.Error(e)
                Log.d("PenaltyHistoryViewModel", e.toString())
            }
        }
    }

    fun getPenaltyHistoryBySearch() {
        apiResponse.value = RequestState.Loading

        viewModelScope.launch {
            try {
                val response = withContext(Dispatchers.IO) {
                    repository.getPenaltyHistoryBySearch(searchText = searchUiState.value.searchText)
                }
                apiResponse.value = RequestState.Success(response)

                if(response.penaltyHistory != null) {
                    penaltyHistory.value = response.penaltyHistory
                }
                Log.d("PenaltyHistoryViewModel", response.toString())
            }
            catch (e: Exception) {
                apiResponse.value = RequestState.Error(e)
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
                        penaltyHistory = createPenaltyHistory()
                    )
                    apiResponse.value = RequestState.Success(lastResponse.value)
                    Log.d("PenaltyHistoryViewModel", lastResponse.toString())
                }
            } catch (e: Exception) {
                apiResponse.value = RequestState.Error(e)
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
                apiResponse.value = RequestState.Success(lastResponse.value)
                Log.d("PenaltyHistoryViewModel", lastResponse.toString())
            } catch (e: Exception) {
                apiResponse.value = RequestState.Error(e)
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
            is PenaltyHistoryUiEvent.PenaltyNameChanged -> {
                penaltyHistoryUiState.value = penaltyHistoryUiState.value.copy(
                    penaltyName = event.penaltyName
                )
            }

            is PenaltyHistoryUiEvent.PlayerNameChanged -> {
                penaltyHistoryUiState.value = penaltyHistoryUiState.value.copy(
                    playerName = event.playerName
                )
            }

            is PenaltyHistoryUiEvent.PenaltyValueChanged -> {
                penaltyHistoryUiState.value = penaltyHistoryUiState.value.copy(
                    penaltyValue = event.penaltyValue
                )
            }

            is PenaltyHistoryUiEvent.PenaltyIsBeerChanged -> {
                penaltyHistoryUiState.value = penaltyHistoryUiState.value.copy(
                    penaltyIsBeer = event.penaltyIsBeer
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
            }
        }
    }

    fun onSearchUiEvent(event: SearchUiEvent) {
        when (event) {
            is SearchUiEvent.SortOrderChanged -> {
                searchUiState.value = searchUiState.value.copy(
                    sortOrder = event.sortOrder
                )
                getAllPenaltyHistory()
            }

            is SearchUiEvent.SearchAppBarStateChanged -> {
                searchUiState.value = searchUiState.value.copy(
                    searchAppBarState = event.searchAppBarState
                )
            }

            is SearchUiEvent.SearchTextChanged -> {
                searchUiState.value = searchUiState.value.copy(
                    searchText = event.searchText
                )
                getAllPenaltyHistory()
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
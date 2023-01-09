package de.vexxes.penaltycatalog.viewmodels

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import de.vexxes.penaltycatalog.domain.model.ApiResponse
import de.vexxes.penaltycatalog.domain.model.Penalty
import de.vexxes.penaltycatalog.domain.repository.Repository
import de.vexxes.penaltycatalog.domain.uievent.PenaltyTypeUiEvent
import de.vexxes.penaltycatalog.domain.uievent.SearchUiEvent
import de.vexxes.penaltycatalog.domain.uistate.PenaltyTypeUiState
import de.vexxes.penaltycatalog.domain.uistate.SearchUiState
import de.vexxes.penaltycatalog.util.RequestState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class PenaltyTypeViewModel @Inject constructor(
    private val repository: Repository
): ViewModel() {
    var penalties: MutableState<List<Penalty>> = mutableStateOf(emptyList())
        private set

    var penaltyTypeUiState: MutableState<PenaltyTypeUiState> = mutableStateOf(PenaltyTypeUiState())
        private set

    var searchUiState: MutableState<SearchUiState> = mutableStateOf(SearchUiState())
        private set

    var apiResponse: MutableState<RequestState> = mutableStateOf(RequestState.Idle)
    var lastResponse: MutableState<ApiResponse> = mutableStateOf(ApiResponse())

    init {
        getAllPenalties()
    }

    private fun convertResponseToPenalty(penalty: Penalty) {
        penaltyTypeUiState.value = penaltyTypeUiState.value.copy(
            id = penalty.id,
            name = penalty.name,
            value = penalty.value,
            description = penalty.description,
            isBeer = penalty.isBeer
        )
    }

    private fun createPenalty(): Penalty {
        return Penalty(
            id = penaltyTypeUiState.value.id,
            name = penaltyTypeUiState.value.name,
            description = penaltyTypeUiState.value.description,
            isBeer = penaltyTypeUiState.value.isBeer,
            value = penaltyTypeUiState.value.value
        )
    }

    private fun getDeclaredPenalties() {
        apiResponse.value = RequestState.Loading

        viewModelScope.launch {
            try {
                val response = withContext(Dispatchers.IO) {
                    repository.getDeclaredPenalties(penaltyName = penaltyTypeUiState.value.name)
                }
                apiResponse.value = RequestState.Success

                if (response.message != null) {
                    penaltyTypeUiState.value = penaltyTypeUiState.value.copy(
                        valueDeclaredPenalties = response.message
                    )
                } else {
                    penaltyTypeUiState.value = penaltyTypeUiState.value.copy(
                        valueDeclaredPenalties = "0"
                    )
                }
                Log.d("PenaltyViewModel", response.toString())
            }
            catch (e: Exception) {
                apiResponse.value = RequestState.Error
                Log.d("PenaltyViewModel", e.toString())
            }
        }
    }

    private fun verifyPenalty(): Boolean {
        val nameResult = penaltyTypeUiState.value.name.isEmpty()
        val valueResult = penaltyTypeUiState.value.value.isEmpty()

        penaltyTypeUiState.value = penaltyTypeUiState.value.copy(
            nameError = nameResult,
            valueError = valueResult
        )

        return !(nameResult || valueResult)
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

    fun getPenaltyById(penaltyId: String) {
        apiResponse.value = RequestState.Loading

        viewModelScope.launch {
            try {
                val response = withContext(Dispatchers.IO) {
                    repository.getPenaltyById(penaltyId = penaltyId)
                }
                apiResponse.value = RequestState.Success

                if (response.penalty != null) {
                    convertResponseToPenalty(penalty = response.penalty.first())
                } else {
                    penaltyTypeUiState.value = PenaltyTypeUiState()
                }
                Log.d("PenaltyViewModel", response.toString())

                getDeclaredPenalties()
            }
            catch (e: Exception) {
                apiResponse.value = RequestState.Error
                Log.d("PenaltyViewModel", e.toString())
            }
        }
    }

    private fun getPenaltiesBySearch() {
        apiResponse.value = RequestState.Loading

        viewModelScope.launch {
            try {
                val response = withContext(Dispatchers.IO) {
                    repository.getPenaltiesBySearch(searchText = searchUiState.value.searchText)
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

    fun updatePenalty(): Boolean {
        apiResponse.value = RequestState.Loading
        viewModelScope.launch {
            try {
                if (verifyPenalty()) {

                    lastResponse.value = repository.updatePenalty(
                        penalty = createPenalty()
                    )
                    apiResponse.value = RequestState.Success
                    Log.d("PenaltyViewModel", lastResponse.toString())
                }
            } catch (e: Exception) {
                apiResponse.value = RequestState.Error
                Log.d("PenaltyViewModel", e.toString())
            }
        }

        return if(lastResponse.value.success) {
            getAllPenalties()
            true
        } else {
            false
        }
    }

    fun deletePenalty(): Boolean {
        apiResponse.value = RequestState.Loading
        viewModelScope.launch {
            try {
                lastResponse.value = repository.deletePenalty(penaltyId = penaltyTypeUiState.value.id)
                apiResponse.value = RequestState.Success
                Log.d("PenaltyViewModel", lastResponse.toString())
            } catch (e: Exception) {
                apiResponse.value = RequestState.Error
                lastResponse.value = ApiResponse(success = false, error = e)
                Log.d("PenaltyViewModel", e.toString())
            }
        }

        return if(lastResponse.value.success) {
            getAllPenalties()
            true
        } else
            false
    }

    fun onPenaltyUiEvent(event: PenaltyTypeUiEvent) {
        when(event) {
            is PenaltyTypeUiEvent.NameChanged -> {
                penaltyTypeUiState.value = penaltyTypeUiState.value.copy(
                    name = event.name
                )
            }

            is PenaltyTypeUiEvent.DescriptionChanged -> {
                penaltyTypeUiState.value = penaltyTypeUiState.value.copy(
                    description = event.description
                )
            }

            is PenaltyTypeUiEvent.IsBeerChanged -> {
                penaltyTypeUiState.value = penaltyTypeUiState.value.copy(
                    isBeer = event.isBeer
                )
            }

            is PenaltyTypeUiEvent.ValueChanged -> {
                penaltyTypeUiState.value = penaltyTypeUiState.value.copy(
                    value = event.value
                )
            }
        }
    }

    fun onSearchUiEvent(event: SearchUiEvent) {
        when(event) {
            is SearchUiEvent.SearchAppBarStateChanged -> {
                searchUiState.value = searchUiState.value.copy(
                    searchAppBarState = event.searchAppBarState
                )
            }

            is SearchUiEvent.SearchTextChanged -> {
                searchUiState.value = searchUiState.value.copy(
                    searchText = event.searchText
                )
                getPenaltiesBySearch()
            }
        }
    }

    fun resetPenalty() {
        penaltyTypeUiState.value = PenaltyTypeUiState()
    }

    fun resetLastResponse() {
        lastResponse.value = ApiResponse()
    }
}
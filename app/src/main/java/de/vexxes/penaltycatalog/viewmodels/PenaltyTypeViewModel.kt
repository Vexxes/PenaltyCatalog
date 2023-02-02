package de.vexxes.penaltycatalog.viewmodels

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import de.vexxes.penaltycatalog.domain.model.PenaltyType
import de.vexxes.penaltycatalog.domain.model.convertToPenaltyTypeUiState
import de.vexxes.penaltycatalog.domain.repository.PenaltyReceivedRepository
import de.vexxes.penaltycatalog.domain.repository.PenaltyTypeRepository
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
    private val penaltyTypeRepository: PenaltyTypeRepository,
    private val penaltyReceivedRepository: PenaltyReceivedRepository
): ViewModel() {
    var penalties: MutableState<List<PenaltyType>> = mutableStateOf(emptyList())
        private set

    var penaltyTypeUiState: MutableState<PenaltyTypeUiState> = mutableStateOf(PenaltyTypeUiState())
        private set

    var searchUiState: MutableState<SearchUiState> = mutableStateOf(SearchUiState())
        private set

    var requestState: MutableState<RequestState> = mutableStateOf(RequestState.Idle)

    var postPenaltyType: MutableState<Boolean> = mutableStateOf(false)
    var updatePenaltyType: MutableState<Boolean> = mutableStateOf(false)

    init {
        getAllPenalties()
    }

    private fun convertResponseToPenaltyType(penaltyType: PenaltyType) {
        penaltyTypeUiState.value = penaltyType.convertToPenaltyTypeUiState()
    }

    private fun createPenaltyType(): PenaltyType {
        return PenaltyType(
            id = penaltyTypeUiState.value.id,
            name = penaltyTypeUiState.value.name,
            description = penaltyTypeUiState.value.description,
            isBeer = penaltyTypeUiState.value.isBeer,
            value = penaltyTypeUiState.value.value.toDouble()
        )
    }

    private fun getDeclaredPenalties() {
        var sum = 0

        viewModelScope.launch {
            try {
                val response = withContext(Dispatchers.IO) {
                    penaltyReceivedRepository.getAllPenaltyReceived()
                }

                if (response.isNotEmpty()) {
                    response.forEach { penaltyReceived ->
                        sum += if (penaltyTypeUiState.value.id == penaltyReceived.penaltyTypeId) 1 else 0
                    }
                }

                penaltyTypeUiState.value = penaltyTypeUiState.value.copy(
                    valueDeclaredPenalties = sum
                )

                Log.d("PenaltyTypeViewModel", response.toString())
            }
            catch (e: Exception) {
                requestState.value = RequestState.Error
                Log.d("PenaltyTypeViewModel", e.toString())
            }
        }
    }

    private fun verifyPenaltyType(): Boolean {
        val nameResult = penaltyTypeUiState.value.name.isEmpty()
        val valueResult = penaltyTypeUiState.value.value.isEmpty()

        penaltyTypeUiState.value = penaltyTypeUiState.value.copy(
            nameError = nameResult,
            valueError = valueResult
        )

        return !(nameResult || valueResult)
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
                Log.d("PenaltyTypeViewModel", response.toString())
            }
            catch (e: Exception) {
                requestState.value = RequestState.Error
                Log.d("PenaltyTypeViewModel", e.toString())
            }
        }
    }

    fun getPenaltyTypeById(penaltyTypeId: String) {
        requestState.value = RequestState.Loading

        viewModelScope.launch {
            try {
                val response = withContext(Dispatchers.IO) {
                    penaltyTypeRepository.getPenaltyTypeById(penaltyTypeId = penaltyTypeId)
                }

                if (response != null) {
                    requestState.value = RequestState.Success
                    convertResponseToPenaltyType(penaltyType = response)
                    getDeclaredPenalties()
                } else {
                    penaltyTypeUiState.value = PenaltyTypeUiState()
                }

                Log.d("PenaltyTypeViewModel", response.toString())
            }
            catch (e: Exception) {
                requestState.value = RequestState.Error
                Log.d("PenaltyTypeViewModel", e.toString())
            }
        }
    }

    private fun getPenaltiesBySearch() {
        requestState.value = RequestState.Loading

        viewModelScope.launch {
            try {
                val response = withContext(Dispatchers.IO) {
                    penaltyTypeRepository.getPenaltyTypesBySearch(name = searchUiState.value.searchText)
                }

                if (response != null) {
                    requestState.value = RequestState.Success
                    penalties.value = response
                }

                Log.d("PenaltyTypeViewModel", response.toString())
            }
            catch (e: Exception) {
                requestState.value = RequestState.Error
                Log.d("PenaltyTypeViewModel", e.toString())
            }
        }
    }

    fun postPenaltyType() {
        requestState.value = RequestState.Loading

        viewModelScope.launch {
            try {
                if (verifyPenaltyType()) {
                    val response = withContext(Dispatchers.IO) {
                        penaltyTypeRepository.postPenaltyType(
                            penaltyType = createPenaltyType()
                        )
                    }

                    if (response != null) {
                        requestState.value = RequestState.Success
                        postPenaltyType.value = true
                    }

                    Log.d("PenaltyTypeViewModel", response.toString())
                }
            } catch (e: Exception) {
                requestState.value = RequestState.Error
                Log.d("PenaltyTypeViewModel", e.toString())
            }
        }
    }

    fun updatePenalty() {
        requestState.value = RequestState.Loading

        viewModelScope.launch {
            try {
                if (verifyPenaltyType()) {
                    val response = withContext(Dispatchers.IO) {
                        penaltyTypeRepository.updatePenaltyType(
                            id = penaltyTypeUiState.value.id,
                            penaltyType = createPenaltyType()
                        )
                    }

                    if (response) {
                        requestState.value = RequestState.Success
                        updatePenaltyType.value = true
                    }

                    Log.d("PenaltyTypeViewModel", "${penaltyTypeUiState.value.id} ${penaltyTypeUiState.value.name} successfully updated")
                }
            } catch (e: Exception) {
                requestState.value = RequestState.Error
                Log.d("PenaltyTypeViewModel", e.toString())
            }
        }
    }

    fun deletePenalty() {
        requestState.value = RequestState.Loading

        viewModelScope.launch {
            try {
                val response = withContext(Dispatchers.IO) {
                    penaltyTypeRepository.deletePenaltyType(
                        penaltyTypeId = penaltyTypeUiState.value.id
                    )
                }

                if (response) {
                    requestState.value = RequestState.Success
                } else {
                    requestState.value = RequestState.Idle
                }

                Log.d("PenaltyTypeViewModel", "${penaltyTypeUiState.value.id} ${penaltyTypeUiState.value.name} successfully deleted")
            } catch (e: Exception) {
                requestState.value = RequestState.Error
                Log.d("PenaltyTypeViewModel", e.toString())
            }
        }
    }

    fun resetPenaltyTypeUiState() {
        penaltyTypeUiState.value = PenaltyTypeUiState()
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
}
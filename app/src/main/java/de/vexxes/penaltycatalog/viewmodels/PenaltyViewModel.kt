package de.vexxes.penaltycatalog.viewmodels

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import de.vexxes.penaltycatalog.domain.model.ApiResponse
import de.vexxes.penaltycatalog.domain.model.Penalty
import de.vexxes.penaltycatalog.domain.model.PenaltyCategory
import de.vexxes.penaltycatalog.domain.repository.Repository
import de.vexxes.penaltycatalog.domain.uievent.PenaltyUiEvent
import de.vexxes.penaltycatalog.domain.uievent.SearchUiEvent
import de.vexxes.penaltycatalog.domain.uistate.PenaltyUiState
import de.vexxes.penaltycatalog.domain.uistate.SearchUiState
import de.vexxes.penaltycatalog.util.RequestState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class PenaltyViewModel @Inject constructor(
    private val repository: Repository
): ViewModel() {

    var categories: MutableState<List<PenaltyCategory>> = mutableStateOf(emptyList())
        private set

    var penalties: MutableState<List<Penalty>> = mutableStateOf(emptyList())
        private set

    var penaltyUiState: MutableState<PenaltyUiState> = mutableStateOf(PenaltyUiState())
        private set

    var searchUiState: MutableState<SearchUiState> = mutableStateOf(SearchUiState())
        private set

    var apiResponse: MutableState<RequestState<ApiResponse>> = mutableStateOf(RequestState.Idle)
    var lastResponse: MutableState<ApiResponse> = mutableStateOf(ApiResponse())

    init {
        getAllCategories()
        getAllPenalties()
    }

    private fun convertResponseToPenalty(penalty: Penalty) {
        penaltyUiState.value = penaltyUiState.value.copy(
            id = penalty._id,
            name = penalty.name,
            categoryName = penalty.categoryName,
            value = penalty.value,
            description = penalty.description,
            isBeer = penalty.isBeer
        )
    }

    private fun createPenalty(): Penalty {
        return Penalty(
            _id = penaltyUiState.value.id,
            name = penaltyUiState.value.name,
            categoryName = penaltyUiState.value.categoryName,
            description = penaltyUiState.value.description,
            isBeer = penaltyUiState.value.isBeer,
            value = penaltyUiState.value.value
        )
    }

    private fun verifyPenalty(): Boolean {
        val nameResult = penaltyUiState.value.name.isEmpty()
        val valueResult = penaltyUiState.value.value.isEmpty()

        penaltyUiState.value = penaltyUiState.value.copy(
            nameError = nameResult,
            valueError = valueResult
        )

        return !(nameResult || valueResult)
    }

    private fun getAllCategories() {
        apiResponse.value = RequestState.Loading

        viewModelScope.launch {
            try {
                val response = withContext(Dispatchers.IO) {
                    repository.getAllCategories()
                }

                apiResponse.value = RequestState.Success(response)

                if(response.penaltyCategory != null) {
                    categories.value = response.penaltyCategory
                }
                Log.d("PenaltyViewModel", response.toString())
            }
            catch (e: Exception) {
                apiResponse.value = RequestState.Error(e)
                Log.d("PenaltyViewModel", e.toString())
            }
        }
    }

    fun getAllPenalties() {
        apiResponse.value = RequestState.Loading

        viewModelScope.launch {
            try {
                val response = withContext(Dispatchers.IO) {
                    repository.getAllPenalties()
                }

                apiResponse.value = RequestState.Success(response)

                if(response.penalty != null) {
                    penalties.value = response.penalty
                }
                Log.d("PenaltyViewModel", response.toString())
            }
            catch (e: Exception) {
                apiResponse.value = RequestState.Error(e)
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
                apiResponse.value = RequestState.Success(response)

                if (response.penalty != null) {
                    convertResponseToPenalty(penalty = response.penalty.first())
                } else {
                    penaltyUiState.value = PenaltyUiState()
                }
                Log.d("PenaltyViewModel", response.toString())
            }
            catch (e: Exception) {
                apiResponse.value = RequestState.Error(e)
                Log.d("PenaltyViewModel", e.toString())
            }
        }
    }

    fun getPenaltiesBySearch() {
        apiResponse.value = RequestState.Loading

        viewModelScope.launch {
            try {
                val response = withContext(Dispatchers.IO) {
                    repository.getPenaltiesBySearch(searchText = searchUiState.value.searchText)
                }
                apiResponse.value = RequestState.Success(response)

                if(response.penalty != null) {
                    penalties.value = response.penalty
                }
                Log.d("PenaltyViewModel", response.toString())
            }
            catch (e: Exception) {
                apiResponse.value = RequestState.Error(e)
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
                    apiResponse.value = RequestState.Success(lastResponse.value)
                    Log.d("PenaltyViewModel", lastResponse.toString())
                }
            } catch (e: Exception) {
                apiResponse.value = RequestState.Error(e)
                Log.d("PenaltyViewModel", e.toString())
            }
        }

        return if(lastResponse.value.success) {
            getAllPenalties()
            true
        } else
            false
    }

    fun deletePenalty(): Boolean {
        apiResponse.value = RequestState.Loading
        viewModelScope.launch {
            try {
                lastResponse.value = repository.deletePenalty(penaltyId = penaltyUiState.value.id)
                apiResponse.value = RequestState.Success(lastResponse.value)
                Log.d("PenaltyViewModel", lastResponse.toString())
            } catch (e: Exception) {
                apiResponse.value = RequestState.Error(e)
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

    fun onPenaltyUiEvent(event: PenaltyUiEvent) {
        when(event) {
            is PenaltyUiEvent.NameChanged -> {
                penaltyUiState.value = penaltyUiState.value.copy(
                    name = event.name
                )
            }

            is PenaltyUiEvent.CategoryNameChanged -> {
                penaltyUiState.value = penaltyUiState.value.copy(
                    categoryName = event.categoryName
                )
            }

            is PenaltyUiEvent.DescriptionChanged -> {
                penaltyUiState.value = penaltyUiState.value.copy(
                    description = event.description
                )
            }

            is PenaltyUiEvent.IsBeerChanged -> {
                penaltyUiState.value = penaltyUiState.value.copy(
                    isBeer = event.isBeer
                )
            }

            is PenaltyUiEvent.ValueChanged -> {
                penaltyUiState.value = penaltyUiState.value.copy(
                    value = event.value //number.toFloat()
                )
/*
                println("EventValue: ${event.value} \r\n")
                val retVal = "%.2f".format(event.value.toFloat() / 100)
                println("EventValue: ${event.value} \r\n")
                println("EventValue Formatted: $retVal \r\n")
*/

/*                penaltyUiState.value = penaltyUiState.value.copy(
                    value = retVal.toFloat() //number.toFloat()
                )*/

                /*                val number = event.value.filter { it.isDigit() }



                                penaltyUiState.value = penaltyUiState.value.copy(
                                    value = retVal //number.toFloat()
                                )*/
            }
        }
    }

    fun onSearchUiEvent(event: SearchUiEvent) {
        when(event) {
            is SearchUiEvent.SortOrderChanged -> {
                searchUiState.value = searchUiState.value.copy(
                    sortOrder = event.sortOrder
                )
                getAllPenalties()
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
                getPenaltiesBySearch()
            }
        }
    }

    fun resetPenalty() {
        penaltyUiState.value = PenaltyUiState()
    }

    fun resetLastResponse() {
        lastResponse.value = ApiResponse()
    }
}
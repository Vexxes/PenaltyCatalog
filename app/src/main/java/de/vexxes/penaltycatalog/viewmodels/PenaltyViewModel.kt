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
import de.vexxes.penaltycatalog.util.RequestState
import de.vexxes.penaltycatalog.util.SearchAppBarState
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

    var penalty: MutableState<Penalty> = mutableStateOf(Penalty())
        private set

    val id: MutableState<String> = mutableStateOf("")

    var penaltyName: MutableState<String> = mutableStateOf("")
        private set

    var penaltyCategoryName: MutableState<String> = mutableStateOf("")
        private set

    var penaltyDescription: MutableState<String> = mutableStateOf("")
        private set

    var isBeer: MutableState<Boolean> = mutableStateOf(false)
        private set

    var penaltyAmount: MutableState<String> = mutableStateOf("")
        private set

    var searchAppBarState: MutableState<SearchAppBarState> = mutableStateOf(SearchAppBarState.CLOSED)
        private set

    var searchText: MutableState<String> = mutableStateOf("")
        private set

    var apiResponse: MutableState<RequestState<ApiResponse>> = mutableStateOf(RequestState.Idle)
    var lastResponse: MutableState<ApiResponse> = mutableStateOf(ApiResponse())

    var nameError: MutableState<Boolean> = mutableStateOf(false)
    var amountError: MutableState<Boolean> = mutableStateOf(false)

    init {
        getAllCategories()
        getAllPenalties()
    }

    fun getAllCategories() {
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
                Log.d("PenaltyViewModelError", apiResponse.toString())

                if (response.penalty != null) {
                    convertResponseToPenalty(penalty = response.penalty.first())
                } else {
                    penalty.value = Penalty()
                }
            }
            catch (e: Exception) {
                apiResponse.value = RequestState.Error(e)
                Log.d("PenaltyViewModelError", e.toString())
            }
        }
    }

    fun getPenaltiesBySearch() {
        apiResponse.value = RequestState.Loading

        viewModelScope.launch {
            try {
                val response = withContext(Dispatchers.IO) {
                    repository.getPenaltiesBySearch(searchText = searchText.value)
                }
                apiResponse.value = RequestState.Success(response)

                if(response.penalty != null) {
                    penalties.value = response.penalty
                }
                Log.d("GetPenaltiesBySearch", response.toString())
            }
            catch (e: Exception) {
                apiResponse.value = RequestState.Error(e)
                Log.d("PenaltyViewModelError", e.toString())
            }
        }
    }

    fun updatePenalty(): Boolean {
        apiResponse.value = RequestState.Loading
        viewModelScope.launch {
            try {
                if (verifyPenalty()) {

                    val locPenalty = Penalty(
                        _id = id.value,
                        name = penaltyName.value,
                        nameOfCategory = penaltyCategoryName.value,
                        isBeer = isBeer.value,
                        value = penaltyAmount.value.toInt()
                    )

                    lastResponse.value = repository.updatePenalty(
                        penalty = locPenalty
                    )
                    apiResponse.value = RequestState.Success(lastResponse.value)
                }
            } catch (e: Exception) {
                apiResponse.value = RequestState.Error(e)
                Log.d("PenaltyViewModelError", e.toString())
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
                lastResponse.value = repository.deletePenalty(penaltyId = id.value)
                apiResponse.value = RequestState.Success(lastResponse.value)
            } catch (e: Exception) {
                apiResponse.value = RequestState.Error(e)
                lastResponse.value = ApiResponse(success = false, error = e)
                Log.d("PenaltyViewModelError", e.toString())
            }
        }

        return if(lastResponse.value.success) {
            getAllPenalties()
            true
        } else
            false
    }

    private fun verifyPenalty(): Boolean {
        nameError.value = penaltyName.value.isEmpty()
        amountError.value = penaltyAmount.value.isEmpty()

        return !(nameError.value || amountError.value)
    }

    fun resetPenalty() {
        id.value = ""
        penaltyName.value = ""
        penaltyCategoryName.value = ""
        isBeer.value = false
        penaltyAmount.value = ""
    }

    private fun convertResponseToPenalty(penalty: Penalty) {
        this.penalty.value = penalty
        id.value = penalty._id
        penaltyName.value = penalty.name
        penaltyCategoryName.value = penalty.nameOfCategory
        penaltyDescription.value = penalty.description
        penaltyAmount.value = penalty.value.toString()
        isBeer.value = penalty.isBeer
    }

    fun resetLastResponse() {
        lastResponse.value = ApiResponse()
    }
}
package de.vexxes.penaltycatalog.viewmodels

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import de.vexxes.penaltycatalog.domain.model.ApiResponse
import de.vexxes.penaltycatalog.domain.model.Player
import de.vexxes.penaltycatalog.domain.repository.Repository
import de.vexxes.penaltycatalog.util.RequestState
import de.vexxes.penaltycatalog.util.SearchAppBarState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class PlayerViewModel @Inject constructor(
    private val repository: Repository
): ViewModel() {

    private val _players: MutableState<List<Player>> = mutableStateOf(emptyList())
    val players: State<List<Player>> = _players

    private val _number: MutableState<Int> = mutableStateOf(0)
    val number: State<Int> = _number

    private val _firstName: MutableState<String> = mutableStateOf("")
    val firstName: State<String> = _firstName

    private val _lastName: MutableState<String> = mutableStateOf("")
    val lastName: State<String> = _lastName

    private val _zipcode: MutableState<Int> = mutableStateOf(0)
    val zipcode: State<Int> = _zipcode

    private val _city: MutableState<String> = mutableStateOf("")
    val city: State<String> = _city

    private val _playedGames: MutableState<Int> = mutableStateOf(0)
    val playedGames: State<Int> = _playedGames

    private val _goals: MutableState<Int> = mutableStateOf(0)
    val goals: State<Int> = _goals

    private val _yellowCards: MutableState<Int> = mutableStateOf(0)
    val yellowCards: State<Int> = _yellowCards

    private val _twoMinutes: MutableState<Int> = mutableStateOf(0)
    val twoMinutes: State<Int> = _twoMinutes

    private val _redCards: MutableState<Int> = mutableStateOf(0)
    val redCards: State<Int> = _redCards

    val searchAppBarState: MutableState<SearchAppBarState> = mutableStateOf(SearchAppBarState.CLOSED)
    val searchTextState: MutableState<String> = mutableStateOf("")

    private val _apiResponse: MutableState<RequestState<ApiResponse>> = mutableStateOf(RequestState.Idle)
    val apiResponsePlayer: State<RequestState<ApiResponse>> = _apiResponse

    init {
        getAllPlayers()
    }

    fun getAllPlayers() {
        _apiResponse.value = RequestState.Loading

        viewModelScope.launch {
            try {
                val response = withContext(Dispatchers.IO) {
                    repository.getAllPlayers()
                }

                // TODO Create the correct response for get all players
                _apiResponse.value = RequestState.Success(response)
                Log.d("Response", response.toString())

                if(response.player != null) {
                    // TODO Get player list instead of a single player
                    _players.value = response.player
                    Log.d("Response", players.value.toString())
//                    _number.value = response.player.number
//                    _firstName.value = response.player.firstName
//                    _lastName.value = response.player.lastName
//                    _zipcode.value = response.player.zipcode
//                    _city.value = response.player.city
//                    _playedGames.value = response.player.playedGames
//                    _goals.value = response.player.goals
//                    _yellowCards.value = response.player.yellowCards
//                    _twoMinutes.value = response.player.twoMinutes
//                    _redCards.value = response.player.redCards
                }
            }
            catch (e: Exception) {
                _apiResponse.value = RequestState.Error(e)
            }
        }
    }
}
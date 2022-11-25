package de.vexxes.penaltycatalog.viewmodels

import android.util.Log
import androidx.compose.runtime.MutableState
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
import javax.inject.Inject

@HiltViewModel
class PlayerViewModel @Inject constructor(
    private val repository: Repository
): ViewModel() {

    var players: MutableState<List<Player>> = mutableStateOf(emptyList())
        private set

     var player: MutableState<Player> = mutableStateOf(Player())
         private set

     val id: MutableState<String> = mutableStateOf("")

    var number: MutableState<String> = mutableStateOf("")
        private set

    var firstName: MutableState<String> = mutableStateOf("")
        private set

    var lastName: MutableState<String> = mutableStateOf("")
        private set

    var birthday: MutableState<String> = mutableStateOf("")
        private set

    var street: MutableState<String> = mutableStateOf("")
        private set

    var zipcode: MutableState<String> = mutableStateOf("")
        private set

    var city: MutableState<String> = mutableStateOf("")
        private set

    var playedGames: MutableState<String> = mutableStateOf("")
        private set

    var goals: MutableState<String> = mutableStateOf("")
        private set

    var yellowCards: MutableState<String> = mutableStateOf("")
        private set

    var twoMinutes: MutableState<String> = mutableStateOf("")
        private set

    var redCards: MutableState<String> = mutableStateOf("")
        private set

    var searchAppBarState: MutableState<SearchAppBarState> = mutableStateOf(SearchAppBarState.CLOSED)
        private set

    var searchTextState: MutableState<String> = mutableStateOf("")
        private set

    private var apiResponse: MutableState<RequestState<ApiResponse>> = mutableStateOf(RequestState.Idle)
    private var lastResponse: MutableState<ApiResponse> = mutableStateOf(ApiResponse())

    init {
        getAllPlayers()
    }

    fun getAllPlayers() {
        apiResponse.value = RequestState.Loading

        viewModelScope.launch {
            try {
                val response = withContext(Dispatchers.IO) {
                    repository.getAllPlayers()
                }

                apiResponse.value = RequestState.Success(response)

                if(response.player != null) {
                    players.value = response.player
                }
            }
            catch (e: Exception) {
                apiResponse.value = RequestState.Error(e)
            }
        }
    }

    fun getPlayerById(playerId: String) {
        apiResponse.value = RequestState.Loading

        viewModelScope.launch {
            try {
                val response = withContext(Dispatchers.IO) {
                    repository.getPlayerById(playerId = playerId)
                }
                apiResponse.value = RequestState.Success(response)

                if (response.player != null) {
                    convertResponseToPlayer(player = response.player.first())
                } else {
                    player.value = Player()
                }
            }
            catch (e: Exception) {
                apiResponse.value = RequestState.Error(e)
            }
        }
    }

    fun updatePlayer(): Boolean {
        apiResponse.value = RequestState.Loading
        viewModelScope.launch {
            try {
                if (verifyPlayer()) {

                    val locPlayer = Player(
                        _id = id.value,
                        number = number.value.toInt(),
                        firstName = firstName.value,
                        lastName = lastName.value,
                        birthday = birthday.value,
                        street = street.value,
                        zipcode = if(zipcode.value.isNotEmpty()) zipcode.value.toInt() else 0,
                        city = city.value,
                        playedGames = if(playedGames.value.isNotEmpty()) playedGames.value.toInt() else 0,
                        goals = if(goals.value.isNotEmpty()) goals.value.toInt() else 0,
                        yellowCards = if(yellowCards.value.isNotEmpty()) yellowCards.value.toInt() else 0,
                        twoMinutes = if(twoMinutes.value.isNotEmpty()) twoMinutes.value.toInt() else 0,
                        redCards = if(redCards.value.isNotEmpty()) redCards.value.toInt() else 0
                    )

                    lastResponse.value = repository.updatePlayer(
                        player = locPlayer
                    )
                    apiResponse.value = RequestState.Success(lastResponse.value)

                }
            } catch (e: Exception) {
                apiResponse.value = RequestState.Error(e)
                Log.d("ApiResponse", e.toString())
            }
        }

        return if(lastResponse.value.success) {
            getAllPlayers()
            true
        } else
            false
    }

    fun deletePlayer(): Boolean {
        apiResponse.value = RequestState.Loading
        viewModelScope.launch {
            try {
                lastResponse.value = repository.deletePlayer(playerId = id.value)
                apiResponse.value = RequestState.Success(lastResponse.value)
            } catch (e: Exception) {
                apiResponse.value = RequestState.Error(e)
                lastResponse.value = ApiResponse(success = false)
            }
        }

        return if(lastResponse.value.success) {
            getAllPlayers()
            true
        } else
            false
    }

    private fun verifyPlayer(): Boolean {
        /*TODO Should have returned false, since the values were empty. Check again, otherwise multiple entries are inserted*/
        return (number.value.isNotEmpty() && firstName.value.isNotEmpty() && lastName.value.isNotEmpty())
    }

    fun resetPlayer() {
        player.value = Player()
        id.value = ""
        number.value = ""
        firstName.value = player.value.firstName
        lastName.value = player.value.lastName
        birthday.value = player.value.birthday
        street.value = player.value.street
        zipcode.value = ""
        city.value = player.value.city
        playedGames.value = ""
        goals.value = ""
        yellowCards.value = ""
        twoMinutes.value = ""
        redCards.value = ""
    }

    private fun convertResponseToPlayer(player: Player) {
        this.player.value = player
        id.value = player._id
        number.value = if(player.number > 0) player.number.toString() else ""
        firstName.value = player.firstName
        lastName.value = player.lastName
        birthday.value = player.birthday
        street.value = player.street
        zipcode.value = if(player.zipcode > 0) player.zipcode.toString() else ""
        city.value = player.city
        playedGames.value = if(player.playedGames > 0) player.playedGames.toString() else ""
        goals.value = if(player.goals > 0) player.goals.toString() else ""
        yellowCards.value = if(player.yellowCards > 0) player.yellowCards.toString() else ""
        twoMinutes.value = if(player.twoMinutes > 0) player.twoMinutes.toString() else ""
        redCards.value = if(player.redCards > 0) player.redCards.toString() else ""
    }
}
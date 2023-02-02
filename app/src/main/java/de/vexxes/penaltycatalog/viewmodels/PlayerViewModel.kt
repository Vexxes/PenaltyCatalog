package de.vexxes.penaltycatalog.viewmodels

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import de.vexxes.penaltycatalog.domain.model.Player
import de.vexxes.penaltycatalog.domain.repository.PenaltyReceivedRepository
import de.vexxes.penaltycatalog.domain.repository.PenaltyTypeRepository
import de.vexxes.penaltycatalog.domain.repository.PlayerRepository
import de.vexxes.penaltycatalog.domain.uievent.PlayerUiEvent
import de.vexxes.penaltycatalog.domain.uievent.SearchUiEvent
import de.vexxes.penaltycatalog.domain.uistate.PlayerUiState
import de.vexxes.penaltycatalog.domain.uistate.SearchUiState
import de.vexxes.penaltycatalog.util.RequestState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class PlayerViewModel @Inject constructor(
    private val playerRepository: PlayerRepository,
    private val penaltyTypeRepository: PenaltyTypeRepository,
    private val penaltyReceivedRepository: PenaltyReceivedRepository
): ViewModel() {

    var players: MutableState<List<Player>> = mutableStateOf(emptyList())
        private set

    var playerUiState: MutableState<PlayerUiState> = mutableStateOf(PlayerUiState())
        private set

    var searchUiState: MutableState<SearchUiState> = mutableStateOf(SearchUiState())
        private set

    var requestState: MutableState<RequestState> = mutableStateOf(RequestState.Idle)

    var postPlayer: MutableState<Boolean> = mutableStateOf(false)
    var updatePlayer: MutableState<Boolean> = mutableStateOf(false)


    init {
        getAllPlayers()
    }

    private fun convertResponseToPlayer(player: Player) {
        playerUiState.value = playerUiState.value.copy(
            id = player.id,
            number = if(player.number > 0) player.number.toString() else "",
            firstName = player.firstName,
            lastName = player.lastName,
            birthday = player.birthday,
            street = player.street,
            zipcode = if(player.zipcode > 0) player.zipcode.toString() else "",
            city = player.city,
            playedGames = if(player.playedGames > 0) player.playedGames.toString() else "",
            goals = if(player.goals > 0) player.goals.toString() else "",
            yellowCards = if(player.yellowCards > 0) player.yellowCards.toString() else "",
            twoMinutes = if(player.twoMinutes > 0) player.twoMinutes.toString() else "",
            redCards = if(player.redCards > 0) player.redCards.toString() else ""
        )
    }

    private fun createPlayer(): Player {
        return Player(
            number = playerUiState.value.number.toInt(),
            firstName = playerUiState.value.firstName,
            lastName = playerUiState.value.lastName,
            birthday = playerUiState.value.birthday,
            street = playerUiState.value.street,
            zipcode = if(playerUiState.value.zipcode.isNotEmpty()) playerUiState.value.zipcode.toInt() else 0,
            city = playerUiState.value.city,
            playedGames = if(playerUiState.value.playedGames.isNotEmpty()) playerUiState.value.playedGames.toInt() else 0,
            goals = if(playerUiState.value.goals.isNotEmpty()) playerUiState.value.goals.toInt() else 0,
            yellowCards = if(playerUiState.value.yellowCards.isNotEmpty()) playerUiState.value.yellowCards.toInt() else 0,
            twoMinutes = if(playerUiState.value.twoMinutes.isNotEmpty()) playerUiState.value.twoMinutes.toInt() else 0,
            redCards = if(playerUiState.value.redCards.isNotEmpty()) playerUiState.value.redCards.toInt() else 0
        )
    }

    private fun getPenaltyReceivedByPlayerId() {
        var sumPenalties = 0.0

        viewModelScope.launch {
            try {
                val response = withContext(Dispatchers.IO) {
                    penaltyReceivedRepository.getPenaltyReceivedByPlayerId(playerId = playerUiState.value.id)
                }

                response?.forEach { penaltyReceived ->
                    if (penaltyReceived.timeOfPenaltyPaid == null) {
                        val penaltyType = penaltyTypeRepository.getPenaltyTypeById(penaltyTypeId =  penaltyReceived.penaltyTypeId)

                        if (!penaltyType!!.isBeer) {
                            sumPenalties += penaltyType.value
                        }
                    }
                }

                playerUiState.value = playerUiState.value.copy(
                    sumPenalties = sumPenalties
                )

                Log.d("PlayerViewModel", response.toString())
            }
            catch (e: Exception) {
                requestState.value = RequestState.Error
                Log.d("PlayerViewModel", e.toString())
            }
        }
    }

    private fun verifyPlayer(): Boolean {
        val numberResult = playerUiState.value.number.isEmpty()
        val firstNameResult = playerUiState.value.firstName.isEmpty()
        val lastNameResult = playerUiState.value.lastName.isEmpty()

        playerUiState.value = playerUiState.value.copy(
            numberError = numberResult,
            firstNameError = firstNameResult,
            lastNameError = lastNameResult
        )

        return (!(numberResult || firstNameResult || lastNameResult))
    }

    fun getAllPlayers() {
        requestState.value = RequestState.Loading

        viewModelScope.launch {
            try {
                val response = withContext(Dispatchers.IO) {
                    playerRepository.getAllPlayers()
                }

                if(response.isNotEmpty()) {
                    requestState.value = RequestState.Success
                    players.value = response
                    players.value = players.value.sortedBy { it.number }
                } else {
                    requestState.value = RequestState.Idle
                }

                Log.d("PlayerViewModel", response.toString())
            }
            catch (e: Exception) {
                requestState.value = RequestState.Error
                Log.d("PlayerViewModel", e.toString())
            }
        }
    }

    fun getPlayerById(playerId: String) {
        requestState.value = RequestState.Loading

        viewModelScope.launch {
            try {
                val response = withContext(Dispatchers.IO) {
                    playerRepository.getPlayerById(id = playerId)
                }

                if (response != null) {
                    requestState.value = RequestState.Success
                    convertResponseToPlayer(player = response)
                    getPenaltyReceivedByPlayerId()
                } else {
                    playerUiState.value = PlayerUiState()
                }

                Log.d("PlayerViewModel", response.toString())
            }
            catch (e: Exception) {
                requestState.value = RequestState.Error
                Log.d("PlayerViewModel", e.toString())
            }
        }
    }

    private fun getPlayersBySearch() {
        requestState.value = RequestState.Loading

        viewModelScope.launch {
            try {
                val response = withContext(Dispatchers.IO) {
                    playerRepository.getPlayersBySearch(name = searchUiState.value.searchText)
                }

                if(response != null) {
                    requestState.value = RequestState.Success
                    players.value = response
                }
                Log.d("PlayerViewModel", response.toString())
            }
            catch (e: Exception) {
                requestState.value = RequestState.Error
                Log.d("PlayerViewModel", e.toString())
            }
        }
    }

    fun postPlayer() {
        requestState.value = RequestState.Loading

        viewModelScope.launch {
            try {
                if (verifyPlayer()) {
                    val response = withContext(Dispatchers.IO) {
                        playerRepository.postPlayer(
                            player = createPlayer()
                        )
                    }

                    if (response != null) {
                        requestState.value = RequestState.Success
                        postPlayer.value = true
                    }
                    Log.d("PlayerViewModel", response.toString())
                }
            } catch (e: Exception) {
                requestState.value = RequestState.Error
                Log.d("PlayerViewModel", e.toString())
            }
        }
    }

    fun updatePlayer() {
        requestState.value = RequestState.Loading

        viewModelScope.launch {
            try {
                if (verifyPlayer()) {
                    val response = withContext(Dispatchers.IO) {
                        playerRepository.updatePlayer(
                            id = playerUiState.value.id,
                            player = createPlayer()
                        )
                    }

                    if (response) {
                        requestState.value = RequestState.Success
                        updatePlayer.value = true
                    }
                    Log.d("PlayerViewModel", "${playerUiState.value.id} ${playerUiState.value.firstName} ${playerUiState.value.lastName} successfully updated")
                }
            } catch (e: Exception) {
                requestState.value = RequestState.Error
                Log.d("PlayerViewModel", e.toString())
            }
        }
    }

    fun deletePlayer() {
        requestState.value = RequestState.Loading

        viewModelScope.launch {
            try {
                val response = withContext(Dispatchers.IO) {
                    playerRepository.deletePlayer(
                        id = playerUiState.value.id
                    )
                }

                if (response) {
                    requestState.value = RequestState.Success
                }
                Log.d("PlayerViewModel", "${playerUiState.value.id} ${playerUiState.value.firstName} ${playerUiState.value.lastName} successfully deleted")

            } catch (e: Exception) {
                requestState.value = RequestState.Error
                Log.d("PlayerViewModel", e.toString())
            }
        }
    }

    fun resetPlayerUiState() {
        playerUiState.value = PlayerUiState()
    }

    fun onPlayerUiEvent(event: PlayerUiEvent) {
        when(event) {
            is PlayerUiEvent.NumberChanged -> {
                playerUiState.value = playerUiState.value.copy(
                    number = event.number
                )
            }

            is PlayerUiEvent.FirstNameChanged -> {
                playerUiState.value = playerUiState.value.copy(
                    firstName = event.firstName
                )
            }

            is PlayerUiEvent.LastNameChanged -> {
                playerUiState.value = playerUiState.value.copy(
                    lastName = event.lastName
                )
            }

            is PlayerUiEvent.BirthdayChanged -> {
                playerUiState.value = playerUiState.value.copy(
                    birthday = event.birthday
                )
            }

            is PlayerUiEvent.StreetChanged -> {
                playerUiState.value = playerUiState.value.copy(
                    street = event.street
                )
            }

            is PlayerUiEvent.ZipcodeChanged -> {
                playerUiState.value = playerUiState.value.copy(
                    zipcode = event.zipcode
                )
            }

            is PlayerUiEvent.CityChanged -> {
                playerUiState.value = playerUiState.value.copy(
                    city = event.city
                )
            }

            is PlayerUiEvent.PlayedGamesChanged -> {
                playerUiState.value = playerUiState.value.copy(
                    playedGames = event.playedGames
                )
            }

            is PlayerUiEvent.GoalsChanged -> {
                playerUiState.value = playerUiState.value.copy(
                    goals = event.goals
                )
            }

            is PlayerUiEvent.YellowCardsChanged -> {
                playerUiState.value = playerUiState.value.copy(
                    yellowCards = event.yellowCards
                )
            }

            is PlayerUiEvent.TwoMinutesChanged -> {
                playerUiState.value = playerUiState.value.copy(
                    twoMinutes = event.twoMinutes
                )
            }

            is PlayerUiEvent.RedCardsChanged -> {
                playerUiState.value = playerUiState.value.copy(
                    redCards = event.redCards
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
                getPlayersBySearch()
            }
        }
    }
}
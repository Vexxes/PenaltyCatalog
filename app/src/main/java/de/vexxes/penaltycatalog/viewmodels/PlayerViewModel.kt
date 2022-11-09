package de.vexxes.penaltycatalog.viewmodels

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import de.vexxes.penaltycatalog.domain.model.Player
import de.vexxes.penaltycatalog.domain.repository.PlayerRepository
import de.vexxes.penaltycatalog.util.RequestState
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class PlayerViewModel @Inject constructor(
    private val PlayerRepository: PlayerRepository
): ViewModel() {

    private val _players: MutableState<RequestState<List<Player>>> = mutableStateOf(RequestState.Idle)
    val players: State<RequestState<List<Player>>> = _players

    init {
        getAllPlayers()
    }

    fun getAllPlayers() {
        _players.value = RequestState.Loading

        try {
            viewModelScope.launch {
//                val response = PlayerRepository.getAllPlayers()
//               TODO Create the correct response for get all players
            //                _players.value = RequestState.Success(response)
            }
        }
        catch (e: Exception) {
            _players.value = RequestState.Error(e)
        }
    }
}
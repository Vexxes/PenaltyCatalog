package de.vexxes.penaltycatalog.presentation.screen.player

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import de.vexxes.penaltycatalog.R
import de.vexxes.penaltycatalog.component.BackDeleteEditTopBar
import de.vexxes.penaltycatalog.component.DeleteAlertDialog
import de.vexxes.penaltycatalog.domain.model.ApiResponse
import de.vexxes.penaltycatalog.domain.model.Player
import de.vexxes.penaltycatalog.domain.uistate.PlayerUiState
import de.vexxes.penaltycatalog.viewmodels.PlayerViewModel
import kotlinx.coroutines.delay

@Composable
fun PlayerDetailScreen(
    playerViewModel: PlayerViewModel,
    onBackClicked: () -> Unit,
    onDeleteClicked: (String) -> Unit,
    onEditClicked: (String) -> Unit,
) {
    val playerUiState by playerViewModel.playerUiState
    val apiResponse by playerViewModel.lastResponse
    var showAlertDialog by remember { mutableStateOf(false) }

    BackHandler {
        onBackClicked()
    }

    if(showAlertDialog) {
        DeleteAlertDialog(
            title = "Permanently delete?",
            text = "Player will be deleted permanently and can't be restored",
            onDismissRequest = {
                showAlertDialog = false
            },
            onConfirmClicked = {
                showAlertDialog = false
                onDeleteClicked(playerUiState.id)
            },
            onDismissButton = {
                showAlertDialog = false
            }
        )
    }

    PlayerDetailScreen(
        playerUiState = playerUiState,
        onBackClicked = onBackClicked,
        onDeleteClicked = { showAlertDialog = true },
        onEditClicked = { onEditClicked(playerUiState.id) },
        apiResponse = apiResponse,
        resetApiResponse = { playerViewModel.resetLastResponse() },
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun PlayerDetailScreen(
    playerUiState: PlayerUiState,
    onBackClicked: () -> Unit,
    onDeleteClicked: () -> Unit,
    onEditClicked: () -> Unit,
    apiResponse: ApiResponse,
    resetApiResponse: () -> Unit,
) {
    Scaffold(
        topBar = {
            BackDeleteEditTopBar(
                onBackClicked = onBackClicked,
                onDeleteClicked = onDeleteClicked,
                onEditClicked = onEditClicked
            )
        },

        content = {
            Box(
                modifier = Modifier.padding(it)
            ) {
                PlayerDetailContent(
                    playerUiState = playerUiState
                )
            }
        },

        snackbarHost = {
            PlayerDetailSnackbar(
                apiResponse = apiResponse,
                resetApiResponse = resetApiResponse
            )
        }
    )
}

@Composable
fun PlayerDetailSnackbar(
    apiResponse: ApiResponse,
    resetApiResponse: () -> Unit
) {
    /*TODO Other approach possible?*/
    // Reset snackbar after 3 seconds
    LaunchedEffect(key1 = true) {
        delay(3000)
        resetApiResponse()
    }

    if(apiResponse.hashCode() != ApiResponse().hashCode()) {
        Snackbar(
            modifier = Modifier
                .padding(8.dp),
            action = {
                Text(
                    modifier = Modifier
                        .clickable { resetApiResponse() },
                    text = stringResource(id = R.string.Ok))
            }
        ) {
            Text(
                text = if(!apiResponse.message.isNullOrEmpty()) apiResponse.message else ""
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PlayerDetailScreenPreview() {
    val player = Player.generateFaker()
    val playerUiState = PlayerUiState(
        id = player._id,
        number = player.number.toString(),
        firstName = player.firstName,
        lastName = player.lastName,
        birthday = player.birthday,
        street = player.street,
        zipcode = player.zipcode.toString(),
        city = player.city,
        playedGames = player.playedGames.toString(),
        goals = player.goals.toString(),
        yellowCards = player.yellowCards.toString(),
        twoMinutes = player.twoMinutes.toString(),
        redCards = player.redCards.toString(),
    )

    PlayerDetailScreen(
        playerUiState = playerUiState,
        onBackClicked = { },
        onDeleteClicked = { },
        onEditClicked = { },
        apiResponse = ApiResponse(),
        resetApiResponse = { }
    )
}
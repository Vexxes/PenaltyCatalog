package de.vexxes.penaltycatalog.presentation.screen.player

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import de.vexxes.penaltycatalog.component.BackDeleteEditTopBar
import de.vexxes.penaltycatalog.domain.model.Player
import de.vexxes.penaltycatalog.viewmodels.PlayerViewModel

@Composable
fun PlayerDetailScreen(
    playerViewModel: PlayerViewModel,
    onBackClicked: () -> Unit,
    onDeleteClicked: (String) -> Unit,
    onEditClicked: (String) -> Unit,
) {
    val player by playerViewModel.player

    BackHandler {
        onBackClicked()
    }

    PlayerDetailScreen(
        player = player,
        onBackClicked = onBackClicked,
        onDeleteClicked = { onDeleteClicked(player._id) },
        onEditClicked = { onEditClicked(player._id) }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun PlayerDetailScreen(
    player: Player,
    onBackClicked: () -> Unit,
    onDeleteClicked: () -> Unit,
    onEditClicked: () -> Unit
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
                PlayerDetailContent(player = player)
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
private fun PlayerDetailScreenPreview() {
    val player = Player(
        _id = "63717e8314ab74703f0ab5cb",
        number = 5,
        firstName = "Thomas",
        lastName = "Schneider",
        street = "Bussardweg 3C",
        birthday = "21.06.1997",
        zipcode = 49424,
        city = "Goldenstedt",
        playedGames = 4,
        goals = 16,
        yellowCards = 1,
        twoMinutes = 1,
        redCards = 0
    )
    PlayerDetailScreen(
        player = player,
        onBackClicked = { },
        onDeleteClicked = { },
        onEditClicked = { }
    )
}
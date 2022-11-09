package de.vexxes.penaltycatalog.presentation.screen.player

import android.annotation.SuppressLint
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import de.vexxes.penaltycatalog.viewmodels.PlayerViewModel

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun PlayerListScreen(
    navigateToPlayerDetailScreen: (playerId: Int) -> Unit,
    playerViewModel: PlayerViewModel
) {

    val players by playerViewModel.players

    Scaffold(

        // TODO Create topBar
        topBar = {

        },

        content = {
            PlayerListContent(
                players = players,
                navigateToPlayerDetailScreen = navigateToPlayerDetailScreen
            )
        },

        // TODO Create fab
        floatingActionButton = {

        }
    )

}








// TODO: Create fab
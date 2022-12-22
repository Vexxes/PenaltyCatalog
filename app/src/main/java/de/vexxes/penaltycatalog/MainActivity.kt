package de.vexxes.penaltycatalog

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import de.vexxes.penaltycatalog.navigation.MainScreen
import de.vexxes.penaltycatalog.ui.theme.PenaltyCatalogTheme
import de.vexxes.penaltycatalog.viewmodels.PenaltyHistoryViewModel
import de.vexxes.penaltycatalog.viewmodels.PenaltyViewModel
import de.vexxes.penaltycatalog.viewmodels.PlayerViewModel

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val playerViewModel: PlayerViewModel by viewModels()
    private val penaltyViewModel: PenaltyViewModel by viewModels()
    private val penaltyHistoryViewModel: PenaltyHistoryViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PenaltyCatalogTheme {
                val navController = rememberNavController()
                MainScreen(
                    navController = navController,
                    playerViewModel = playerViewModel,
                    penaltyViewModel = penaltyViewModel,
                    penaltyHistoryViewModel = penaltyHistoryViewModel
                )
            }
        }
    }
}
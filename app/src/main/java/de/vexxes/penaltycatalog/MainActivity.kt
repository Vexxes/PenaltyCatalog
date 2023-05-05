package de.vexxes.penaltycatalog

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import de.vexxes.penaltycatalog.navigation.SetupNavGraph
import de.vexxes.penaltycatalog.ui.theme.PenaltyCatalogTheme
import de.vexxes.penaltycatalog.viewmodels.CancellationViewModel
import de.vexxes.penaltycatalog.viewmodels.EventViewModel
import de.vexxes.penaltycatalog.viewmodels.PenaltyReceivedViewModel
import de.vexxes.penaltycatalog.viewmodels.PenaltyTypeViewModel
import de.vexxes.penaltycatalog.viewmodels.PlayerViewModel

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val playerViewModel: PlayerViewModel by viewModels()
    private val penaltyTypeViewModel: PenaltyTypeViewModel by viewModels()
    private val penaltyReceivedViewModel: PenaltyReceivedViewModel by viewModels()
    private val eventViewModel: EventViewModel by viewModels()
    private val cancellationViewModel: CancellationViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PenaltyCatalogTheme {
                val navController = rememberNavController()
                SetupNavGraph(
                    navController = navController,
                    playerViewModel = playerViewModel,
                    penaltyTypeViewModel = penaltyTypeViewModel,
                    penaltyReceivedViewModel = penaltyReceivedViewModel,
                    eventViewModel = eventViewModel,
                    cancellationViewModel = cancellationViewModel
                )
            }
        }
    }
}
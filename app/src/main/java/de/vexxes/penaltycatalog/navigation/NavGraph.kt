package de.vexxes.penaltycatalog.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import de.vexxes.penaltycatalog.navigation.navGraph.cancellationScreensGraph
import de.vexxes.penaltycatalog.navigation.navGraph.eventScreensGraph
import de.vexxes.penaltycatalog.navigation.navGraph.penaltyReceivedScreensGraph
import de.vexxes.penaltycatalog.navigation.navGraph.penaltyScreensGraph
import de.vexxes.penaltycatalog.navigation.navGraph.playerScreensGraph
import de.vexxes.penaltycatalog.viewmodels.CancellationViewModel
import de.vexxes.penaltycatalog.viewmodels.EventViewModel
import de.vexxes.penaltycatalog.viewmodels.PenaltyReceivedViewModel
import de.vexxes.penaltycatalog.viewmodels.PenaltyTypeViewModel
import de.vexxes.penaltycatalog.viewmodels.PlayerViewModel

object Graph {
    const val ROOT = "root_graph"
    const val PENALTY = "penalty_graph"
    const val PLAYER = "player_graph"
    const val PENALTY_RECEIVED = "penalty_received_graph"
    const val CANCELLATIONS = "cancellation_graph"
    const val EVENTS = "event_graph"
}

@Composable
fun SetupNavGraph(
    paddingValues: PaddingValues,
    navController: NavHostController,
    playerViewModel: PlayerViewModel,
    penaltyTypeViewModel: PenaltyTypeViewModel,
    penaltyReceivedViewModel: PenaltyReceivedViewModel,
    eventViewModel: EventViewModel,
    cancellationViewModel: CancellationViewModel
) {
    NavHost(
        modifier = Modifier.padding(paddingValues),
        navController = navController,
        startDestination = Graph.PENALTY,
        route = Graph.ROOT,
    ) {
        penaltyScreensGraph(
            navController = navController,
            penaltyTypeViewModel = penaltyTypeViewModel
        )

        playerScreensGraph(
            navController = navController,
            playerViewModel = playerViewModel
        )

        penaltyReceivedScreensGraph(
            navController = navController,
            penaltyReceivedViewModel = penaltyReceivedViewModel
        )

        cancellationScreensGraph(
            navController = navController,
            cancellationViewModel = cancellationViewModel
        )

        eventScreensGraph(
            navController = navController,
            eventViewModel = eventViewModel
        )

    }
}

@Composable
fun MainScreen(
    navController: NavHostController,
    playerViewModel: PlayerViewModel,
    penaltyTypeViewModel: PenaltyTypeViewModel,
    penaltyReceivedViewModel: PenaltyReceivedViewModel,
    eventViewModel: EventViewModel,
    cancellationViewModel: CancellationViewModel
) {
    Scaffold(
        bottomBar = { BottomBar(navController = navController) }
    ) { paddingValues ->
        SetupNavGraph(
            paddingValues = paddingValues,
            navController = navController,
            playerViewModel = playerViewModel,
            penaltyTypeViewModel = penaltyTypeViewModel,
            penaltyReceivedViewModel = penaltyReceivedViewModel,
            eventViewModel = eventViewModel,
            cancellationViewModel = cancellationViewModel
        )
    }
}
package de.vexxes.penaltycatalog.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import de.vexxes.penaltycatalog.navigation.navGraph.eventScreensGraph
import de.vexxes.penaltycatalog.navigation.navGraph.penaltyReceivedScreensGraph
import de.vexxes.penaltycatalog.navigation.navGraph.penaltyScreensGraph
import de.vexxes.penaltycatalog.navigation.navGraph.playerScreensGraph
import de.vexxes.penaltycatalog.ui.theme.Typography
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
    eventViewModel: EventViewModel
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

        composable(route = ScreenNavigation.Cancellations.route) {
            // TODO Call cancellations screen
            ScreenText(text = ScreenNavigation.Cancellations.name)
        }

        eventScreensGraph(
            navController = navController,
            eventViewModel = eventViewModel
        )

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    navController: NavHostController,
    playerViewModel: PlayerViewModel,
    penaltyTypeViewModel: PenaltyTypeViewModel,
    penaltyReceivedViewModel: PenaltyReceivedViewModel,
    eventViewModel: EventViewModel
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
            eventViewModel = eventViewModel
        )
    }
}

// TODO: Remove later
@Composable
fun ScreenText(text: String) {
    Text(
        modifier = Modifier
            .fillMaxSize()
            .wrapContentHeight(Alignment.CenterVertically),
        text = text,
        textAlign = TextAlign.Center,
        style = Typography.headlineLarge
    )
}
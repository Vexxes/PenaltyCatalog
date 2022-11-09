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
import de.vexxes.penaltycatalog.navigation.navGraph.mainScreensGraph
import de.vexxes.penaltycatalog.ui.theme.Typography
import de.vexxes.penaltycatalog.viewmodels.PlayerViewModel

@Composable
fun SetupNavGraph(
    paddingValues: PaddingValues,
    navController: NavHostController,
    playerViewModel: PlayerViewModel
) {
    NavHost(
        modifier = Modifier.padding(paddingValues),
        navController = navController,
        route = Graph.ROOT,
        startDestination = Graph.MAIN
    ) {
        mainScreensGraph(
            navController = navController,
            playerViewModel = playerViewModel
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    navController: NavHostController,
    playerViewModel: PlayerViewModel
) {
    Scaffold(
        bottomBar = { BottomBar(navController = navController) }
    ) { paddingValues ->
        SetupNavGraph(
            paddingValues = paddingValues,
            navController = navController,
            playerViewModel = playerViewModel
        )
    }
}

object Graph {
    const val ROOT = "root_graph"
    const val MAIN = "main_graph"
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
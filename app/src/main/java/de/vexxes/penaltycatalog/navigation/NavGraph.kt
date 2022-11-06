package de.vexxes.penaltycatalog.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import de.vexxes.penaltycatalog.ui.theme.Typography

@Composable
fun SetupNavGraph(paddingValues: PaddingValues, navController: NavHostController) {
    NavHost(
        modifier = Modifier.padding(paddingValues),
        navController = navController,
        startDestination = Screen.Penalties.route
    ) {
        composable(route = Screen.Penalties.route) {
            // TODO: Call penalties screen
            ScreenText(text = Screen.Penalties.name)
        }

        composable(route = Screen.Players.route) {
            // TODO Call player screen
            ScreenText(text = Screen.Players.name)
        }

        composable(route = Screen.PenaltyHistory.route) {
            // TODO Call penalty history screen
            ScreenText(text = Screen.PenaltyHistory.name)
        }

        composable(route = Screen.Cancellations.route) {
            // TODO Call cancellations screen
            ScreenText(text = Screen.Cancellations.name)
        }

        composable(route = Screen.Events.route) {
            // TODO Call events screen
            ScreenText(text = Screen.Events.name)
        }
    }
}

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
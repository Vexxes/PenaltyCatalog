package de.vexxes.penaltycatalog.navigation

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState
import de.vexxes.penaltycatalog.R

@Composable
fun BottomBar(navController: NavController) {

    val penalty = Screen.Penalties
    penalty.name = stringResource(id = R.string.Penalty)

    val players = Screen.Players
    players.name = stringResource(id = R.string.Players)

    val penaltyHistory = Screen.PenaltyHistory
    penaltyHistory.name = stringResource(id = R.string.PenaltyHistory)

    val cancellations = Screen.Cancellations
    cancellations.name = stringResource(id = R.string.Cancellations)

    val events = Screen.Events
    events.name = stringResource(id = R.string.Events)

    val screens = listOf(
        penalty,
        players,
        penaltyHistory,
        cancellations,
        events
    )

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    NavigationBar {
        screens.forEach { screen ->
            AddItem(
                screen = screen,
                currentDestination = currentDestination,
                navController = navController)
        }
    }
}

@Composable
fun RowScope.AddItem(
    screen: Screen,
    currentDestination: NavDestination?,
    navController: NavController
) {
    val selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true

    NavigationBarItem(
        label = {
            Text(
                text = screen.name,
                textAlign = TextAlign.Center
            )
        },
        icon = {
            Icon(
                imageVector = if (selected) screen.iconSelected else screen.iconUnselected,
                contentDescription = screen.name
            )
        },
        selected = selected,
        onClick = {
            navController.navigate(screen.route)
        }
    )
}
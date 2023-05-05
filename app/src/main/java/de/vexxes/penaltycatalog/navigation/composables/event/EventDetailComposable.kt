package de.vexxes.penaltycatalog.navigation.composables.event

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import de.vexxes.penaltycatalog.navigation.Screen
import de.vexxes.penaltycatalog.presentation.screen.events.EventDetailScreen
import de.vexxes.penaltycatalog.util.EVENT_ID
import de.vexxes.penaltycatalog.viewmodels.EventViewModel

fun NavGraphBuilder.eventDetailComposable(
    eventViewModel: EventViewModel,
    navigateToEventList: () -> Unit,
    onEditClicked: (String) -> Unit
) {
    composable(
        route = Screen.EventDetail.route,
        arguments = listOf(navArgument(EVENT_ID) {
            type = NavType.StringType
        })
    ) { navBackStackEntry ->

        // Get eventId from argument
        val eventId = navBackStackEntry.arguments?.getString(EVENT_ID)
        eventViewModel.getEventById(eventId = eventId!!)

        var visible by remember {
            mutableStateOf(false)
        }
        LaunchedEffect(key1 = true) { visible = true }

        AnimatedVisibility(
            visible = visible,
            enter = fadeIn(animationSpec = tween(durationMillis = 500)),
            exit = fadeOut(animationSpec = tween(durationMillis = 500))
        ) {
            EventDetailScreen(
                eventViewModel = eventViewModel,
                onBackClicked = { navigateToEventList() },
                onDeleteClicked = {
                    eventViewModel.deleteEvent()
                    navigateToEventList()
                },
                onEditClicked = { onEditClicked(it) }
            )
        }
    }
}
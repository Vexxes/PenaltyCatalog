package de.vexxes.penaltycatalog.navigation.composables.event

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
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
import de.vexxes.penaltycatalog.presentation.screen.events.EventEditScreen
import de.vexxes.penaltycatalog.util.EVENT_ID
import de.vexxes.penaltycatalog.viewmodels.EventViewModel

fun NavGraphBuilder.eventEditComposable(
    eventViewModel: EventViewModel,
    navigateBack: () -> Unit
) {
    composable(
        route = Screen.EventEdit.route,
        arguments = listOf(navArgument(EVENT_ID) {
            type = NavType.StringType
            nullable = true
            defaultValue = ""
        })
    ) { navBackStackEntry ->

        // Get eventId from argument
        val eventId = navBackStackEntry.arguments?.getString(EVENT_ID)
        LaunchedEffect(key1 = eventId) {
            if (eventId == "")
                eventViewModel.resetEventUiState()
        }

        var visible by remember { mutableStateOf(false) }
        LaunchedEffect(key1 = true) {
            visible = true
            eventViewModel.getAllEvents()
        }

        // Make invisible and navigate back, if postEvent or updateEvent was successful
        var postEvent by eventViewModel.postEvent
        var updateEvent by eventViewModel.updateEvent
        if (postEvent || updateEvent) {
            postEvent = false
            updateEvent = false
            visible = false
            navigateBack()
        }

        AnimatedVisibility(
            visible = visible,
            enter = slideInHorizontally(animationSpec = tween(durationMillis = 300)) { fullWidth -> fullWidth },
            exit = slideOutHorizontally(animationSpec = tween(durationMillis = 300)) { fullWidth -> fullWidth }
        ) {
            EventEditScreen(
                eventViewModel = eventViewModel,
                onBackClicked = {
                    visible = false
                    navigateBack()
                },
                onSaveClicked = {
                    if (eventViewModel.eventUiState.value.id == "") {
                        eventViewModel.postEvent()
                    } else {
                        eventViewModel.updateEvent()
                    }
                }
            )
        }
    }
}
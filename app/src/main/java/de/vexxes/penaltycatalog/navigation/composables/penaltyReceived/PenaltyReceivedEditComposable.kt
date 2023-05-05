package de.vexxes.penaltycatalog.navigation.composables.penaltyReceived

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
import de.vexxes.penaltycatalog.presentation.screen.penaltyReceived.PenaltyReceivedEditScreen
import de.vexxes.penaltycatalog.util.PENALTY_RECEIVED_ID
import de.vexxes.penaltycatalog.viewmodels.PenaltyReceivedViewModel

fun NavGraphBuilder.penaltyReceivedEditComposable(
    penaltyReceivedViewModel: PenaltyReceivedViewModel,
    navigateBack: () -> Unit
) {
    composable(
        route = Screen.PenaltyReceivedEdit.route,
        arguments = listOf(navArgument(PENALTY_RECEIVED_ID) {
            type = NavType.StringType
            nullable = true
            defaultValue = ""
        })
    ) { navBackStackEntry ->

        // Get penaltyHistoryId from argument
        val penaltyReceivedId = navBackStackEntry.arguments?.getString(PENALTY_RECEIVED_ID)
        LaunchedEffect(key1 = penaltyReceivedId) {
            if (penaltyReceivedId == "")
                penaltyReceivedViewModel.resetPenaltyReceivedUiState()
        }

        var visible by remember { mutableStateOf(false) }
        LaunchedEffect(key1 = true) {
            visible = true
            penaltyReceivedViewModel.updateLists()
        }

        // Make invisible and navigate back, if postPenaltyReceived or updatePenaltyReceived was successful
        var postPenaltyReceived by penaltyReceivedViewModel.postPenaltyReceived
        var updatePenaltyReceived by penaltyReceivedViewModel.updatePenaltyReceived
        if (postPenaltyReceived || updatePenaltyReceived) {
            postPenaltyReceived = false
            updatePenaltyReceived = false
            visible = false
            navigateBack()
        }

        AnimatedVisibility(
            visible = visible,
            enter = slideInHorizontally(animationSpec = tween(durationMillis = 300)) { fullWidth -> fullWidth },
            exit = slideOutHorizontally(animationSpec = tween(durationMillis = 300)) { fullWidth -> fullWidth }
        ) {
            PenaltyReceivedEditScreen(
                penaltyReceivedViewModel = penaltyReceivedViewModel,
                onBackClicked = {
                    visible = false
                    navigateBack()
                },
                onSaveClicked = {
                    if (penaltyReceivedViewModel.penaltyReceivedUiState.value.id == "") {
                        penaltyReceivedViewModel.postPenaltyReceived()
                    } else {
                        penaltyReceivedViewModel.updatePenaltyReceived()
                    }
                }
            )
        }
    }
}
package de.vexxes.penaltycatalog.navigation.composables.penaltyHistory

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
import de.vexxes.penaltycatalog.domain.model.ApiResponse
import de.vexxes.penaltycatalog.navigation.Screen
import de.vexxes.penaltycatalog.presentation.screen.history.PenaltyHistoryEditScreen
import de.vexxes.penaltycatalog.viewmodels.PenaltyHistoryViewModel

fun NavGraphBuilder.penaltyHistoryEditComposable(
    penaltyHistoryViewModel: PenaltyHistoryViewModel,
    navigateBack: () -> Unit
) {
    composable(
        route = Screen.PenaltyHistoryEdit.route + Screen.PenaltyHistoryEdit.argument,
        arguments = listOf(navArgument("penaltyHistoryId") {
            type = NavType.StringType
        })
    ) { navBackStackEntry ->

        // Get penaltyHistoryId from argument
        val penaltyHistoryId = navBackStackEntry.arguments?.getString("penaltyHistoryId")
        LaunchedEffect(key1 = penaltyHistoryId) {
            if (penaltyHistoryId == "-1")
                penaltyHistoryViewModel.resetPenaltyHistory()
        }

        var visible by remember { mutableStateOf(false) }
        LaunchedEffect(key1 = true) {
            visible = true
            penaltyHistoryViewModel.lastResponse.value = ApiResponse()
            penaltyHistoryViewModel.getAllPenalties()
            penaltyHistoryViewModel.getAllPlayers()
        }

        AnimatedVisibility(
            visible = visible,
            enter = slideInHorizontally(animationSpec = tween(durationMillis = 300)) { fullWidth -> fullWidth },
            exit = slideOutHorizontally(animationSpec = tween(durationMillis = 300)) { fullWidth -> fullWidth }
        ) {
            PenaltyHistoryEditScreen(
                penaltyHistoryViewModel = penaltyHistoryViewModel,
                onBackClicked = {
                    visible = false
                    navigateBack()
                },
                onSaveClicked = {
                    if (penaltyHistoryViewModel.updatePenaltyHistory()) {
                        visible = false
                        navigateBack()
                    }
                }
            )
        }
    }
}
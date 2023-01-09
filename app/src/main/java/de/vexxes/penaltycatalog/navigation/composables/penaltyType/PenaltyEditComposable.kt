package de.vexxes.penaltycatalog.navigation.composables.penaltyType

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
import de.vexxes.penaltycatalog.presentation.screen.penaltyType.PenaltyEditScreen
import de.vexxes.penaltycatalog.viewmodels.PenaltyTypeViewModel

fun NavGraphBuilder.penaltyEditComposable(
    penaltyTypeViewModel: PenaltyTypeViewModel,
    navigateBack: () -> Unit
) {
    composable(
        route = Screen.PenaltyEdit.route + Screen.PenaltyEdit.argument,
        arguments = listOf(navArgument("penaltyId") {
            type = NavType.StringType
        })
    ) { navBackStackEntry ->

        // Get penaltyId from argument
        val penaltyId = navBackStackEntry.arguments?.getString("penaltyId")
        LaunchedEffect(key1 = penaltyId) {
            if (penaltyId == "-1")
                penaltyTypeViewModel.resetPenalty()
        }

        var visible by remember {
            mutableStateOf(false)
        }
        LaunchedEffect(key1 = true) {
            visible = true
            penaltyTypeViewModel.lastResponse.value = ApiResponse()
        }

        AnimatedVisibility(
            visible = visible,
            enter = slideInHorizontally(animationSpec = tween(durationMillis = 300)) { fullWidth -> fullWidth },
            exit = slideOutHorizontally(animationSpec = tween(durationMillis = 300)) { fullWidth -> fullWidth }
        ) {
            PenaltyEditScreen(
                penaltyTypeViewModel = penaltyTypeViewModel,
                onBackClicked = {
                    visible = false
                    navigateBack()
                },
                onSaveClicked = {
                    if (penaltyTypeViewModel.updatePenalty()) {
                        visible = false
                        navigateBack()
                    }
                }
            )
        }
    }
}
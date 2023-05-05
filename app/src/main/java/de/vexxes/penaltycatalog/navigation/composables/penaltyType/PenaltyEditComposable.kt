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
import de.vexxes.penaltycatalog.navigation.Screen
import de.vexxes.penaltycatalog.presentation.screen.penaltyType.PenaltyTypeEditScreen
import de.vexxes.penaltycatalog.util.PENALTY_TYPE_ID
import de.vexxes.penaltycatalog.viewmodels.PenaltyTypeViewModel

fun NavGraphBuilder.penaltyEditComposable(
    penaltyTypeViewModel: PenaltyTypeViewModel,
    navigateBack: () -> Unit
) {
    composable(
        route = Screen.PenaltyEdit.route,
        arguments = listOf(navArgument(PENALTY_TYPE_ID) {
            type = NavType.StringType
            nullable = true
            defaultValue = ""
        })
    ) { navBackStackEntry ->

        // Get penaltyId from argument
        val penaltyTypeId = navBackStackEntry.arguments?.getString(PENALTY_TYPE_ID)
        println(penaltyTypeId)
        LaunchedEffect(key1 = penaltyTypeId) {
            if (penaltyTypeId == "")
                penaltyTypeViewModel.resetPenaltyTypeUiState()
        }

        var visible by remember { mutableStateOf(false) }
        LaunchedEffect(key1 = true) {
            visible = true
        }

        // Make invisible and navigate back, if postPenaltyType or updatePenaltyType was successful
        var postPenaltyType by penaltyTypeViewModel.postPenaltyType
        var updatePenaltyType by penaltyTypeViewModel.updatePenaltyType
        if (postPenaltyType || updatePenaltyType) {
            postPenaltyType = false
            updatePenaltyType = false
            visible = false
            navigateBack()
        }

        AnimatedVisibility(
            visible = visible,
            enter = slideInHorizontally(animationSpec = tween(durationMillis = 300)) { fullWidth -> fullWidth },
            exit = slideOutHorizontally(animationSpec = tween(durationMillis = 300)) { fullWidth -> fullWidth }
        ) {
            PenaltyTypeEditScreen(
                penaltyTypeViewModel = penaltyTypeViewModel,
                onBackClicked = {
                    visible = false
                    navigateBack()
                },
                onSaveClicked = {
                    if (penaltyTypeViewModel.penaltyTypeUiState.value.id == "") {
                        penaltyTypeViewModel.postPenaltyType()
                    } else {
                        penaltyTypeViewModel.updatePenalty()
                    }
                }
            )
        }
    }
}
package de.vexxes.penaltycatalog.navigation.composables.cancellation

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
import de.vexxes.penaltycatalog.presentation.screen.cancel.CancellationEditScreen
import de.vexxes.penaltycatalog.util.CANCELLATION_ID
import de.vexxes.penaltycatalog.viewmodels.CancellationViewModel

fun NavGraphBuilder.cancellationEditComposable(
    cancellationViewModel: CancellationViewModel,
    navigateBack: () -> Unit
) {
    composable(
        route = Screen.CancellationEdit.route,
        arguments = listOf(navArgument(CANCELLATION_ID) {
            type = NavType.StringType
            nullable = true
            defaultValue = ""
        })
    ) { navBackStackEntry ->

        // Get cancellationId from argument
        val cancellationId = navBackStackEntry.arguments?.getString(CANCELLATION_ID)
        LaunchedEffect(key1 = cancellationId) {
            if (cancellationId == "")
                cancellationViewModel.resetCancellationUiState()
        }

        var visible by remember { mutableStateOf(false) }
        LaunchedEffect(key1 = true) {
            visible = true
            cancellationViewModel.getAllCancellations()
        }

        // Make invisible and navigate back, if postCancellation or updateCancellation was successful
        var postCancellation by cancellationViewModel.postCancellation
        var updateCancellation by cancellationViewModel.updateCancellation
        if (postCancellation || updateCancellation) {
            postCancellation = false
            updateCancellation = false
            visible = false
            navigateBack()
        }

        AnimatedVisibility(
            visible = visible,
            enter = slideInHorizontally(animationSpec = tween(durationMillis = 300)) { fullWidth -> fullWidth },
            exit = slideOutHorizontally(animationSpec = tween(durationMillis = 300)) { fullWidth -> fullWidth }
        ) {
            CancellationEditScreen(
                cancellationViewModel = cancellationViewModel,
                onBackClicked = {
                    visible = false
                    navigateBack()
                },
                onSaveClicked = {
                    if (cancellationViewModel.cancellationUiState.value.id == "") {
                        cancellationViewModel.postCancellation()
                    } else {
                        cancellationViewModel.updateCancellation()
                    }
                }
            )
        }
    }
}
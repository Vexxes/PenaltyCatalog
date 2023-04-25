package de.vexxes.penaltycatalog.navigation.composables.cancellation

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
import de.vexxes.penaltycatalog.presentation.screen.cancel.CancellationDetailScreen
import de.vexxes.penaltycatalog.viewmodels.CancellationViewModel

fun NavGraphBuilder.cancellationDetailComposable(
    cancellationViewModel: CancellationViewModel,
    navigateToCancellationList: () -> Unit,
    onEditClicked: (String) -> Unit
) {
    composable(
        route = Screen.CancellationDetail.route + Screen.CancellationDetail.argument,
        arguments = listOf(navArgument("cancellationId") {
            type = NavType.StringType
        })
    ) { navBackStackEntry ->

        // Get cancellationId from argument
        val cancellationId = navBackStackEntry.arguments?.getString("cancellationId")
        LaunchedEffect(key1 = cancellationId) {
            if (!cancellationId.isNullOrEmpty() && cancellationId != "-1") {
                cancellationViewModel.getCancellationById(cancellationId = cancellationId)
            }
        }

        var visible by remember {
            mutableStateOf(false)
        }
        LaunchedEffect(key1 = true) { visible = true }

        AnimatedVisibility(
            visible = visible,
            enter = fadeIn(animationSpec = tween(durationMillis = 500)),
            exit = fadeOut(animationSpec = tween(durationMillis = 500))
        ) {
            CancellationDetailScreen(
                cancellationViewModel = cancellationViewModel,
                onBackClicked = { navigateToCancellationList() },
                onDeleteClicked = {
                    cancellationViewModel.deleteCancellation()
                    navigateToCancellationList()
                },
                onEditClicked = { onEditClicked(it) }
            )
        }
    }
}
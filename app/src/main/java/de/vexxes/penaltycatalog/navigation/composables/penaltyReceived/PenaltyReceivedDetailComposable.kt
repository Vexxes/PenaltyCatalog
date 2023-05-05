package de.vexxes.penaltycatalog.navigation.composables.penaltyReceived

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
import de.vexxes.penaltycatalog.presentation.screen.penaltyReceived.PenaltyReceivedDetailScreen
import de.vexxes.penaltycatalog.util.PENALTY_RECEIVED_ID
import de.vexxes.penaltycatalog.viewmodels.PenaltyReceivedViewModel

fun NavGraphBuilder.penaltyReceivedDetailComposable(
    penaltyReceivedViewModel: PenaltyReceivedViewModel,
    navigateToPenaltyReceivedList: () -> Unit,
    onEditClicked: (String) -> Unit
) {
    composable(
        route = Screen.PenaltyReceivedDetail.route,
        arguments = listOf(navArgument(PENALTY_RECEIVED_ID) {
            type = NavType.StringType
        })
    ) { navBackStackEntry ->

        // Get penaltyReceivedId from argument
        val penaltyReceivedId = navBackStackEntry.arguments?.getString(PENALTY_RECEIVED_ID)
        penaltyReceivedViewModel.getPenaltyReceivedById(penaltyReceivedId = penaltyReceivedId!!)

        var visible by remember {
            mutableStateOf(false)
        }
        LaunchedEffect(key1 = true) { visible = true }

        AnimatedVisibility(
            visible = visible,
            enter = fadeIn(animationSpec = tween(durationMillis = 500)),
            exit = fadeOut(animationSpec = tween(durationMillis = 500))
        ) {
            PenaltyReceivedDetailScreen(
                penaltyReceivedViewModel = penaltyReceivedViewModel,
                onBackClicked = {
                    navigateToPenaltyReceivedList()
                },
                onDeleteClicked = {
                    penaltyReceivedViewModel.deletePenaltyReceived()
                    navigateToPenaltyReceivedList()
                },
                onEditClicked = { onEditClicked(it) }
            )
        }
    }
}
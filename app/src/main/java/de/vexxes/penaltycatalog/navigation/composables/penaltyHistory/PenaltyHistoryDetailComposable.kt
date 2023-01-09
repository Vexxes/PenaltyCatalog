package de.vexxes.penaltycatalog.navigation.composables.penaltyHistory

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
import de.vexxes.penaltycatalog.presentation.screen.penaltyHistory.PenaltyHistoryDetailScreen
import de.vexxes.penaltycatalog.viewmodels.PenaltyHistoryViewModel

fun NavGraphBuilder.penaltyHistoryDetailComposable(
    penaltyHistoryViewModel: PenaltyHistoryViewModel,
    navigateToPenaltyHistoryList: () -> Unit,
    onEditClicked: (String) -> Unit
) {
    composable(
        route = Screen.PenaltyHistoryDetail.route + Screen.PenaltyHistoryDetail.argument,
        arguments = listOf(navArgument("penaltyHistoryId") {
            type = NavType.StringType
        })
    ) { navBackStackEntry ->

        // Get penaltyHistoryId from argument
        val penaltyHistoryId = navBackStackEntry.arguments?.getString("penaltyHistoryId")
        LaunchedEffect(key1 = penaltyHistoryId) {
            if (!penaltyHistoryId.isNullOrEmpty() && penaltyHistoryId != "-1") {
                penaltyHistoryViewModel.getPenaltyHistoryById(penaltyHistoryId = penaltyHistoryId)
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
            PenaltyHistoryDetailScreen(
                penaltyHistoryViewModel = penaltyHistoryViewModel,
                onBackClicked = {
                    navigateToPenaltyHistoryList()
                },
                onDeleteClicked = {
                    penaltyHistoryViewModel.deletePenaltyHistory()
                    navigateToPenaltyHistoryList()
                },
                onEditClicked = { onEditClicked(it) }
            )
        }
    }
}
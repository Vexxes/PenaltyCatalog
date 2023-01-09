package de.vexxes.penaltycatalog.navigation.composables.penaltyType

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
import de.vexxes.penaltycatalog.presentation.screen.penaltyType.PenaltyDetailScreen
import de.vexxes.penaltycatalog.viewmodels.PenaltyTypeViewModel

fun NavGraphBuilder.penaltyDetailComposable(
    penaltyTypeViewModel: PenaltyTypeViewModel,
    navigateToPenaltyList: () -> Unit,
    onEditClicked: (String) -> Unit
) {
    composable(
        route = Screen.PenaltyDetail.route + Screen.PenaltyDetail.argument,
        arguments = listOf(navArgument("penaltyId") {
            type = NavType.StringType
        })
    ) { navBackStackEntry ->

        // Get penaltyId from argument
        val penaltyId = navBackStackEntry.arguments?.getString("penaltyId")
        LaunchedEffect(key1 = penaltyId) {
            if (!penaltyId.isNullOrEmpty() && penaltyId != "-1") {
                penaltyTypeViewModel.getPenaltyById(penaltyId = penaltyId)
            }
        }

        var visible by remember {
            mutableStateOf(false)
        }
        LaunchedEffect(key1 = true) { visible = true}

        AnimatedVisibility(
            visible = visible,
            enter = fadeIn(animationSpec = tween(durationMillis = 500)),
            exit = fadeOut(animationSpec = tween(durationMillis = 500))
        ) {
            PenaltyDetailScreen(
                penaltyTypeViewModel = penaltyTypeViewModel,
                onBackClicked = {
                    navigateToPenaltyList()
                },
                onDeleteClicked = {
                    penaltyTypeViewModel.deletePenalty()
                    navigateToPenaltyList()
                },
                onEditClicked = { onEditClicked(it) }
            )
        }
    }
}
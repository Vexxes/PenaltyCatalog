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
import de.vexxes.penaltycatalog.presentation.screen.penaltyType.PenaltyTypeDetailScreen
import de.vexxes.penaltycatalog.util.PENALTY_TYPE_ID
import de.vexxes.penaltycatalog.viewmodels.PenaltyTypeViewModel

fun NavGraphBuilder.penaltyDetailComposable(
    penaltyTypeViewModel: PenaltyTypeViewModel,
    navigateToPenaltyList: () -> Unit,
    onEditClicked: (String) -> Unit
) {
    composable(
        route = Screen.PenaltyDetail.route,
        arguments = listOf(navArgument(PENALTY_TYPE_ID) {
            type = NavType.StringType
        })
    ) { navBackStackEntry ->

        // Get penaltyId from argument
        val penaltyTypeId = navBackStackEntry.arguments?.getString(PENALTY_TYPE_ID)
        penaltyTypeViewModel.getPenaltyTypeById(penaltyTypeId = penaltyTypeId!!)

        var visible by remember {
            mutableStateOf(false)
        }
        LaunchedEffect(key1 = true) { visible = true}

        AnimatedVisibility(
            visible = visible,
            enter = fadeIn(animationSpec = tween(durationMillis = 500)),
            exit = fadeOut(animationSpec = tween(durationMillis = 500))
        ) {
            PenaltyTypeDetailScreen(
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
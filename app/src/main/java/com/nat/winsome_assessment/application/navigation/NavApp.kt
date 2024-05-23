package com.nat.winsome_assessment.application.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.nat.winsome_assessment.application.data.remote.network.NetworkErrorHandler.Companion.generalState
import com.nat.winsome_assessment.screens.mainScreen.presentation.MainScreen
import com.nat.winsome_assessment.screens.mainScreen.presentation.MainViewModel
import kotlinx.serialization.Serializable

@Serializable
object MainScreen

@Serializable
data class DetailsScreen(val image: String)


@Composable
fun NavApp(navController: NavHostController) {
    NavHost(navController = navController, startDestination = MainScreen) {
        composable<MainScreen> {
            val generalState = generalState.collectAsStateWithLifecycle()
            val viewModel: MainViewModel = hiltViewModel()
            val state = viewModel.state.collectAsStateWithLifecycle().value
            MainScreen(
                state = state,
                navigateToDetailsScreen = { navController.navigate(DetailsScreen) }
            )
        }
    }
}
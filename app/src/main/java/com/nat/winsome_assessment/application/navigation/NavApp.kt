package com.nat.winsome_assessment.application.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.nat.winsome_assessment.application.data.remote.NetworkErrorHandler.Companion.generalState
import com.nat.winsome_assessment.screens.detailsScreen.presentation.MovieDetailsScreen
import com.nat.winsome_assessment.screens.detailsScreen.presentation.MovieDetailsViewModel
import com.nat.winsome_assessment.screens.mainScreen.presentation.MainScreen
import com.nat.winsome_assessment.screens.mainScreen.presentation.MainScreenViewModel
import kotlinx.serialization.Serializable

@Serializable
object Main

@Serializable
data class Details(val movieID: Int)


@Composable
fun NavApp(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Main) {
        composable<Main> {
            val generalState = generalState.collectAsStateWithLifecycle().value
            val viewModel: MainScreenViewModel = hiltViewModel()
            val state = viewModel.state.collectAsStateWithLifecycle().value
            MainScreen(
                state = state,
                event = viewModel::onEvent,
                generalState = generalState,
                navigateToDetailsScreen = { id ->
                    navController.navigate(Details(movieID = id))
                }
            )
        }

        composable<Details> {
            val args = it.toRoute<Details>()
            val viewModel: MovieDetailsViewModel = hiltViewModel()
            val state = viewModel.state.collectAsStateWithLifecycle().value
            val generalState = generalState.collectAsStateWithLifecycle().value
            MovieDetailsScreen(
                state = state,
                generalState = generalState,
                event = viewModel::onEvent,
                movieID = args.movieID,
                navigateToFavorites = {},
                navigateBack = { navController.navigateUp() }
            )
        }
    }
}
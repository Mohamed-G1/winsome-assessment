package com.nat.winsome_assessment.screens.favoritesScreen.presentation

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.nat.winsome_assessment.R
import com.nat.winsome_assessment.application.data.local.db.entity.toUiModel
import com.nat.winsome_assessment.application.presentation.MovieImageLoader
import com.nat.winsome_assessment.application.presentation.general.StatusBarIconColoring
import com.nat.winsome_assessment.ui.theme.largeTitle
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

@Composable
fun FavoritesScreen(
    state: FavoritesState,
    event: ((FavoritesEvent) -> Unit)? = null,
    checkIsMovieSaved: ((Int) -> Flow<Boolean>)? = null,
    navigateBack: (() -> Unit)? = null
) {
    StatusBarIconColoring(showAsDarkIcons = true)
    ScreenContent(
        state = state,
        event = event,
        checkIsMovieSaved = checkIsMovieSaved,
        navigateBack = navigateBack
    )
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)
@Composable
private fun ScreenContent(
    state: FavoritesState,
    event: ((FavoritesEvent) -> Unit)? = null,
    checkIsMovieSaved: ((Int) -> Flow<Boolean>)? = null,
    navigateBack: (() -> Unit)? = null
) {

    // Check if the movie is exists in the DB
    val isSaved = checkIsMovieSaved?.invoke(state.storedMovies.firstOrNull()?.movieId ?: 0)
        ?.collectAsState(initial = false)?.value ?: false
    var currentQuery by rememberSaveable { mutableStateOf("") }
    var isSearchActive by remember { mutableStateOf(false) }
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current

    Scaffold(
        topBar = {
            TopAppBar(title = {
                // If the search is active show the search bar
                if (isSearchActive) {
                    TextField(
                        value = currentQuery,
                        onValueChange = { newQuery ->
                            currentQuery = newQuery
                            event?.invoke(FavoritesEvent.SearchOnMovie(query = newQuery))
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight()
                            .padding(horizontal = 16.dp)
                            .border(
                                0.3.dp,
                                color = Color.Black,
                                shape = RoundedCornerShape(8.dp)
                            )
                            .clip(RoundedCornerShape(8.dp)),
                        placeholder = { Text(stringResource(R.string.search)) },
                        maxLines = 1,
                        colors = TextFieldDefaults.colors(
                            unfocusedContainerColor = Color.White,
                            focusedContainerColor = Color.White,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            disabledIndicatorColor = Color.Transparent
                        ),
                        keyboardOptions = KeyboardOptions(
                            imeAction = ImeAction.Search, keyboardType = KeyboardType.Text
                        ),
                        keyboardActions = KeyboardActions(onSearch = {
                            event?.invoke(FavoritesEvent.SearchOnMovie(query = currentQuery))
                            focusManager.clearFocus()
                            keyboardController?.hide()
                        })
                    )
                    // Otherwise open show app name
                } else {
                    Text(
                        text = stringResource(R.string.favorites_list),
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth(),
                        style = largeTitle
                    )
                }
            },
                // Toggle between search and clear search queries actions
                actions = {
                    if (isSearchActive) {
                        // Close search
                        IconButton(onClick = {
                            currentQuery = ""
                            isSearchActive = false
                            // Return to the stored movie list
                            event?.invoke(FavoritesEvent.SearchClosed)
                        }) {
                            Icon(Icons.Default.Close, contentDescription = null)
                        }
                    } else {
                        // Open search field
                        IconButton(onClick = { isSearchActive = true }) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_search),
                                contentDescription = null
                            )
                        }
                    }
                },
                navigationIcon = {
                    IconButton(onClick = { navigateBack?.invoke() }) {
                        Icon(
                            painterResource(id = R.drawable.ic_back),
                            contentDescription = null,
                            tint = Color.Black
                        )
                    }
                })
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            val scope = rememberCoroutineScope()

            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.spacedBy(24.dp),
            ) {
                items(items = state.storedMovies, key = { entity -> entity.id }) { movie ->
                    var isVisible by remember { mutableStateOf(true) }
                    AnimatedVisibility(
                        visible = isVisible,
                        enter = fadeIn(animationSpec = tween(1000)),
                        exit = fadeOut(animationSpec = tween(1000))
                    ) {
                        AnimatedContent(
                            targetState = movie,
                            transitionSpec = {
                                fadeIn(animationSpec = tween(1000)) togetherWith fadeOut(animationSpec = tween(1000))
                            }, label = ""
                        ) { targetMovie ->
                            MovieImageLoader(
                                model = targetMovie.toUiModel(),
                                onSaveClick = {
                                    isVisible = false
                                    // Delay the deletion to allow the animation to play
                                    scope.launch {
                                        delay(1000)
                                        event?.invoke(FavoritesEvent.DeleteMovie(movie = targetMovie.toUiModel()))
                                    }
                                },
                                isMovieSaved = isSaved
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun FavoritesScreenPreview() {
    FavoritesScreen(defaultFavoritesState())
}
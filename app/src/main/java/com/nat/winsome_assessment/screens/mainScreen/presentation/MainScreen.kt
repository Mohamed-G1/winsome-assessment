package com.nat.winsome_assessment.screens.mainScreen.presentation

import android.annotation.SuppressLint
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import com.nat.winsome_assessment.application.general.GeneralState
import com.nat.winsome_assessment.application.general.HandleGeneralCompose
import com.nat.winsome_assessment.application.general.defaultGeneralState
import com.nat.winsome_assessment.application.presentation.Loading
import com.nat.winsome_assessment.application.presentation.MovieImageLoader
import com.nat.winsome_assessment.ui.theme.largeTitle

@Composable
fun MainScreen(
    state: MainScreenState,
    event: ((MainScreenEvent) -> Unit)? = null,
    generalState: GeneralState,
    navigateToDetailsScreen: ((Int) -> Unit)? = null
) {
    ScreenContent(
        state = state,
        event = event,
        generalState = generalState,
        navigateToDetailsScreen = navigateToDetailsScreen
    )
}

@SuppressLint("DefaultLocale")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScreenContent(
    state: MainScreenState,
    event: ((MainScreenEvent) -> Unit)? = null,
    generalState: GeneralState,
    navigateToDetailsScreen: ((Int) -> Unit)? = null
) {
    var currentQuery by remember { mutableStateOf("") }
    var isSearchActive by remember { mutableStateOf(currentQuery.isNotEmpty()) }
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current

    Scaffold(
        topBar = {
            TopAppBar(title = {
                // If the search is not active show the App name
                if (!isSearchActive) {
                    Text(
                        text = stringResource(R.string.movie_app),
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth(),
                        style = largeTitle
                    )
                    // Otherwise open the TextFiled, so the use can search
                } else {
                    TextField(
                        value = currentQuery,
                        onValueChange = { newQuery ->
                            currentQuery = newQuery
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(55.dp)
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
                            // Call search Api
                            event?.invoke(MainScreenEvent.SearchOnMovie(query = currentQuery))
                            focusManager.clearFocus()
                            keyboardController?.hide()
                        })
                    )
                }
            },
                // Toggle between search and cancel actions
                actions = {
                    if (!isSearchActive) {
                        IconButton(onClick = { isSearchActive = true }) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_search),
                                contentDescription = null
                            )
                        }
                    } else {
                        IconButton(onClick = { isSearchActive = false }) {
                            Icon(Icons.Default.Close, contentDescription = null)
                        }
                    }
                })
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            Text(
                text = stringResource(R.string.now_showing),
                style = largeTitle,
                modifier = Modifier.padding(top = 16.dp, start = 16.dp)
            )
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.spacedBy(24.dp),
            ) {

                items(items = state.model ?: emptyList(), key = { it.id ?: 0 }) { movie ->
                    // This to only extract the first digit instead of the all digits after the number
                    val rate = String.format("%.1f", movie.rate)

                    MovieImageLoader(
                        imageUrl = movie.moviePoster ?: "",
                        title = movie.movieName ?: "",
                        rate = rate,
                        onClick = { navigateToDetailsScreen?.invoke(movie.id ?: 0) }
                    )
                }
            }
        }
    }

    if (state.isLoading) {
        Loading()
    }
    HandleGeneralCompose(state = generalState)
}

@Preview
@Composable
fun MainScreenPreview() {
    MainScreen(state = defaultMainState(), generalState = defaultGeneralState())
}
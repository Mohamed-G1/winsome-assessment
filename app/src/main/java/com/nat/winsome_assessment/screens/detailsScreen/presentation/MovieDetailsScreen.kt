package com.nat.winsome_assessment.screens.detailsScreen.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.nat.winsome_assessment.R
import com.nat.winsome_assessment.application.data.local.db.entity.toUiModel
import com.nat.winsome_assessment.application.presentation.CastImageLoader
import com.nat.winsome_assessment.application.presentation.Loading
import com.nat.winsome_assessment.application.presentation.general.GeneralState
import com.nat.winsome_assessment.application.presentation.general.HandleGeneralCompose
import com.nat.winsome_assessment.application.presentation.general.StatusBarIconColoring
import com.nat.winsome_assessment.application.presentation.general.defaultGeneralState
import com.nat.winsome_assessment.application.utils.Constants.IMAGE_URL
import com.nat.winsome_assessment.application.utils.SaveIconState
import com.nat.winsome_assessment.application.utils.toHoursAndMinutes
import com.nat.winsome_assessment.application.utils.toVote
import com.nat.winsome_assessment.ui.theme.SoftBlue
import com.nat.winsome_assessment.ui.theme.SoftGray
import com.nat.winsome_assessment.ui.theme.VerySoftBlue
import com.nat.winsome_assessment.ui.theme.Yellow
import com.nat.winsome_assessment.ui.theme.largeTitle
import com.nat.winsome_assessment.ui.theme.movieTitleStyle
import kotlinx.coroutines.flow.Flow

@Composable
fun MovieDetailsScreen(
    state: MovieDetailsState,
    event: ((MovieDetailsEvent) -> Unit)? = null,
    generalState: GeneralState,
    movieId: Int? = null,
    checkIsMovieSaved: ((Int) -> Flow<Boolean>)? = null,
    navigateToFavorites: (() -> Unit)? = null,
    navigateBack: (() -> Unit)? = null
) {
    StatusBarIconColoring(showAsDarkIcons = true)
    LaunchedEffect(movieId) {
        event?.invoke(MovieDetailsEvent.GetMovieDetails(movieId = movieId ?: 0))
    }
    if (state.isLoading) {
        Loading()
    } else {
        ScreenContent(
            state = state,
            event = event,
            generalState = generalState,
            checkIsMovieSaved = checkIsMovieSaved,
            navigateToFavorites = navigateToFavorites,
            navigateBack = navigateBack
        )
    }
}

@Composable
fun ScreenContent(
    state: MovieDetailsState,
    event: ((MovieDetailsEvent) -> Unit)? = null,
    generalState: GeneralState,
    checkIsMovieSaved: ((Int) -> Flow<Boolean>)? = null,
    navigateBack: (() -> Unit)? = null,
    navigateToFavorites: (() -> Unit)? = null
) {
    var isMenuExpanded by remember { mutableStateOf(false) }

    // Check if the movie is exists in the DB
    val isSaved = checkIsMovieSaved?.invoke(state.model?.movieId ?: 0)
        ?.collectAsState(initial = false)?.value ?: false

    // This will ensure that the icon only changes when the value changes to preventing unnecessary recompositions
    val saveIcon by remember(isSaved) {
        derivedStateOf {
            if (isSaved) {
                SaveIconState(
                    icon = R.drawable.ic_saved,
                    tint = Yellow
                )
            } else {
                SaveIconState(
                    icon = R.drawable.ic_not_saved,
                    tint = Color.Black
                )
            }
        }
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        AsyncImage(
            model = "$IMAGE_URL${state.model?.moviePoster}",
            contentDescription = null,
            placeholder = painterResource(id = R.drawable.ic_no_photography),
            error = painterResource(id = R.drawable.ic_no_photography),
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp),
            contentScale = ContentScale.Crop
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp, vertical = 40.dp)
                .align(Alignment.TopCenter),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            IconButton(onClick = { navigateBack?.invoke() }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_back),
                    contentDescription = null,
                    tint = Color.White
                )
            }

            Column {
                IconButton(onClick = { isMenuExpanded = isMenuExpanded.not() }) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_menu),
                        contentDescription = null,
                        tint = Color.White
                    )
                }

                DropdownMenu(
                    expanded = isMenuExpanded,
                    onDismissRequest = { isMenuExpanded = false },
                    modifier = Modifier.background(Color.White)
                ) {
                    DropdownMenuItem(
                        text = {
                            Text(
                                text = stringResource(R.string.favorites_list),
                                style = movieTitleStyle
                            )
                        },
                        onClick = { navigateToFavorites?.invoke() },
                        modifier = Modifier.clip(RoundedCornerShape(12.dp))
                    )
                }
            }

        }

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.70f)
                .align(Alignment.BottomCenter),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            shape = RoundedCornerShape(
                topStart = 16.dp,
                topEnd = 16.dp,
                bottomEnd = 0.dp,
                bottomStart = 0.dp
            )
        )
        {
            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                item {
                    Column(
                        modifier = Modifier.padding(horizontal = 20.dp, vertical = 24.dp)
                    ) {
                        Row {
                            Text(
                                text = state.model?.movieName ?: "",
                                style = movieTitleStyle.copy(fontSize = 20.sp),
                                modifier = Modifier.weight(1f),
                                lineHeight = 24.sp
                            )
                            IconButton(onClick = {
                                state.model?.toUiModel()
                                    ?.let { movie ->
                                        MovieDetailsEvent.AddAndDeleteMovie(movie = movie)
                                    }
                                    ?.let {
                                        event?.invoke(
                                            it
                                        )
                                    }
                            }) {
                                Icon(
                                    painter = painterResource(id = saveIcon.icon),
                                    contentDescription = null,
                                    modifier = Modifier.size(32.dp),
                                    tint = saveIcon.tint
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.ic_star),
                                contentDescription = null
                            )
                            val rateValue =
                                StringBuilder().append(state.model?.rate?.toVote())
                                    .append("/10")
                                    .append(" ")
                                    .append(stringResource(R.string.imdb))
                                    .toString()
                            Text(
                                text = rateValue,
                                style = movieTitleStyle.copy(
                                    color = SoftGray,
                                    fontWeight = FontWeight.Normal
                                )
                            )
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        Row(
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {

                            state.model?.genre?.forEach { genre ->
                                Card(
                                    colors = CardDefaults.cardColors(containerColor = VerySoftBlue),
                                    shape = RoundedCornerShape(16.dp)
                                ) {
                                    Text(
                                        text = genre.name ?: "",
                                        modifier = Modifier.padding(
                                            horizontal = 12.dp,
                                            vertical = 4.dp
                                        ),
                                        style = movieTitleStyle.copy(
                                            color = SoftBlue,
                                            fontSize = 10.sp
                                        )
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        Row(
                            horizontalArrangement = Arrangement.spacedBy(64.dp)
                        ) {
                            Column(
                                verticalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                Text(
                                    text = stringResource(R.string.length),
                                    style = movieTitleStyle.copy(
                                        color = SoftGray,
                                        fontSize = 12.sp
                                    )
                                )

                                Text(
                                    text = state.model?.movieLength?.toHoursAndMinutes() ?: "",
                                    style = movieTitleStyle.copy(fontSize = 12.sp)
                                )
                            }

                            Column(
                                verticalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                Text(
                                    text = stringResource(R.string.language),
                                    style = movieTitleStyle.copy(
                                        color = SoftGray,
                                        fontSize = 12.sp
                                    )
                                )

                                Text(
                                    text = state.model?.language ?: "",
                                    style = movieTitleStyle.copy(fontSize = 12.sp)
                                )
                            }

                            Column(
                                verticalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                Text(
                                    text = stringResource(R.string.rating),
                                    style = movieTitleStyle.copy(
                                        color = SoftGray,
                                        fontSize = 12.sp
                                    )
                                )

                                Text(
                                    text = state.model?.voteCount?.toString() ?: "",
                                    style = movieTitleStyle.copy(fontSize = 12.sp)
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(24.dp))

                        Text(
                            text = stringResource(R.string.description),
                            style = largeTitle,
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        Text(
                            text = state.model?.movieDescription ?: "",
                            style = movieTitleStyle.copy(color = SoftGray, fontSize = 12.sp),
                            lineHeight = 24.sp
                        )

                        Spacer(modifier = Modifier.height(24.dp))

                        Text(
                            text = stringResource(R.string.cast),
                            style = largeTitle,
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        LazyRow(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 32.dp),
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            items(items = state.cast ?: emptyList()) { person ->
                                CastImageLoader(
                                    imageUrl = person.personImage ?: "",
                                    personName = person.personName ?: ""
                                )
                            }
                        }
                    }
                }
            }
        }
    }
    HandleGeneralCompose(state = generalState)
}

@Preview
@Composable
private fun MovieDetailsScreenPreview() {
    MovieDetailsScreen(state = defaultMovieDetailsState(), generalState = defaultGeneralState())
}


package com.nat.winsome_assessment.application.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
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
import coil.compose.AsyncImage
import com.nat.winsome_assessment.R
import com.nat.winsome_assessment.application.utils.SaveIconState
import com.nat.winsome_assessment.application.utils.Constants.IMAGE_URL
import com.nat.winsome_assessment.application.utils.toVote
import com.nat.winsome_assessment.screens.mainScreen.domain.models.MovieUiModel
import com.nat.winsome_assessment.ui.theme.SoftGray
import com.nat.winsome_assessment.ui.theme.Yellow
import com.nat.winsome_assessment.ui.theme.movieTitleStyle
import java.lang.StringBuilder

@Composable
fun MovieImageLoader(
    model: MovieUiModel?,
    onClick: (() -> Unit)? = null,
    onSaveClick: (() -> Unit)? = null,
    isMovieSaved: Boolean
) {
    // This will ensure that the icon only changes when the value changes to preventing unnecessary recompositions
    val saveIcon by remember(isMovieSaved) {
        derivedStateOf {
            if (isMovieSaved) {
                SaveIconState(
                    icon = R.drawable.ic_saved,
                    tint = Yellow
                )
            } else {
                SaveIconState(
                    icon = R.drawable.ic_not_saved,
                    tint = Color.White
                )
            }
        }
    }

    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp),
        modifier = Modifier.padding(16.dp)

    ) {
        Box(
            modifier = Modifier.wrapContentSize()
        ) {
            Card(
                onClick = { onClick?.invoke() },
                modifier = Modifier
                    .height(230.dp)
                    .align(Alignment.Center),
                elevation = CardDefaults.outlinedCardElevation(6.dp)
            ) {
                AsyncImage(
                    model = "$IMAGE_URL${model?.moviePoster}",
                    contentDescription = null,
                    placeholder = painterResource(id = R.drawable.ic_no_photography),
                    error = painterResource(id = R.drawable.ic_no_photography),
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(8.dp))
                )
            }

            IconButton(
                onClick = { onSaveClick?.invoke() },
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(8.dp)
            ) {
                Icon(
                    painter = painterResource(id = saveIcon.icon),
                    contentDescription = null,
                    modifier = Modifier.size(32.dp),
                    tint = saveIcon.tint
                )
            }
        }

        Text(
            text = model?.movieName ?: "",
            style = movieTitleStyle,
            maxLines = 2,
            modifier = Modifier.fillMaxWidth()
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_star),
                contentDescription = null
            )
            val rateValue =
                StringBuilder().append(model?.rate?.toVote()).append("/10").append(" ")
                    .append(stringResource(R.string.imdb))
                    .toString()
            Text(
                text = rateValue,
                style = movieTitleStyle.copy(color = SoftGray, fontWeight = FontWeight.Normal)
            )
        }
    }
}

@Preview
@Composable
private fun MovieImagePreview() {
    MovieImageLoader(model = null, isMovieSaved = false)
}
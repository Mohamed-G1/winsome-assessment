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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.nat.winsome_assessment.ui.theme.SoftGray
import com.nat.winsome_assessment.ui.theme.movieTitleStyle
import java.lang.StringBuilder

const val Url = "https://image.tmdb.org/t/p/w500"

@Composable
fun MovieImageLoader(
    imageUrl: String,
    title: String,
    rate: String,
    onClick:(() -> Unit)? = null
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp),
        modifier = Modifier.padding(16.dp)

    ) {
        Box {

            Card(
                onClick = { onClick?.invoke() },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(212.dp),
                elevation = CardDefaults.outlinedCardElevation(6.dp)
            ) {
                AsyncImage(
                    model = "$Url$imageUrl",
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(8.dp))
                )
            }


            IconButton(
                onClick = { /*TODO*/ }, modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(8.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_bookmark),
                    contentDescription = null,
                    tint = Color.White
                )
            }
        }

        Text(
            text = title,
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
                StringBuilder().append(rate).append("/10").append(" ").append(stringResource(R.string.imdb))
                    .toString()
            Text(text = rateValue, style = movieTitleStyle.copy(color = SoftGray, fontWeight = FontWeight.Normal))

        }
    }
}

@Preview
@Composable
private fun MovieImagePreview() {
    MovieImageLoader("", "Title", "9/10")
}
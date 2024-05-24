package com.nat.winsome_assessment.application.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.nat.winsome_assessment.R
import com.nat.winsome_assessment.application.utils.Constants.IMAGE_URL
import com.nat.winsome_assessment.ui.theme.DarkBlue
import com.nat.winsome_assessment.ui.theme.movieTitleStyle


@Composable
fun CastImageLoader(
    imageUrl: String,
    personName: String
) {
    Column(
        modifier = Modifier.width(80.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        AsyncImage(
            model = "$IMAGE_URL$imageUrl",
            contentDescription = null,
            placeholder = painterResource(id = R.drawable.ic_no_photography),
            error = painterResource(id = R.drawable.ic_no_photography),
            modifier = Modifier
                .size(80.dp)
                .clip(RoundedCornerShape(8.dp)),
            contentScale = ContentScale.Crop
        )
        Text(
            text = personName,
            style = movieTitleStyle.copy(color = DarkBlue, fontSize = 12.sp),

        )
    }
}

@Preview
@Composable
private fun CastImageLoaderPreview() {
    CastImageLoader("", "Mohamed")
}
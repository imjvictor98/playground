package br.com.cvj.playground.ui.widget

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import br.com.cvj.playground.R
import kotlin.math.ceil
import kotlin.math.floor

@Composable
fun RatingBar(
    modifier: Modifier = Modifier,
    rating: Double = 0.0,
    stars: Int = 5,
    starsColor: Color = Color.Yellow,
    starSize: Dp = 12.dp
) {

    val filledStars = floor(rating).toInt()
    val unfilledStars = (stars - ceil(rating)).toInt()
    val halfStar = !(rating.rem(1).equals(0.0))

    Row(modifier = modifier) {
        repeat(filledStars) {
            Icon(modifier = Modifier.size(starSize), painter = painterResource(id = R.drawable.ic_outline_star_24), tint = starsColor, contentDescription = "")
        }

        if (halfStar) {
            Icon(modifier = Modifier.size(starSize), painter = painterResource(id = R.drawable.ic_outline_star_half_24), tint = starsColor, contentDescription = "")
        }

        repeat(unfilledStars) {
            Icon(modifier = Modifier.size(starSize), painter = painterResource(id = R.drawable.ic_outline_star_outline_24), tint = starsColor, contentDescription = "")
        }
    }
}


@Preview
@Composable
fun RatingPreview() {
    RatingBar(rating = 2.5)
}

@Preview
@Composable
fun TenStarsRatingPreview() {
    RatingBar(stars = 10, rating = 8.5)
}

@Preview
@Composable
fun RatingPreviewFull() {
    RatingBar(rating = 5.0)
}

@Preview
@Composable
fun RatingPreviewWorst() {
    RatingBar(rating = 1.0)
}

@Preview
@Composable
fun RatingPreviewDisabled() {
    RatingBar(rating = 0.0, starsColor = Color.Gray)
}
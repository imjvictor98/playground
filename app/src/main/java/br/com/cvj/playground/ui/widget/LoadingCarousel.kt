package br.com.cvj.playground.ui.widget

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.cvj.playground.R
import br.com.cvj.playground.ui.theme.Colors
import br.com.cvj.playground.ui.theme.PlaygroundTheme
import br.com.cvj.playground.ui.theme.Typography
import kotlinx.coroutines.delay

@Composable
fun LoadingCarousel(
    backgroundColor: Color = Colors.Blue850
) {
    val images = listOf(
        Pair(R.drawable.weather_loading_1, stringResource(id = R.string.loading_carousel_text_1)),
        Pair(R.drawable.weather_loading_6, stringResource(id = R.string.loading_carousel_text_2)),
        Pair(R.drawable.weather_loading_3, stringResource(id = R.string.loading_carousel_text_3)),
        Pair(R.drawable.weather_loading_4, stringResource(id = R.string.loading_carousel_text_4)),
        Pair(R.drawable.weather_loading_5, stringResource(id = R.string.loading_carousel_text_5)),
        Pair(R.drawable.weather_loading_2, stringResource(id = R.string.loading_carousel_text_6))
    )

    var index by remember { mutableIntStateOf(0) }

    LaunchedEffect(key1 = true) {
        while (true) {
            if (index == images.indices.last()) {
                index = 0
            } else {
                index++
            }
            delay(4000)
        }
    }

    Column(
        modifier = Modifier.fillMaxSize().background(backgroundColor).padding(8.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = images[index].first),
            contentDescription = "Loading Image",
            modifier = Modifier.size(200.dp)
        )
        Text(
            text = images[index].second,
            style = Typography.bodyLarge,
            textAlign = TextAlign.Center,
            fontSize = 16.sp,
            modifier = Modifier.padding(top = 48.dp),
            color = Colors.White
        )
    }
}

@Composable
@Preview
fun LoadingCarouselPreview() {
    PlaygroundTheme {
        Surface {
            LoadingCarousel()
        }
    }

}
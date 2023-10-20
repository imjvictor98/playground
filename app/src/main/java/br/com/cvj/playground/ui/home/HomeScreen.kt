package br.com.cvj.playground.ui.home

import android.location.Location
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import br.com.cvj.playground.R
import br.com.cvj.playground.data.network.ApiFactory
import br.com.cvj.playground.domain.model.forecast.Day
import br.com.cvj.playground.domain.model.forecast.DayByType
import br.com.cvj.playground.domain.model.forecast.DayByTypeDTO
import br.com.cvj.playground.domain.model.forecast.ForecastDTO
import br.com.cvj.playground.domain.model.forecast.ForecastDay
import br.com.cvj.playground.domain.model.forecast.Hour
import br.com.cvj.playground.domain.model.weather.WeatherCurrent
import br.com.cvj.playground.ui.theme.Colors
import br.com.cvj.playground.ui.theme.hirukoProFamily
import br.com.cvj.playground.ui.theme.mazzardDmFamily
import br.com.cvj.playground.ui.widget.atom.TextTabAtom
import br.com.cvj.playground.ui.widget.molecule.SearchableHeaderMolecule
import br.com.cvj.playground.ui.widget.molecule.TabLayoutMolecule
import br.com.cvj.playground.util.extension.applyScheme
import br.com.cvj.playground.util.extension.format
import br.com.cvj.playground.util.extension.getCurrentHourIndex
import br.com.cvj.playground.util.extension.getDateForTab
import br.com.cvj.playground.util.extension.isEqualsToCurrent
import br.com.cvj.playground.util.extension.isSameDay
import br.com.cvj.playground.util.helper.LocationHelper
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.placeholder
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.Date

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel = viewModel()
) {
    val context = LocalContext.current
    viewModel.initialize(ApiFactory.getWeatherServices(context))

    val homeUiState = viewModel.uiState.collectAsState()

    when (val state = homeUiState.value) {
        is HomeUiState.IsLoading -> {
            if (state.isLoading) {
                Box(contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(color = Colors.Salmon, strokeWidth = 5.dp)
                }
            }
        }

        is HomeUiState.Success -> {
            val pagerState =
                rememberPagerState(
                    initialPage = 0,
                    initialPageOffsetFraction = 0F
                ) { state.forecasts.size }
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Colors.Black100)
            ) {
                SearchableHeaderMolecule(
                    text = state.weatherLocation.getRegionFormatted(),
                    modifier = Modifier.padding(16.dp)
                )
                TabLayout(state.forecasts, pagerState)
                TabContent(state.forecasts, pagerState)
            }
        }
    }

    LocationHelper.getLastLocation(context, object : LocationHelper.LocationAvailabilityListener {
        override fun onSuccess(location: Location) {
            viewModel.requestForecast(location)
        }

        override fun onFailure() {
            Timber.d("Location failed")
            //do nothing for while
        }

        override fun onError(exception: Exception) {
            Timber.e(exception)
        }
    })

}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TabLayout(forecasts: List<ForecastDTO>, pagerState: PagerState) {

    val scope = rememberCoroutineScope()

    TabLayoutMolecule(pagerState = pagerState, tabData = forecasts, text = { index, forecastItem ->
        TextTabAtom(
            text = forecastItem.getDateForTab().toString(),
            textColor = if (pagerState.currentPage == index) Color.White else Colors.Gray100
        )
    }, onClick = { index, _ ->
        scope.launch {
            pagerState.animateScrollToPage(index)
        }
    })
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TabContent(
    forecasts: List<ForecastDTO>,
    pagerState: PagerState
) {
    HorizontalPager(state = pagerState, userScrollEnabled = true) { index ->
        if (index <= forecasts.size - 1) {
            TabItem(forecasts[index])
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun TabItem(forecast: ForecastDTO) {
    Column(Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .clip(RoundedCornerShape(24.dp))
                .background(Colors.Blue850)
                .padding(vertical = 8.dp),


            ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                GlideImage(
                    modifier = Modifier
                        .size(100.dp)
                        .padding(horizontal = 8.dp),
                    loading = placeholder(R.drawable.ic_placeholder_rainbow),
                    failure = placeholder(R.drawable.ic_placeholder_rainbow),
                    model = forecast.current?.condition?.icon?.applyScheme()
                        ?.replace("64", "128").toString(),
                    contentDescription = ""
                )
                Text(
                    modifier = Modifier.padding(top = 16.dp, end = 8.dp),
                    fontSize = 60.sp,
                    fontFamily = mazzardDmFamily,
                    fontWeight = FontWeight.Normal,
                    color = Colors.White,
                    text = forecast.current?.tempC?.toInt().toString() + "°C"
                )
            }
            TabResumeDayList(days = forecast.getConditionsForDay())
        }
        TabResumeHoursList(
            hours = forecast.forecastDay?.hour ?: emptyList(),
            forecast.forecastDay?.date.toString()
        )
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun TabResumeHoursList(hours: List<Hour>, dateAsString: String) {
    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()
    var currentHour: Hour? = null

    LazyRow(
        state = listState,
        contentPadding = PaddingValues(4.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        items(hours) { hour ->
            if (currentHour == null && Date().isSameDay(dateAsString)) {
                currentHour = hour
            }
            Column(
                modifier = Modifier
                    .height(110.dp)
                    .padding(horizontal = 8.dp, vertical = 2.dp)
                    .background(
                        if (hour.time?.isEqualsToCurrent("HH") == true && Date().isSameDay(
                                dateAsString
                            )
                        ) Colors.Blue500 else Colors.Transparent,
                        shape = RoundedCornerShape(8.dp)
                    )
                    .padding(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                GlideImage(
                    modifier = Modifier,
                    loading = placeholder(R.drawable.ic_placeholder_rainbow),
                    failure = placeholder(R.drawable.ic_placeholder_rainbow),
                    model = hour.condition?.icon?.applyScheme()?.replace("64", "128"),
                    contentDescription = ""
                )
                Text(
                    modifier = Modifier.padding(top = 6.dp),
                    text = hour.tempC?.toInt().toString() + "°C",
                    fontSize = 12.sp,
                    fontFamily = mazzardDmFamily,
                    fontWeight = FontWeight.Normal,
                    color = Colors.White
                )
                Text(
                    modifier = Modifier.padding(top = 12.dp),
                    text = hour.time?.format("HH:mm").toString(),
                    fontSize = 14.sp,
                    fontFamily = hirukoProFamily,
                    fontWeight = FontWeight.Normal,
                    color = Colors.White
                )
            }

        }
    }

    LaunchedEffect(key1 = "Trying to get current hour") {
        coroutineScope.launch {
            val currentIndex = currentHour?.getCurrentHourIndex(hours)
            if (currentIndex != -1 && currentIndex != null) {
                listState.scrollToItem(currentIndex)
            }
        }
    }
}

@Composable
fun TabResumeDayList(days: List<DayByTypeDTO>) {
    LazyVerticalGrid(
        modifier = Modifier.padding(horizontal = 4.dp),
        columns = GridCells.Fixed(3),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(4.dp)
    ) {
        items(days) { currentDay ->
            Column(
                modifier = Modifier
                    .height(100.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(Colors.Blue950)
                    .padding(16.dp),

                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                when (currentDay.type) {
                    DayByType.WIND -> {
                        TabResumeDayItem(
                            weatherDayIcon = painterResource(id = R.drawable.ic_wind_18dp_blue),
                            weatherDayTitle = stringResource(id = R.string.weather_resume_wind),
                            weatherDayDescription = stringResource(
                                id = R.string.string_format_kmh,
                                currentDay.day.maxwindKph?.toInt().toString()
                            )
                        )
                    }

                    DayByType.HUMIDITY -> {
                        TabResumeDayItem(
                            weatherDayIcon = painterResource(id = R.drawable.ic_humidity_18dp_blue),
                            weatherDayTitle = stringResource(id = R.string.weather_resume_humidity),
                            weatherDayDescription = stringResource(
                                id = R.string.string_format_percent,
                                currentDay.day.avghumidity?.toInt().toString()
                            )
                        )
                    }

                    DayByType.CHANCE_OF_RAIN -> {
                        TabResumeDayItem(
                            weatherDayIcon = painterResource(id = R.drawable.ic_chance_rain_18dp_blue),
                            weatherDayTitle = stringResource(id = R.string.weather_resume_chance_of_rain),
                            weatherDayDescription = stringResource(
                                id = R.string.string_format_percent,
                                currentDay.day.dailyChanceOfRain.toString()
                            )
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun TabResumeDayItem(
    weatherDayIcon: Painter,
    weatherDayTitle: String,
    weatherDayDescription: String,
) {
    Image(
        painter = weatherDayIcon,
        contentDescription = ""
    )
    Text(
        modifier = Modifier.padding(top = 6.dp),
        text = weatherDayTitle,
        fontSize = 12.sp,
        fontFamily = mazzardDmFamily,
        fontWeight = FontWeight.Normal,
        color = Colors.White
    )
    Text(
        modifier = Modifier.padding(top = 6.dp),
        text = weatherDayDescription,
        fontSize = 14.sp,
        fontFamily = hirukoProFamily,
        fontWeight = FontWeight.Normal,
        color = Colors.White
    )
}

@Composable
@Preview("TabItem", showBackground = true, backgroundColor = 0x333)
fun TabItemPreview() {
    TabItem(
        forecast = ForecastDTO(
            current = WeatherCurrent(tempC = 28.0),
            forecastDay = ForecastDay(date = "2023-10-19", day = Day())
        )
    )
}
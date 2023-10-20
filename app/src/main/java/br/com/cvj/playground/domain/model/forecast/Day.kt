package br.com.cvj.playground.domain.model.forecast


import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import br.com.cvj.playground.R
import br.com.cvj.playground.domain.model.weather.WeatherCondition
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.io.Serializable

@JsonClass(generateAdapter = true)
data class Day(
    @Json(name = "avghumidity")
    val avghumidity: Double? = 0.0,
    @Json(name = "avgtemp_c")
    val avgtempC: Double? = 0.0,
    @Json(name = "avgtemp_f")
    val avgtempF: Double? = 0.0,
    @Json(name = "avgvis_km")
    val avgvisKm: Double? = 0.0,
    @Json(name = "avgvis_miles")
    val avgvisMiles: Double? = 0.0,
    @Json(name = "condition")
    val condition: WeatherCondition? = null,
    @Json(name = "daily_chance_of_rain")
    val dailyChanceOfRain: Int? = 0,
    @Json(name = "daily_chance_of_snow")
    val dailyChanceOfSnow: Int? = 0,
    @Json(name = "daily_will_it_rain")
    val dailyWillItRain: Int? = 0,
    @Json(name = "daily_will_it_snow")
    val dailyWillItSnow: Int? = 0,
    @Json(name = "maxtemp_c")
    val maxtempC: Double? = 0.0,
    @Json(name = "maxtemp_f")
    val maxtempF: Double? = 0.0,
    @Json(name = "maxwind_kph")
    val maxwindKph: Double? = 0.0,
    @Json(name = "maxwind_mph")
    val maxwindMph: Double? = 0.0,
    @Json(name = "mintemp_c")
    val mintempC: Double? = 0.0,
    @Json(name = "mintemp_f")
    val mintempF: Double? = 0.0,
    @Json(name = "totalprecip_in")
    val totalprecipIn: Double? = 0.0,
    @Json(name = "totalprecip_mm")
    val totalprecipMm: Double? = 0.0,
    @Json(name = "totalsnow_cm")
    val totalsnowCm: Double? = 0.0,
    @Json(name = "uv")
    val uv: Double? = 0.0
): Serializable {
    fun getDayByTypeDTO(type: DayByType) = DayByTypeDTO(type, this)

}

enum class DayByType : Serializable{
    WIND,
    HUMIDITY,
    CHANCE_OF_RAIN
}

data class DayByTypeDTO(
    val type: DayByType,
    val day: Day
): Serializable {
    fun getProviderResources(): DayTypeProvider {
        return when (type) {
            DayByType.WIND -> DayTypeProviderByWind(day)
            DayByType.HUMIDITY -> DayTypeProviderByHumidity(day)
            DayByType.CHANCE_OF_RAIN -> DayTypeProviderByChanceOfRain(day)
        }
    }
}

interface DayTypeProvider {
    @Composable
    fun getIcon(): Painter
    @Composable
    fun getTitle(): String
    @Composable
    fun getDescription(): String
}

class DayTypeProviderByWind(private val day: Day): DayTypeProvider {
    @Composable
    override fun getIcon(): Painter {
        return painterResource(id = R.drawable.ic_wind_18dp_blue)
    }

    @Composable
    override fun getTitle(): String {
        return stringResource(id = R.string.weather_resume_wind)
    }

    @Composable
    override fun getDescription(): String {
        return stringResource(
            id = R.string.string_format_kmh,
            day.maxwindKph?.toInt().toString()
        )
    }
}
class DayTypeProviderByHumidity(private val day: Day): DayTypeProvider {
    @Composable
    override fun getIcon(): Painter {
        return painterResource(id = R.drawable.ic_humidity_18dp_blue)
    }

    @Composable
    override fun getTitle(): String {
        return stringResource(id = R.string.weather_resume_humidity)
    }

    @Composable
    override fun getDescription(): String {
        return stringResource(
            id = R.string.string_format_percent,
            day.avghumidity?.toInt().toString()
        )
    }
}
class DayTypeProviderByChanceOfRain(private val day: Day): DayTypeProvider {
    @Composable
    override fun getIcon(): Painter {
        return painterResource(id = R.drawable.ic_chance_rain_18dp_blue)
    }

    @Composable
    override fun getTitle(): String {
        return stringResource(id = R.string.weather_resume_chance_of_rain)
    }

    @Composable
    override fun getDescription(): String {
        return stringResource(
            id = R.string.string_format_percent,
            day.dailyChanceOfRain.toString()
        )
    }
}
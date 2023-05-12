package br.com.cvj.playground.domain.model.forecast


import br.com.cvj.playground.domain.model.weather.WeatherCondition
import com.google.gson.annotations.SerializedName
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

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
) {
    fun getDayByTypeDTO(type: DayByType) = DayByTypeDTO(type, this)

}

enum class DayByType {
    WIND,
    HUMIDITY,
    CHANCE_OF_RAIN
}

data class DayByTypeDTO(
    val type: DayByType,
    val day: Day
)
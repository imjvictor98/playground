package br.com.cvj.playground.domain.model.forecast


import br.com.cvj.playground.domain.model.weather.WeatherCondition
import com.google.gson.annotations.SerializedName
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Hour(
    @Json(name = "chance_of_rain")
    val chanceOfRain: Int? = 0,
    @Json(name = "chance_of_snow")
    val chanceOfSnow: Int? = 0,
    @Json(name = "cloud")
    val cloud: Int? = 0,
    @Json(name = "condition")
    val condition: WeatherCondition? = null,
    @Json(name = "dewpoint_c")
    val dewpointC: Double? = 0.0,
    @Json(name = "dewpoint_f")
    val dewpointF: Double? = 0.0,
    @Json(name = "feelslike_c")
    val feelslikeC: Double? = 0.0,
    @Json(name = "feelslike_f")
    val feelslikeF: Double? = 0.0,
    @Json(name = "gust_kph")
    val gustKph: Double? = 0.0,
    @Json(name = "gust_mph")
    val gustMph: Double? = 0.0,
    @Json(name = "heatindex_c")
    val heatindexC: Double? = 0.0,
    @Json(name = "heatindex_f")
    val heatindexF: Double? = 0.0,
    @Json(name = "humidity")
    val humidity: Int? = 0,
    @Json(name = "is_day")
    val isDay: Int? = 0,
    @Json(name = "precip_in")
    val precipIn: Double? = 0.0,
    @Json(name = "precip_mm")
    val precipMm: Double? = 0.0,
    @Json(name = "pressure_in")
    val pressureIn: Double? = 0.0,
    @Json(name = "pressure_mb")
    val pressureMb: Double? = 0.0,
    @Json(name = "temp_c")
    val tempC: Double? = 0.0,
    @Json(name = "temp_f")
    val tempF: Double? = 0.0,
    @Json(name = "time")
    val time: String? = "",
    @Json(name = "time_epoch")
    val timeEpoch: Int? = 0,
    @Json(name = "uv")
    val uv: Double? = 0.0,
    @Json(name = "vis_km")
    val visKm: Double? = 0.0,
    @Json(name = "vis_miles")
    val visMiles: Double? = 0.0,
    @Json(name = "will_it_rain")
    val willItRain: Int? = 0,
    @Json(name = "will_it_snow")
    val willItSnow: Int? = 0,
    @Json(name = "wind_degree")
    val windDegree: Int? = 0,
    @Json(name = "wind_dir")
    val windDir: String? = "",
    @Json(name = "wind_kph")
    val windKph: Double? = 0.0,
    @Json(name = "wind_mph")
    val windMph: Double? = 0.0,
    @Json(name = "windchill_c")
    val windchillC: Double? = 0.0,
    @Json(name = "windchill_f")
    val windchillF: Double? = 0.0
)
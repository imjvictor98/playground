package br.com.cvj.playground.domain.model.weather


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.io.Serializable

@JsonClass(generateAdapter = true)
data class WeatherCurrent(
    @Json(name = "cloud")
    val cloud: Int? = null,
    @Json(name = "condition")
    val condition: WeatherCondition? = null,
    @Json(name = "feelslike_c")
    val feelslikeC: Double? = null,
    @Json(name = "feelslike_f")
    val feelslikeF: Double? = null,
    @Json(name = "gust_kph")
    val gustKph: Double? = null,
    @Json(name = "gust_mph")
    val gustMph: Double? = null,
    @Json(name = "humidity")
    val humidity: Int? = null,
    @Json(name = "is_day")
    val isDay: Int? = null,
    @Json(name = "last_updated")
    val lastUpdated: String? = null,
    @Json(name = "last_updated_epoch")
    val lastUpdatedEpoch: Int? = null,
    @Json(name = "precip_in")
    val precipIn: Double? = null,
    @Json(name = "precip_mm")
    val precipMm: Double? = null,
    @Json(name = "pressure_in")
    val pressureIn: Double? = null,
    @Json(name = "pressure_mb")
    val pressureMb: Double? = null,
    @Json(name = "temp_c")
    val tempC: Double? = null,
    @Json(name = "temp_f")
    val tempF: Double? = null,
    @Json(name = "uv")
    val uv: Double? = null,
    @Json(name = "vis_km")
    val visKm: Double? = null,
    @Json(name = "vis_miles")
    val visMiles: Double? = null,
    @Json(name = "wind_degree")
    val windDegree: Int? = null,
    @Json(name = "wind_dir")
    val windDir: String? = null,
    @Json(name = "wind_kph")
    val windKph: Double? = null,
    @Json(name = "wind_mph")
    val windMph: Double? = null
): Serializable
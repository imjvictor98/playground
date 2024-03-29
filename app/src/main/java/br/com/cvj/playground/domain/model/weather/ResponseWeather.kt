package br.com.cvj.playground.domain.model.weather


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.io.Serializable

@JsonClass(generateAdapter = true)
data class ResponseWeather(
    @Json(name = "current")
    val current: WeatherCurrent? = null,
    @Json(name = "location")
    val location: WeatherLocation? = null
): Serializable
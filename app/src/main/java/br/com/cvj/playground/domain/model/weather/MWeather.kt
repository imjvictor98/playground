package br.com.cvj.playground.domain.model.weather


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MWeather(
    @Json(name = "current")
    val current: MWeatherCurrent? = null,
    @Json(name = "location")
    val location: MWeatherLocation? = null
)
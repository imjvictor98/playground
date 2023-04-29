package br.com.cvj.playground.domain.model.forecast


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Forecast(
    @Json(name = "forecastday")
    val forecastDay: List<ForecastDay>? = listOf()
)
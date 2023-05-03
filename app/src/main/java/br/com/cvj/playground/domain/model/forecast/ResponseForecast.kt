package br.com.cvj.playground.domain.model.forecast


import br.com.cvj.playground.domain.model.weather.WeatherCurrent
import br.com.cvj.playground.domain.model.weather.WeatherLocation
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ResponseForecast(
    @Json(name = "alerts")
    val alerts: Alerts? = null,
    @Json(name = "current")
    val current: WeatherCurrent? = null,
    @Json(name = "forecast")
    val forecast: Forecast? = null,
    @Json(name = "location")
    val location: WeatherLocation? = null
)
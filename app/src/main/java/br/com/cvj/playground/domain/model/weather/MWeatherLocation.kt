package br.com.cvj.playground.domain.model.weather


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MWeatherLocation(
    @Json(name = "country")
    val country: String? = null,
    @Json(name = "lat")
    val lat: Double? = null,
    @Json(name = "localtime")
    val localtime: String? = null,
    @Json(name = "localtime_epoch")
    val localtimeEpoch: Int? = null,
    @Json(name = "lon")
    val lon: Double? = null,
    @Json(name = "name")
    val name: String? = null,
    @Json(name = "region")
    val region: String? = null,
    @Json(name = "tz_id")
    val tzId: String? = null
)
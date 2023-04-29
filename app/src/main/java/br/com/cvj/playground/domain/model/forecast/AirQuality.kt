package br.com.cvj.playground.domain.model.forecast


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AirQuality(
    @Json(name = "co")
    val co: Double? = null,
    @Json(name = "gb-defra-index")
    val gbDefraIndex: Int? = null,
    @Json(name = "no2")
    val no2: Double? = null,
    @Json(name = "o3")
    val o3: Double? = null,
    @Json(name = "pm10")
    val pm10: Double? = null,
    @Json(name = "pm2_5")
    val pm25: Double? = null,
    @Json(name = "so2")
    val so2: Double? = null,
    @Json(name = "us-epa-index")
    val usEpaIndex: Int? = null
)
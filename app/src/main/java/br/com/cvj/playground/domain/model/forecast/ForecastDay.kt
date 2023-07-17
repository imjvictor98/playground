package br.com.cvj.playground.domain.model.forecast


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.io.Serializable
import java.util.Date

@JsonClass(generateAdapter = true)
data class ForecastDay(
    @Json(name = "astro")
    val astro: Astro? = null,
    @Json(name = "date")
    val date: String? = "",
    @Json(name = "date_epoch")
    val dateEpoch: Int? = 0,
    @Json(name = "day")
    val day: Day? = null,
    @Json(name = "hour")
    val hour: List<Hour>? = null
): Serializable
package br.com.cvj.playground.domain.model.forecast


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.io.Serializable

@JsonClass(generateAdapter = true)
data class Alerts(
    @Json(name = "alert")
    val alertList: List<Alert>? = null
): Serializable
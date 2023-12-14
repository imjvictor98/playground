package br.com.cvj.playground.domain.model.forecast

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.io.Serializable

@JsonClass(generateAdapter = true)
data class Alert(
    @Json(name = "areas")
    val areas: String? = null,
    @Json(name = "category")
    val category: String? = null,
    @Json(name = "certainty")
    val certainty: String? = null,
    @Json(name = "desc")
    val desc: String? = null,
    @Json(name = "effective")
    val effective: String? = null,
    @Json(name = "event")
    val event: String? = null,
    @Json(name = "expires")
    val expires: String? = null,
    @Json(name = "headline")
    val headline: String? = null,
    @Json(name = "instruction")
    val instruction: String? = null,
    @Json(name = "msgtype")
    val msgtype: String? = null,
    @Json(name = "note")
    val note: String? = null,
    @Json(name = "severity")
    val severity: String? = null,
    @Json(name = "urgency")
    val urgency: String? = null
): Serializable
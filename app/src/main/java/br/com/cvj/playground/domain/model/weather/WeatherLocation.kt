package br.com.cvj.playground.domain.model.weather


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.io.Serializable

@JsonClass(generateAdapter = true)
data class WeatherLocation(
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
) : Serializable {
    fun getRegionFormatted(): String {
        return StringBuilder()
            .append(name)
            .append(
                if (region?.isNotEmpty() == true) {
                    "," + System.lineSeparator() + region.split(",")[0]
                } else {
                    ""
                }
            ).toString()
    }
}
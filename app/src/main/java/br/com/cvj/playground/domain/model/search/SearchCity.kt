package br.com.cvj.playground.domain.model.search


import android.location.Location
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.io.Serializable

@JsonClass(generateAdapter = true)
data class SearchCityItem(
    @Json(name = "country")
    val country: String = "",
    @Json(name = "lat")
    val lat: String = "",
    @Json(name = "lng")
    val lng: String = "",
    @Json(name = "name")
    val name: String = ""
): Serializable {
    override fun toString(): String {
        return "${name}, $country"
    }
}

fun SearchCityItem.toLocation(): Location {
    return Location("").also {
        it.latitude = lat.toDouble()
        it.longitude = lng.toDouble()
    }
}
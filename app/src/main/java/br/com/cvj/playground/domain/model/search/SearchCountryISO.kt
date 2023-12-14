package br.com.cvj.playground.domain.model.search


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.io.Serializable

@JsonClass(generateAdapter = true)
data class SearchSearchCountryISOItem(
    @Json(name = "iso")
    val iso: String = "",
    @Json(name = "name")
    val name: String = ""
): Serializable
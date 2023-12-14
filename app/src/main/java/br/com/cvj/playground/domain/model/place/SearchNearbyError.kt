package br.com.cvj.playground.domain.model.place


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SearchNearbyError(
    @Json(name = "error")
    val error: Error
) {
    @JsonClass(generateAdapter = true)
    data class Error(
        @Json(name = "code")
        val code: Int,
        @Json(name = "details")
        val details: List<Detail>,
        @Json(name = "message")
        val message: String,
        @Json(name = "status")
        val status: String
    ) {
        @JsonClass(generateAdapter = true)
        data class Detail(
            @Json(name = "domain")
            val domain: String,
            @Json(name = "metadata")
            val metadata: Metadata,
            @Json(name = "reason")
            val reason: String,
            @Json(name = "@type")
            val type: String
        ) {
            @JsonClass(generateAdapter = true)
            data class Metadata(
                @Json(name = "service")
                val service: String
            )
        }
    }
}
package br.com.cvj.playground.domain.model.place


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SearchNearbyResponse(
    @Json(name = "places")
    val places: List<Place>
) {
    @JsonClass(generateAdapter = true)
    data class Place(
        @Json(name = "accessibilityOptions")
        val accessibilityOptions: AccessibilityOptions?,
        @Json(name = "addressComponents")
        val addressComponents: List<AddressComponent?>?,
        @Json(name = "adrFormatAddress")
        val adrFormatAddress: String?,
        @Json(name = "allowsDogs")
        val allowsDogs: Boolean?,
        @Json(name = "businessStatus")
        val businessStatus: String?,
        @Json(name = "curbsidePickup")
        val curbsidePickup: Boolean?,
        @Json(name = "currentOpeningHours")
        val currentOpeningHours: CurrentOpeningHours?,
        @Json(name = "currentSecondaryOpeningHours")
        val currentSecondaryOpeningHours: List<CurrentSecondaryOpeningHour?>?,
        @Json(name = "delivery")
        val delivery: Boolean?,
        @Json(name = "dineIn")
        val dineIn: Boolean?,
        @Json(name = "displayName")
        val displayName: DisplayName?,
        @Json(name = "editorialSummary")
        val editorialSummary: EditorialSummary?,
        @Json(name = "formattedAddress")
        val formattedAddress: String?,
        @Json(name = "googleMapsUri")
        val googleMapsUri: String?,
        @Json(name = "iconBackgroundColor")
        val iconBackgroundColor: String?,
        @Json(name = "iconMaskBaseUri")
        val iconMaskBaseUri: String?,
        @Json(name = "id")
        val id: String?,
        @Json(name = "internationalPhoneNumber")
        val internationalPhoneNumber: String?,
        @Json(name = "location")
        val location: Location,
        @Json(name = "name")
        val name: String?,
        @Json(name = "nationalPhoneNumber")
        val nationalPhoneNumber: String?,
        @Json(name = "photos")
        val photos: List<Photo?>?,
        @Json(name = "plusCode")
        val plusCode: PlusCode?,
        @Json(name = "priceLevel")
        val priceLevel: String?,
        @Json(name = "primaryType")
        val primaryType: String?,
        @Json(name = "primaryTypeDisplayName")
        val primaryTypeDisplayName: PrimaryTypeDisplayName?,
        @Json(name = "rating")
        val rating: Double?,
        @Json(name = "regularOpeningHours")
        val regularOpeningHours: RegularOpeningHours?,
        @Json(name = "regularSecondaryOpeningHours")
        val regularSecondaryOpeningHours: List<RegularSecondaryOpeningHour?>?,
        @Json(name = "shortFormattedAddress")
        val shortFormattedAddress: String?,
        @Json(name = "userRatingCount")
        val userRatingCount: Int?,
        @Json(name = "websiteUri")
        val websiteUri: String?
    ) {
        fun toFillUnavailablePhotos() = photos
            ?: arrayListOf(
                Photo(
                    authorAttributions = null,
                    name = "Dump Photo",
                    heightPx = 1024,
                    widthPx = 1024
                )
            )

        fun toPriceLevelMoneyDTO() = SearchNearbyPriceLevel(priceLevel)

        enum class PhotoAvailability {
            AVAILABLE,
            UNAVAILABLE,
        }

        @JsonClass(generateAdapter = true)
        data class AccessibilityOptions(
            @Json(name = "wheelchairAccessibleEntrance")
            val wheelchairAccessibleEntrance: Boolean?,
            @Json(name = "wheelchairAccessibleParking")
            val wheelchairAccessibleParking: Boolean?,
            @Json(name = "wheelchairAccessibleRestroom")
            val wheelchairAccessibleRestroom: Boolean?,
            @Json(name = "wheelchairAccessibleSeating")
            val wheelchairAccessibleSeating: Boolean?
        )

        @JsonClass(generateAdapter = true)
        data class AddressComponent(
            @Json(name = "languageCode")
            val languageCode: String?,
            @Json(name = "longText")
            val longText: String?,
            @Json(name = "shortText")
            val shortText: String?,
            @Json(name = "types")
            val types: List<String?>?
        )

        @JsonClass(generateAdapter = true)
        data class CurrentOpeningHours(
            @Json(name = "openNow")
            val openNow: Boolean?,
            @Json(name = "periods")
            val periods: List<Period?>?,
            @Json(name = "weekdayDescriptions")
            val weekdayDescriptions: List<String?>?
        ) {
            @JsonClass(generateAdapter = true)
            data class Period(
                @Json(name = "close")
                val close: Close?,
                @Json(name = "open")
                val `open`: Open?
            ) {
                @JsonClass(generateAdapter = true)
                data class Close(
                    @Json(name = "date")
                    val date: Date?,
                    @Json(name = "day")
                    val day: Int?,
                    @Json(name = "hour")
                    val hour: Int?,
                    @Json(name = "minute")
                    val minute: Int?,
                    @Json(name = "truncated")
                    val truncated: Boolean?
                ) {
                    @JsonClass(generateAdapter = true)
                    data class Date(
                        @Json(name = "day")
                        val day: Int?,
                        @Json(name = "month")
                        val month: Int?,
                        @Json(name = "year")
                        val year: Int?
                    )
                }

                @JsonClass(generateAdapter = true)
                data class Open(
                    @Json(name = "date")
                    val date: Date?,
                    @Json(name = "day")
                    val day: Int?,
                    @Json(name = "hour")
                    val hour: Int?,
                    @Json(name = "minute")
                    val minute: Int?,
                    @Json(name = "truncated")
                    val truncated: Boolean?
                ) {
                    @JsonClass(generateAdapter = true)
                    data class Date(
                        @Json(name = "day")
                        val day: Int?,
                        @Json(name = "month")
                        val month: Int?,
                        @Json(name = "year")
                        val year: Int?
                    )
                }
            }
        }

        @JsonClass(generateAdapter = true)
        data class CurrentSecondaryOpeningHour(
            @Json(name = "openNow")
            val openNow: Boolean?,
            @Json(name = "periods")
            val periods: List<Period?>?,
            @Json(name = "secondaryHoursType")
            val secondaryHoursType: String?,
            @Json(name = "weekdayDescriptions")
            val weekdayDescriptions: List<String?>?
        ) {
            @JsonClass(generateAdapter = true)
            data class Period(
                @Json(name = "close")
                val close: Close?,
                @Json(name = "open")
                val `open`: Open?
            ) {
                @JsonClass(generateAdapter = true)
                data class Close(
                    @Json(name = "date")
                    val date: Date?,
                    @Json(name = "day")
                    val day: Int?,
                    @Json(name = "hour")
                    val hour: Int?,
                    @Json(name = "minute")
                    val minute: Int?,
                    @Json(name = "truncated")
                    val truncated: Boolean?
                ) {
                    @JsonClass(generateAdapter = true)
                    data class Date(
                        @Json(name = "day")
                        val day: Int?,
                        @Json(name = "month")
                        val month: Int?,
                        @Json(name = "year")
                        val year: Int?
                    )
                }

                @JsonClass(generateAdapter = true)
                data class Open(
                    @Json(name = "date")
                    val date: Date?,
                    @Json(name = "day")
                    val day: Int?,
                    @Json(name = "hour")
                    val hour: Int?,
                    @Json(name = "minute")
                    val minute: Int?,
                    @Json(name = "truncated")
                    val truncated: Boolean?
                ) {
                    @JsonClass(generateAdapter = true)
                    data class Date(
                        @Json(name = "day")
                        val day: Int?,
                        @Json(name = "month")
                        val month: Int?,
                        @Json(name = "year")
                        val year: Int?
                    )
                }
            }
        }

        @JsonClass(generateAdapter = true)
        data class DisplayName(
            @Json(name = "languageCode")
            val languageCode: String?,
            @Json(name = "text")
            val text: String?
        )

        @JsonClass(generateAdapter = true)
        data class EditorialSummary(
            @Json(name = "languageCode")
            val languageCode: String?,
            @Json(name = "text")
            val text: String?
        )

        @JsonClass(generateAdapter = true)
        data class Location(
            @Json(name = "latitude")
            val latitude: Double,
            @Json(name = "longitude")
            val longitude: Double
        )

        @JsonClass(generateAdapter = true)
        data class Photo(
            @Json(name = "authorAttributions")
            val authorAttributions: List<AuthorAttribution?>?,
            @Json(name = "heightPx")
            val heightPx: Int?,
            @Json(name = "name")
            val name: String?,
            @Json(name = "widthPx")
            val widthPx: Int?
        ) {
            @JsonClass(generateAdapter = true)
            data class AuthorAttribution(
                @Json(name = "displayName")
                val displayName: String?,
                @Json(name = "photoUri")
                val photoUri: String?,
                @Json(name = "uri")
                val uri: String?
            )
        }

        @JsonClass(generateAdapter = true)
        data class PlusCode(
            @Json(name = "compoundCode")
            val compoundCode: String?,
            @Json(name = "globalCode")
            val globalCode: String?
        )

        @JsonClass(generateAdapter = true)
        data class PrimaryTypeDisplayName(
            @Json(name = "languageCode")
            val languageCode: String?,
            @Json(name = "text")
            val text: String?
        )

        @JsonClass(generateAdapter = true)
        data class RegularOpeningHours(
            @Json(name = "openNow")
            val openNow: Boolean?,
            @Json(name = "periods")
            val periods: List<Period?>?,
            @Json(name = "weekdayDescriptions")
            val weekdayDescriptions: List<String?>?
        ) {
            @JsonClass(generateAdapter = true)
            data class Period(
                @Json(name = "close")
                val close: Close?,
                @Json(name = "open")
                val `open`: Open?
            ) {
                @JsonClass(generateAdapter = true)
                data class Close(
                    @Json(name = "day")
                    val day: Int?,
                    @Json(name = "hour")
                    val hour: Int?,
                    @Json(name = "minute")
                    val minute: Int?
                )

                @JsonClass(generateAdapter = true)
                data class Open(
                    @Json(name = "day")
                    val day: Int?,
                    @Json(name = "hour")
                    val hour: Int?,
                    @Json(name = "minute")
                    val minute: Int?
                )
            }
        }

        @JsonClass(generateAdapter = true)
        data class RegularSecondaryOpeningHour(
            @Json(name = "openNow")
            val openNow: Boolean?,
            @Json(name = "periods")
            val periods: List<Period?>?,
            @Json(name = "secondaryHoursType")
            val secondaryHoursType: String?,
            @Json(name = "weekdayDescriptions")
            val weekdayDescriptions: List<String?>?
        ) {
            @JsonClass(generateAdapter = true)
            data class Period(
                @Json(name = "close")
                val close: Close?,
                @Json(name = "open")
                val `open`: Open?
            ) {
                @JsonClass(generateAdapter = true)
                data class Close(
                    @Json(name = "day")
                    val day: Int?,
                    @Json(name = "hour")
                    val hour: Int?,
                    @Json(name = "minute")
                    val minute: Int?
                )

                @JsonClass(generateAdapter = true)
                data class Open(
                    @Json(name = "day")
                    val day: Int?,
                    @Json(name = "hour")
                    val hour: Int?,
                    @Json(name = "minute")
                    val minute: Int?
                )
            }
        }
    }
}

data class SearchNearbyPriceLevel(
    val priceLevel: String?
) {
    fun toMoneyString() = when (priceLevel) {
        "PRICE_LEVEL_VERY_EXPENSIVE" -> "$$$$"
        "PRICE_LEVEL_EXPENSIVE" -> "$$$"
        "PRICE_LEVEL_MODERATE" -> "$$"
        "PRICE_LEVEL_INEXPENSIVE" -> "$"
        else -> ""
    }
}
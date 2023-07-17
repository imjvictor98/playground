package br.com.cvj.playground.domain.model.weather


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.io.Serializable

@JsonClass(generateAdapter = true)
data class WeatherCondition(
    @Json(name = "code")
    val code: Int? = null,
    @Json(name = "icon")
    val icon: String? = null,
    @Json(name = "text")
    val text: String? = null
): Serializable

enum class WeatherConditionEnum(text: String?): Serializable {
    SUNNY("Sunny"),
    PARTLY_CLOUDY("Partly cloudy"),
    CLOUDY("Cloudy"),
    OVERCAST("Overcast"),
    MIST("Mist"),
    PATCHY_RAIN_POSSIBLE("Patchy rain possible"),
    PATCHY_SNOW_POSSIBLE("Patchy snow possible"),
    PATCHY_SLEET_POSSIBLE("Patchy sleet possible"),
    PATCHY_FREEZING_DRIZZLE_POSSIBLE("Patchy freezing drizzle possible"),
    THUNDERY_OUTBREAKS_POSSIBLE("Thundery outbreaks possible"),
    BLOWING_SNOW("Blowing snow"),
    BLIZZARD("Blizzard"),
    FOG("Fog"),
    FREEZING_FOG("Freezing fog"),
    PATCHY_LIGHT_DRIZZLE("Patchy light drizzle"),
    LIGHT_DRIZZLE("Light drizzle"),
    FREEZING_DRIZZLE("Freezing drizzle"),
    HEAVY_FREEZING_DRIZZLE("Heavy freezing drizzle"),
    PATCHY_LIGHT_RAIN("Patchy light rain"),
    LIGHT_RAIN("Light rain"),
    MODERATE_RAIN_AT_TIMES("Moderate rain at times"),
    MODERATE_RAIN("Moderate rain"),
    HEAVY_RAIN_AT_TIMES("Heavy rain at times"),
    HEAVY_RAIN("Heavy rain"),
    LIGHT_FREEZING_RAIN("Light freezing rain"),
    MODERATE_OR_HEAVY_FREEZING_RAIN("Moderate or heavy freezing rain"),
    LIGHT_SLEET("Light sleet"),
    MODERATE_OR_HEAVY_LIGHT_SLEET("Moderate or heavy sleet"),
    PATCHY_LIGHT_SNOW("Patchy light snow"),
    LIGHT_SNOW("Light snow"),
    PATCHY_MODERATE_SNOW("Patchy moderate snow"),
    MODERATE_SNOW("Moderate snow"),
    PATCHY_HEAVY_SNOW("Patchy heavy snow"),
    HEAVY_SNOW("Heavy snow"),
    ICE_PELLETS("Ice pellets"),
    LIGHT_RAIN_SHOWER("Light rain shower"),
    MODERATE_OR_HEAVY_RAIN_SHOWER("Moderate or heavy rain shower"),
    TORRENTIAL_RAIN_SHOWER("Torrential rain shower"),
    LIGHT_SLEET_SHOWERS("Light sleet showers"),
    MODERATE_OR_HEAVY_SLEET_SHOWERS("Moderate or heavy sleet showers"),
    LIGHT_SNOW_SHOWERS("Light snow showers"),
    MODERATE_OR_HEAVY_SNOW_SHOWERS("Moderate or heavy snow showers"),
    LIGHT_SHOWERS_OF_ICE_PELLETS("Light showers of ice pellets"),
    MODERATE_OR_HEAVY_SHOWER_OF_ICE_PELLETS("Moderate or heavy showers of ice pellets"),
    PATCHY_LIGHT_RAIN_WITH_THUNDER("Patchy light rain with thunder"),
    MODERATE_OR_HEAVY_RAIN_WITH_THUNDER("Moderate or heavy rain with thunder"),
    PATCHY_LIGHT_SNOW_WITH_THUNDER("Patchy light snow with thunder"),
    MODERATE_OR_HEAVY_SNOW_WITH_THUNDER("Moderate or heavy snow with thunder")
}
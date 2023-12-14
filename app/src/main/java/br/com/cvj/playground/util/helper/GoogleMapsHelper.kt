package br.com.cvj.playground.util.helper

import android.net.Uri
import br.com.cvj.playground.BuildConfig

object GoogleMapsHelper {
    private const val photoBaseUrl = "https://maps.googleapis.com/maps/api/place/photo"

    fun getPlacePhotoUrl(
        maxWidth: Int = 400,
        maxHeight: Int = 400,
        photoReference: String
    ): String {
        val url = Uri.parse(photoBaseUrl)
            .buildUpon()
            .appendQueryParameter("maxwidth", maxWidth.toString())
            .appendQueryParameter("maxheight", maxHeight.toString())
            .appendQueryParameter("photo_reference", photoReference.substringAfter("photos/"))
            .appendQueryParameter("key", BuildConfig.PLACES_API_KEY)
            .build()

        return url.toString()
    }

}
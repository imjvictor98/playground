package br.com.cvj.playground.util.helper

import android.content.Context
import android.location.Location
import androidx.annotation.FloatRange
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

object LocationHelper {
    private const val TAG = "LOCATION_HELPER"

    private fun getFusedLocationProvider(context: Context): FusedLocationProviderClient {
        return LocationServices.getFusedLocationProviderClient(context)
    }

    fun getLastLocation(context: Context, onLocationAvailability: LocationAvailabilityListener) {
        try {
            val taskToGetLocation = getFusedLocationProvider(context).lastLocation

            taskToGetLocation.addOnSuccessListener {
                if (it != null) {
                    onLocationAvailability.onSuccess(it)
                } else {
                    onLocationAvailability.onFailure()
                }
            }

            taskToGetLocation.addOnFailureListener {
                onLocationAvailability.onError(it)
            }

            taskToGetLocation.addOnCanceledListener {
                onLocationAvailability.onFailure()
            }
        } catch (e: SecurityException) {
            onLocationAvailability.onError(e)
        }
    }

    fun getLocationByLatLong(
        @FloatRange(from = -90.0, to = 90.0) latitude: Double,
        @FloatRange(from = -180.0, to = 180.0) longitude: Double,
    ): Location {
        Location("").apply {
            setLatitude(latitude)
            setLongitude(longitude)
            return this
        }
    }

    interface LocationAvailabilityListener {
        fun onSuccess(location: Location)
        fun onFailure()

        fun onError(exception: Exception)
    }
}
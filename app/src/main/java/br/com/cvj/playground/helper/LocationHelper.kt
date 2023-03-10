package br.com.cvj.playground.helper

import android.content.Context
import android.location.Location
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.tasks.Task

object LocationHelper {
    private const val TAG = "LOCATION_HELPER"

    private fun getFusedLocationProvider(context: Context): FusedLocationProviderClient {
        return LocationServices.getFusedLocationProviderClient(context)
    }

    fun getLastLocation(context: Context): Task<Location>? {
        if (GoogleApiAvailability.getInstance()
                .isGooglePlayServicesAvailable(context) != ConnectionResult.SUCCESS
        ) {
            return null
        }
        return if (PermissionHelper.hasLocationPermissions(context)) {
            try {
                getFusedLocationProvider(context).lastLocation
            } catch (e: SecurityException) {
                null
            }
        } else {
            null
        }
    }
}
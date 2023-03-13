package br.com.cvj.playground.util.helper

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager

object PermissionHelper {
    const val REQUEST_CODE_LOCATION_PERMISSIONS = 2002

    fun requestLocationPermission(activity: Activity) {
        activity.requestPermissions(
            arrayOf(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION,
            ),
            REQUEST_CODE_LOCATION_PERMISSIONS
        )
    }

    fun hasLocationPermissions(context: Context): Boolean {
        return context.checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED ||
                context.checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
    }


    fun checkLocationPermissionsAllowed(permissions: Array<out String>, grantResults: IntArray): Boolean {
        if (grantResults.isEmpty() || grantResults.all{ it == PackageManager.PERMISSION_DENIED }) return false

        val hasPermissionsAllowed = grantResults.mapIndexed { i, result ->
            (permissions[i] == Manifest.permission.ACCESS_FINE_LOCATION || permissions[i] == Manifest.permission.ACCESS_COARSE_LOCATION) && result == PackageManager.PERMISSION_GRANTED
        }

        return hasPermissionsAllowed.isNotEmpty()
    }

}
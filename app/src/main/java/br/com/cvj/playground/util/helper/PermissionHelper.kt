package br.com.cvj.playground.util.helper

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable

object PermissionHelper {
    const val REQUEST_CODE_LOCATION_PERMISSIONS = 2002

    val locationPermissions = arrayOf(
        Manifest.permission.ACCESS_COARSE_LOCATION,
        Manifest.permission.ACCESS_FINE_LOCATION,
    )

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

    fun isLocationPermissions(permissions: Map<String, @JvmSuppressWildcards Boolean>): Boolean {
        return permissions.contains(Manifest.permission.ACCESS_FINE_LOCATION) ||
                permissions.contains(Manifest.permission.ACCESS_COARSE_LOCATION)
    }

    fun checkLocationPermissionsAllowed(
        permissions: Array<out String>,
        grantResults: IntArray
    ): Boolean {
        if (grantResults.isEmpty() || grantResults.all { it == PackageManager.PERMISSION_DENIED }) return false

        val hasPermissionsAllowed = grantResults.mapIndexed { i, result ->
            (permissions[i] == Manifest.permission.ACCESS_FINE_LOCATION || permissions[i] == Manifest.permission.ACCESS_COARSE_LOCATION) && result == PackageManager.PERMISSION_GRANTED
        }

        return hasPermissionsAllowed.isNotEmpty()
    }
}
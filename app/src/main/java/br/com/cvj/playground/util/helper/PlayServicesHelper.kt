package br.com.cvj.playground.util.helper

import android.content.Context
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability

object PlayServicesHelper {
    fun isAvailable(context: Context) = GoogleApiAvailability.getInstance()
        .isGooglePlayServicesAvailable(context) != ConnectionResult.SUCCESS
}
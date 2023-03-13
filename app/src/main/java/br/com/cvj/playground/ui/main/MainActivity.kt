package br.com.cvj.playground.ui.main

import android.content.Context
import android.content.Intent
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import br.com.cvj.playground.R
import br.com.cvj.playground.util.helper.LocationHelper
import br.com.cvj.playground.util.helper.PermissionHelper
import br.com.cvj.playground.data.network.ApiFactory
import com.google.android.gms.location.LocationServices
import com.haroldadmin.cnradapter.NetworkResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity: AppCompatActivity() {
    companion object {
        fun start(context: Context) {
            Intent(context, MainActivity::class.java).apply {
                context.startActivity(this)
            }
        }
    }

    private var mCurrentLocation: Location? = null
    private val mFusedLocationClient by lazy {
        LocationServices.getFusedLocationProviderClient(this)
    }


    private val mTextView: TextView? by lazy {
        findViewById(R.id.activity_main_text)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (!PermissionHelper.hasLocationPermissions(this)) {
            PermissionHelper.requestLocationPermission(this)
        } else  {
            LocationHelper.getLastLocation(this)?.addOnSuccessListener {
                mCurrentLocation = it
                requestWeather()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    private fun requestWeather() {
        if (mCurrentLocation?.latitude != null && mCurrentLocation?.longitude != null) {
            CoroutineScope(Dispatchers.Main).launch {
                val response = ApiFactory(this@MainActivity).weatherServices.getCurrentWeatherAt(
                    "${mCurrentLocation!!.latitude},${mCurrentLocation!!.longitude}"
                )

                when (response) {
                    is NetworkResponse.Success -> {
                        mTextView?.text = "${response?.body?.location?.name}, " +
                                "${response.body.location?.region}\n" +
                                "${response.body.location?.country}"
                        Log.d("Current Location", "lat: ${mCurrentLocation?.latitude} - lon: ${mCurrentLocation?.longitude}")
                    }
                    else -> {
                        Log.d("Current Location error", "lat: ${mCurrentLocation?.latitude} - lon: ${mCurrentLocation?.longitude}")
                    }
                }

            }
        }
    }
}
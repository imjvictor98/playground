package br.com.cvj.playground

import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import br.com.cvj.playground.helper.LocationHelper
import br.com.cvj.playground.helper.PermissionHelper
import br.com.cvj.playground.network.ApiFactory
import com.google.android.gms.location.LocationServices
import com.haroldadmin.cnradapter.NetworkResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class MainActivity: BaseActivity() {
    private var mCurrentLocation: Location? = null
    private val mFusedLocationClient by lazy {
        LocationServices.getFusedLocationProviderClient(this)
    }
    private var mCurrentWeatherCall: Job? = null

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
    }

    override fun onDestroy() {
        mCurrentWeatherCall?.cancel()
        super.onDestroy()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            PermissionHelper.REQUEST_CODE_LOCATION_PERMISSIONS -> {
                if (grantResults.isNotEmpty()) {
                    if (PermissionHelper.hasLocationPermissions(this)) {
                        mFusedLocationClient.lastLocation.addOnSuccessListener { location ->
                            mCurrentLocation = location
                        }
                    } else if (grantResults.size <= 1) {
                        mFusedLocationClient.lastLocation.addOnSuccessListener { location ->
                            mCurrentLocation = location
                        }
                    }
                } else {
                    when (PackageManager.PERMISSION_DENIED) {
                        checkSelfPermission(permissions[0]) -> {
                            shouldShowRequestPermissionRationale(permissions[0])
                        }
                        checkSelfPermission(permissions[1]) -> {
                            shouldShowRequestPermissionRationale(permissions[1])
                        }
                        else -> {
                            return
                        }
                    }
                    //Essa funcionalidade não vai funcionar para você pois precisamos das permissões de localização e blá blá blá
                }
                return
            }
            else -> {
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }
}
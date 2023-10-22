package br.com.cvj.playground.ui.main

import android.location.Location
import br.com.cvj.playground.data.repository.weather.IWeatherServices
import br.com.cvj.playground.util.extension.getLatLongAsString
import com.haroldadmin.cnradapter.NetworkResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class MainPresenter(
    override val mView: IMainContract.View,
    private val mWeatherServices: IWeatherServices
) : IMainContract.Presenter {
    private var mWeatherCall: Job? = null

    override fun requestForecast(location: Location?) {
        if (location != null) {
            mView.displayLoading()
            mWeatherCall = CoroutineScope(Dispatchers.Main).launch {
                val latLong = location.getLatLongAsString()
                when (val response = mWeatherServices.getForecastToday(latLong)) {
                    is NetworkResponse.Success -> {
                        response.body.location?.let {
                            mView.displayCity(it.getRegionFormatted())
                        } ?: run {
                            mView.hideCity()
                        }

                        val forecasts = response.body.getForecastListDTO()
                        if (forecasts.isNotEmpty()) {
                            mView.setupPages(forecasts)
                        }

                        mView.revealSearch()
                    }
                    else -> {
                        // uma tela de erro talvez, com um retry?
                    }
                }

            }
            mWeatherCall?.invokeOnCompletion {
                mView.hideLoading()
            }
        } else {
            //do nothing
        }
    }

    override fun beforeDestroyPresenter() {
        mWeatherCall?.cancel()
    }
}
package br.com.cvj.playground.ui.main

import android.location.Location
import br.com.cvj.playground.data.network.IWeatherServices
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

    override fun requestWeather(location: Location) {
        mWeatherCall = CoroutineScope(Dispatchers.Main).launch {
            val latLong = "${location.latitude},${location.longitude}"
            when (val response = mWeatherServices.getCurrentWeatherAt(latLong)) {
                is NetworkResponse.Success -> {
                    val weather = response.body
                    val regionCountry = weather.location?.name
                        ?.plus(", ")
                        ?.plus(weather.location.region)

                    val temperatureInCelsius = weather.current?.tempC?.toInt()
                    val weatherImage = weather.current?.condition?.icon?.replace("//", "https://")
                    val conditionWeather = weather.current?.condition?.text

                    mView.displayCurrentLocation(regionCountry.toString())
                    mView.displayWeatherImage(weatherImage.toString())
                    mView.displayTemperature(temperatureInCelsius.toString())
                    mView.displayWeatherCondition(conditionWeather.toString())
                }
                else -> {

                }
            }

        }
    }

    override fun beforeDestroyPresenter() {
        mWeatherCall?.cancel()
    }
}
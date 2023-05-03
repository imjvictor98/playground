package br.com.cvj.playground.ui.main

import android.location.Location
import br.com.cvj.playground.data.network.IWeatherServices
import br.com.cvj.playground.domain.model.weather.WeatherRegion
import br.com.cvj.playground.util.extension.getLatLongAsString
import br.com.cvj.playground.util.extension.isEqualsToCurrent
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
    private var mForecastCall: Job? = null

    override fun requestWeather(location: Location?) {
        if (location != null) {
            mWeatherCall = CoroutineScope(Dispatchers.Main).launch {
                val latLong = location.getLatLongAsString()
                when (val response = mWeatherServices.getCurrentWeatherAt(latLong)) {
                    is NetworkResponse.Success -> {
                        val weather = response.body
                        val regionCountry = weather.location?.name
                            ?.plus(System.lineSeparator())
                            ?.plus(WeatherRegion.getAcronymByRegion(weather.location.region) ?: weather.location.region)

                        val temperatureInCelsius = weather.current?.tempC?.toInt()
                        val weatherImage = ("https:" + weather.current?.condition?.icon).replace("64", "128")
                        val conditionWeather = weather.current?.condition?.text

                        mView.displayCurrentLocation(regionCountry?.replace("\n", ", ").toString())
                        mView.displayTemperature(temperatureInCelsius.toString())
                        mView.displayWeatherCondition(conditionWeather.toString())
                        mView.displayWeatherImage(weatherImage)
                    }
                    else -> {
                        // uma tela de erro talvez, com um retry?
                    }
                }

            }
        } else {
            //do nothing
        }

    }

    override fun requestForecast(location: Location?) {
        if (location != null) {
            mWeatherCall = CoroutineScope(Dispatchers.Main).launch {
                val latLong = location.getLatLongAsString()
                when (val response = mWeatherServices.getForecastToday(latLong)) {
                    is NetworkResponse.Success -> {
                        response.body.forecast?.forecastDay?.firstOrNull()?.hour?.let {
                            mView.setForecastList(it)
                            val hour = it.find { hour ->
                                hour.time?.isEqualsToCurrent("HH") == true
                            }
                            val position = it.indexOf(hour ?: 0)
                            mView.scrollToCurrentForecast(position)
                        } ?: run {
                            // não fazemos nada até o momento
                        }
                    }
                    else -> {
                        // uma tela de erro talvez, com um retry?
                    }
                }

            }
        } else {
            //do nothing
        }
    }

    override fun beforeDestroyPresenter() {
        mWeatherCall?.cancel()
        mForecastCall?.cancel()
    }
}
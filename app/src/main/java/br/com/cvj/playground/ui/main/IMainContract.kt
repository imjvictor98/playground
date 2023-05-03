package br.com.cvj.playground.ui.main

import android.location.Location
import br.com.cvj.playground.domain.model.forecast.Hour
import br.com.cvj.playground.domain.model.forecast.ResponseForecast
import br.com.cvj.playground.ui.IBaseContract

interface IMainContract {
    interface View: IBaseContract.BaseView {
        fun displayWeatherImage(url: String)

        fun displayWeatherText(weather: String)

        fun displayTemperature(temperature: String)

        fun displayCurrentLocation(location: String)

        fun displayWeatherCondition(condition: String)

        fun setForecastList(list: List<Hour>)

        fun scrollToCurrentForecast(position: Int)
    }

    interface Presenter: IBaseContract.BasePresenter<View>{
        fun requestWeather(location: Location? = null)

        fun requestForecast(location: Location? = null)
    }
}
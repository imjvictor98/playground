package br.com.cvj.playground.ui.weather.resume

import br.com.cvj.playground.domain.model.forecast.DayByType
import br.com.cvj.playground.domain.model.forecast.ForecastDTO
import br.com.cvj.playground.util.extension.applyScheme
import br.com.cvj.playground.util.extension.isEqualsToCurrent

class WeatherResumePresenter(override val mView: IWeatherResumeContract.View) : IWeatherResumeContract.Presenter {
    override fun start(forecast: ForecastDTO) {
        forecast.forecastDay?.day?.avgtempC?.let {
            mView.displayTemperature(it.toInt().toString() + "°")
        }

        forecast.current?.condition?.let {
            mView.displayCondition(it.icon?.applyScheme()?.replace("64", "128").toString())
        }

        try {
            val daysList = listOf(
                forecast.forecastDay!!.day!!.getDayByTypeDTO(DayByType.WIND),
                forecast.forecastDay.day!!.getDayByTypeDTO(DayByType.HUMIDITY),
                forecast.forecastDay.day.getDayByTypeDTO(DayByType.CHANCE_OF_RAIN)
            )

            mView.displayDaysProbabilityList(daysList)
        } catch (e: NullPointerException) {
            //do nothing at the moment
        }

        forecast.forecastDay?.hour?.let {
            mView.displayForecastList(it)
            val current = it.findLast { hour -> hour.time?.isEqualsToCurrent("HH") == true }
            mView.scrollToCurrentHour(it.indexOf(current))
        }
    }

    override fun beforeDestroyPresenter() {

    }
}
package br.com.cvj.playground.ui.weather.resume

import br.com.cvj.playground.domain.model.forecast.DayByTypeDTO
import br.com.cvj.playground.domain.model.forecast.ForecastDTO
import br.com.cvj.playground.domain.model.forecast.Hour
import br.com.cvj.playground.ui.base.IBaseContract

interface IWeatherResumeContract {
    interface View: IBaseContract.BaseView {
        fun displayTemperature(temperature: String)

        fun displayCondition(imageUrl: String)

        fun displayDaysProbabilityList(list: List<DayByTypeDTO>)

        fun displayForecastList(list: List<Hour>)

        fun scrollToCurrentHour(position: Int)
    }

    interface Presenter: IBaseContract.BasePresenter<View> {
        fun start(forecast: ForecastDTO)
    }
}
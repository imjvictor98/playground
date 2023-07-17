package br.com.cvj.playground.ui.main

import android.location.Location
import br.com.cvj.playground.domain.model.forecast.ForecastDTO
import br.com.cvj.playground.ui.base.IBaseContract

interface IMainContract {
    interface View: IBaseContract.BaseView {
        fun displayLoading()

        fun hideLoading()

        fun displayCity(name: String)

        fun hideCity()

        fun setupPages(forecasts: List<ForecastDTO>)

        fun revealSearch()
    }

    interface Presenter: IBaseContract.BasePresenter<View>{
        fun requestForecast(location: Location? = null)
    }
}
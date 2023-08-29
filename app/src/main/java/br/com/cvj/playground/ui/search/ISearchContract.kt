package br.com.cvj.playground.ui.search

import br.com.cvj.playground.domain.model.search.SearchCityItem
import br.com.cvj.playground.ui.base.IBaseContract

interface ISearchContract {
    interface View: IBaseContract.BaseView {
        fun showResults(cities: List<SearchCityItem>)

        fun clearResults()

        fun showSpinner()

        fun hideSpinner()
    }

    interface Presenter: IBaseContract.BasePresenter<View> {
        fun findCityByName(name: String)
        fun onCloseBtnPressed()

    }
}
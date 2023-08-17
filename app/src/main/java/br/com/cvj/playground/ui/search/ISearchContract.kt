package br.com.cvj.playground.ui.search

import br.com.cvj.playground.domain.model.search.SearchCityItem
import br.com.cvj.playground.ui.base.IBaseContract

interface ISearchContract {
    interface View: IBaseContract.BaseView {
        fun updateDropdown(cities: List<SearchCityItem>)

        fun showSpinner()

        fun hideSpinner()
    }

    interface Presenter: IBaseContract.BasePresenter<View> {
        fun findCityByName(name: String)
    }
}
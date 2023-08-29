package br.com.cvj.playground.ui.search

import br.com.cvj.playground.data.repository.search.SearchCountriesRepository

class SearchPresenter(
    override val mView: ISearchContract.View,
    private val repository: SearchCountriesRepository
) : ISearchContract.Presenter {
    override fun findCityByName(name: String) {
        val cities = repository.fetchCountriesBy(name)

        if (!cities.isNullOrEmpty()) {
            mView.showResults(cities)
            mView.showSpinner()
        } else {
            //What to do when is empty or null ?
        }
    }

    override fun onCloseBtnPressed() {
        mView.clearResults()
        mView.hideSpinner()
    }

    override fun beforeDestroyPresenter() {}
}
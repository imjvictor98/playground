package br.com.cvj.playground.data.repository.search


import br.com.cvj.playground.domain.model.search.SearchCityItem
import kotlinx.coroutines.CoroutineDispatcher

class SearchCountriesDataSource(
    private val searchCountriesRaw: SearchCountriesRaw,
    private val ioDispatcher: CoroutineDispatcher
) {

    fun fetchCountriesBy(): List<SearchCityItem>? {
        return searchCountriesRaw.getCities()
    }
}
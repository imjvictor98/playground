package br.com.cvj.playground.data.repository.search


import br.com.cvj.playground.domain.model.search.SearchCityItem
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

class SearchCountriesDataSource(
    private val searchCountriesRaw: SearchCountriesRaw,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) {

    fun fetchCountriesBy(): List<SearchCityItem>? {
        return searchCountriesRaw.getCities()
    }
}
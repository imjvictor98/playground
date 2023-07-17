package br.com.cvj.playground.data.repository.search

import br.com.cvj.playground.domain.model.BaseModel
import br.com.cvj.playground.domain.model.search.CountryResponse
import com.haroldadmin.cnradapter.NetworkResponse
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class SearchCountriesDataSource(
    private val countriesApi: SearchCountriesApi,
    private val ioDispatcher: CoroutineDispatcher
) {

    suspend fun fetchCountriesBy(query: String):
            NetworkResponse<List<CountryResponse.SearchCountryResponseItem>, BaseModel> {

        val countries = withContext(ioDispatcher) {
            countriesApi.getCountries(query)
        }

        return countries
    }
}
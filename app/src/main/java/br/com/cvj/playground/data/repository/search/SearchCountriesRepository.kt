package br.com.cvj.playground.data.repository.search

import br.com.cvj.playground.domain.model.BaseModel
import br.com.cvj.playground.domain.model.search.CountryResponse
import com.haroldadmin.cnradapter.NetworkResponse

class SearchCountriesRepository(
    private val searchCountriesDataSource: SearchCountriesDataSource
) {
    suspend fun fetchCountriesBy(query: String): NetworkResponse<
            List<CountryResponse.SearchCountryResponseItem>, BaseModel
            > = searchCountriesDataSource.fetchCountriesBy(query)
}
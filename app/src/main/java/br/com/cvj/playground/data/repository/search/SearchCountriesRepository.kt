package br.com.cvj.playground.data.repository.search

class SearchCountriesRepository(
    private val searchCountriesDataSource: SearchCountriesDataSource
) {
    fun fetchCountriesBy(query: String) =
        searchCountriesDataSource.fetchCountriesBy()
            ?.filter { it.name.contains(query, true) }
}
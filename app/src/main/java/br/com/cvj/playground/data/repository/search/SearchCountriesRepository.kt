package br.com.cvj.playground.data.repository.search

class SearchCountriesRepository(
    private val searchCountriesDataSource: SearchCountriesDataSource
) {
    suspend fun fetchCountriesBy(query: String) =
        searchCountriesDataSource.fetchCountriesBy()
            ?.filter {
                it.name
                .trim()
                .startsWith(query.trim(), true)
            }
}
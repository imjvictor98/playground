package br.com.cvj.playground.data.repository.search

import br.com.cvj.playground.util.extension.removeAccents
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SearchCountriesRepository(
    private val searchCountriesDataSource: SearchCountriesDataSource,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    suspend fun fetchCountriesBy(query: String) = withContext(dispatcher) {
        searchCountriesDataSource.fetchCountriesBy()
            ?.filter {
                it.name
                    .removeAccents()
                    .trim()
                    .startsWith(query.trim(), true)
            }
    }

}
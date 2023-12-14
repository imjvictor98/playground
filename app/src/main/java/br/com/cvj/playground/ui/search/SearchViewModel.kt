package br.com.cvj.playground.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import br.com.cvj.playground.data.repository.search.SearchCountriesRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SearchViewModel(
    private val searchRepository: SearchCountriesRepository
): ViewModel() {

    private val _uiState: MutableStateFlow<SearchUiState> =
        MutableStateFlow(SearchUiState.Initial)

    val uiState: StateFlow<SearchUiState> =
        _uiState.asStateFlow()

    private var countriesJob: Job? = null

    fun fetchCountries(query: String) {
        _uiState.value = SearchUiState.Loading

        viewModelScope.launch {
            countriesJob = CoroutineScope(Dispatchers.IO).launch {
                val countries = searchRepository.fetchCountriesBy(query)

                if (!countries.isNullOrEmpty()) {
                    _uiState.value = SearchUiState.Success(countries)
                } else {
                    _uiState.value = SearchUiState.Empty
                }
            }
        }
    }

    fun cancelFetchCountries() {
        countriesJob?.cancel()
        countriesJob = null
        _uiState.value = SearchUiState.Initial
    }

    fun userIsTyping() {
        _uiState.value = SearchUiState.Typing
    }

    class Factory(private val repository: SearchCountriesRepository): ViewModelProvider.NewInstanceFactory() {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return SearchViewModel(repository) as T
        }
    }
}
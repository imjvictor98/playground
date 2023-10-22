package br.com.cvj.playground.ui.search2

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import br.com.cvj.playground.data.repository.search.SearchCountriesRepository
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

    fun fetchCountries(query: String) {
        _uiState.value = SearchUiState.Loading

        viewModelScope.launch {

            val countries = searchRepository.fetchCountriesBy(query)

            if (!countries.isNullOrEmpty()) {
                _uiState.value = SearchUiState.Success(countries)
            } else {
                _uiState.value = SearchUiState.Empty
            }
        }
    }

    class Factory(private val repository: SearchCountriesRepository): ViewModelProvider.NewInstanceFactory() {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return SearchViewModel(repository) as T
        }
    }
}
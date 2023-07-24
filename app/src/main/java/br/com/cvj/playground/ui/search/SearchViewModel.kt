package br.com.cvj.playground.ui.search

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import br.com.cvj.playground.data.repository.search.SearchCountriesRepository
import br.com.cvj.playground.domain.model.search.SearchCityItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class SearchViewModel(
    private val repository: SearchCountriesRepository
) : ViewModel() {

    private val _uiState: MutableStateFlow<SearchUiState> =
        MutableStateFlow(SearchUiState.Empty(true))

    val uiState: StateFlow<SearchUiState> = _uiState


    fun getCountries(query: String) {
        when (val response = repository.fetchCountriesBy(query.trim())) {
            is List<SearchCityItem> -> {
                if (response.isEmpty()) {
                    _uiState.value = SearchUiState.Empty(true)
                } else {
                    _uiState.value = SearchUiState.Success(response)
                }
            }

            else -> {
                _uiState.value = SearchUiState.Error()
                Log.e("NetworkResponse", response.toString())
            }
        }
    }
}

sealed interface SearchUiState {
    data class Success(val countries: List<SearchCityItem>) :
        SearchUiState

    data class Error(val error: Throwable? = null) : SearchUiState
    data class Empty(val isEmpty: Boolean) : SearchUiState
}

class SearchViewModelFactory(private val repository: SearchCountriesRepository) :
    ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return SearchViewModel(repository) as T
    }
}
package br.com.cvj.playground.ui.search

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import br.com.cvj.playground.data.repository.search.SearchCountriesRepository
import br.com.cvj.playground.domain.model.search.CountryResponse
import com.haroldadmin.cnradapter.NetworkResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class SearchViewModel(
    private val repository: SearchCountriesRepository
) : ViewModel() {

    private val _uiState: MutableStateFlow<SearchUiState> =
        MutableStateFlow(SearchUiState.Empty(true))

    val uiState: StateFlow<SearchUiState> = _uiState


    suspend fun getCountries(query: String) {
        when (val response = repository.fetchCountriesBy(query.trim())) {
            is NetworkResponse.Success<List<CountryResponse.SearchCountryResponseItem>> -> {
                if (response.body.isEmpty()) {
                    _uiState.value = SearchUiState.Empty(true)
                } else {
                    _uiState.value = SearchUiState.Success(response.body)
                }
            }

            is NetworkResponse.NetworkError -> {
                _uiState.value = SearchUiState.Error(response.error.cause)
                Log.e("NetworkResponse", response.error.cause.toString())
            }

            else -> {
                _uiState.value = SearchUiState.Error()
                Log.e("NetworkResponse", response.toString())
            }
        }
    }
}

sealed interface SearchUiState {
    data class Success(val countries: List<CountryResponse.SearchCountryResponseItem>) :
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
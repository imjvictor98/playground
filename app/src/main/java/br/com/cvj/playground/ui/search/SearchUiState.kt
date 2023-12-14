package br.com.cvj.playground.ui.search

import br.com.cvj.playground.domain.model.search.SearchCityItem

sealed class SearchUiState {
    object Initial: SearchUiState()
    data class Success(
        val cities: List<SearchCityItem>
    ): SearchUiState()
    object Empty : SearchUiState()

    object Loading: SearchUiState()

    object Typing: SearchUiState()
}
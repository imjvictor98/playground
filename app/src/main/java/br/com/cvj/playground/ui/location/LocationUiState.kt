package br.com.cvj.playground.ui.location

import br.com.cvj.playground.domain.model.place.SearchNearbyResponse

sealed class LocationUiState {
    object Initial: LocationUiState()
    data class Success(val places: List<SearchNearbyResponse.Place>): LocationUiState()
}
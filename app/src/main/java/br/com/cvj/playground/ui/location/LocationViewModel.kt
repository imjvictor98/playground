package br.com.cvj.playground.ui.location

import android.location.Location
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import br.com.cvj.playground.data.repository.place.PlaceRepository
import br.com.cvj.playground.domain.model.place.PlaceCenter
import br.com.cvj.playground.domain.model.place.PlaceCircle
import br.com.cvj.playground.domain.model.place.PlaceLocationRestriction
import br.com.cvj.playground.domain.model.place.SearchNearbyRequest
import com.haroldadmin.cnradapter.NetworkResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import timber.log.Timber

class LocationViewModel(
    private val repository: PlaceRepository
): ViewModel() {
    private val _locationUiState: MutableStateFlow<LocationUiState> =
        MutableStateFlow(LocationUiState.Initial)

    val locationUiState: StateFlow<LocationUiState> =
        _locationUiState

    fun getRestaurantsNearby(location: Location) {
        viewModelScope.launch {
            when (val response = repository.getPlacesNearby(SearchNearbyRequest(
                includedTypes = listOf("restaurant"),
                maxResultCount = 20,
                locationRestriction = PlaceLocationRestriction(
                    circle = PlaceCircle(
                        center = PlaceCenter(
                            latitude = location.latitude,
                            longitude =  location.longitude
                        ),
                        radius = 3000.0
                    )
                )
            ))) {
                is NetworkResponse.Success -> {
                    _locationUiState.value = LocationUiState.Success(response.body.places)
                    Timber.d("Fetch Places Api returns: %s", response.body.places[0].displayName)
                }
                is NetworkResponse.NetworkError -> {
                    Timber.e(response.error, "Error to make request to places API: %s", response.toString())
                }
                is NetworkResponse.ServerError -> {
                    Timber.d("Error to make request to places API: %s", response.body?.error?.message)
                }
                is NetworkResponse.UnknownError -> {
                    Timber.e(response.error, "Error to make request to places API")
                }
                else -> {
                    //TODO: handle network error
                    Timber.e("Error to make request to places API")
                }
            }
        }
    }
    class Factory(private val repository: PlaceRepository): ViewModelProvider.NewInstanceFactory() {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return LocationViewModel(repository) as T
        }
    }
}
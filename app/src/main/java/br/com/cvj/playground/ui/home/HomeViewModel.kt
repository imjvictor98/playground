package br.com.cvj.playground.ui.home

import android.location.Location
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import br.com.cvj.playground.data.repository.weather.WeatherRepository
import br.com.cvj.playground.util.extension.getLatLongAsString
import com.haroldadmin.cnradapter.NetworkResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HomeViewModel(private val repository: WeatherRepository) : ViewModel() {
    private val _uiState: MutableStateFlow<HomeUiState> =
        MutableStateFlow(HomeUiState.IsLoading())

    val uiState: StateFlow<HomeUiState> =
        _uiState.asStateFlow()


    fun requestForecast(location: Location? = null) {
        if (location != null) {
            viewModelScope.launch {
                val latLong = location.getLatLongAsString()
                when (val response = repository.getForecastToday(latLong)) {
                    is NetworkResponse.Success -> {
                        response.body.location?.let {
                            _uiState.value = HomeUiState.IsLoading(false)
                            _uiState.value = HomeUiState.Success(
                                weatherLocation = it,
                                forecasts = response.body.getForecastListDTO()
                            )
                        }
                    }

                    else -> {
                        // uma tela de erro talvez, com um retry?
                    }
                }

            }
        } else {
            //do nothing
        }
    }

    class Factory(private val repository: WeatherRepository):
    ViewModelProvider.NewInstanceFactory() {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return HomeViewModel(repository) as T
        }
    }

}
package br.com.cvj.playground.ui.home

import br.com.cvj.playground.domain.model.forecast.ForecastDTO
import br.com.cvj.playground.domain.model.weather.WeatherLocation

sealed class HomeUiState {
    data class Success(
        val weatherLocation: WeatherLocation,
        val forecasts: List<ForecastDTO>
        ) : HomeUiState()
    data class IsLoading(val isLoading: Boolean = true): HomeUiState()
}
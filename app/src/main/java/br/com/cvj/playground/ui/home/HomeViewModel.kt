package br.com.cvj.playground.ui.home

import android.location.Location
import androidx.lifecycle.ViewModel
import br.com.cvj.playground.data.network.IWeatherServices
import br.com.cvj.playground.util.extension.getLatLongAsString
import com.haroldadmin.cnradapter.NetworkResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {
    private lateinit var mWeatherServices: IWeatherServices

    private val _uiState: MutableStateFlow<HomeUiState> =
        MutableStateFlow(HomeUiState.IsLoading())

    val uiState: StateFlow<HomeUiState> =
        _uiState.asStateFlow()


    private var mWeatherCall: Job? = null

    fun initialize(weatherServices: IWeatherServices) {
        mWeatherServices = weatherServices
    }

    fun requestForecast(location: Location? = null) {
        if (location != null) {
            mWeatherCall = CoroutineScope(Dispatchers.Main).launch {
                val latLong = location.getLatLongAsString()
                when (val response = mWeatherServices.getForecastToday(latLong)) {
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
            mWeatherCall?.invokeOnCompletion {
//                mView.hideLoading()
            }
        } else {
            //do nothing
        }
    }


}
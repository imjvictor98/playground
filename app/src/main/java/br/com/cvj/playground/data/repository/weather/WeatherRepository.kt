package br.com.cvj.playground.data.repository.weather

import br.com.cvj.playground.util.extension.asDecision
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

class WeatherRepository(
    private val weatherServices: IWeatherServices,
    private val dispatcher: CoroutineContext = Dispatchers.IO
) {
    suspend fun getForecastToday(
        latLong: String,
        alerts: String = true.asDecision(),
        days: Int = 3
    ) = withContext(dispatcher) {
        weatherServices.getForecastToday(latLong, alerts, days)
    }

}
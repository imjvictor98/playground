package br.com.cvj.playground.data.network

import br.com.cvj.playground.domain.model.forecast.ResponseForecast
import br.com.cvj.playground.domain.model.BaseModel
import br.com.cvj.playground.domain.model.weather.ResponseWeather
import br.com.cvj.playground.util.extension.asDecision
import com.haroldadmin.cnradapter.NetworkResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface IWeatherServices {
    @GET("current.json")
    suspend fun getCurrentWeatherAt(@Query("q") latLong: String): NetworkResponse<ResponseWeather, BaseModel>

    @GET("forecast.json")
    suspend fun getForecastToday(
        @Query("q") latLong: String,
        @Query("alerts") alerts: String = true.asDecision()
    ): NetworkResponse<ResponseForecast, BaseModel>

}
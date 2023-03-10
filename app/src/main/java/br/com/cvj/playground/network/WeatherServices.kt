package br.com.cvj.playground.network

import br.com.cvj.playground.model.MBaseModel
import br.com.cvj.playground.model.MWeather
import com.haroldadmin.cnradapter.NetworkResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherServices {
    @GET("current.json")
    suspend fun getCurrentWeatherAt(@Query("q") latLong: String): NetworkResponse<MWeather, MBaseModel>
}
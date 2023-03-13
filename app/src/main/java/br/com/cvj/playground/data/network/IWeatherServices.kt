package br.com.cvj.playground.data.network

import br.com.cvj.playground.domain.model.weather.MBaseModel
import br.com.cvj.playground.domain.model.weather.MWeather
import com.haroldadmin.cnradapter.NetworkResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface IWeatherServices {
    @GET("current.json")
    suspend fun getCurrentWeatherAt(@Query("q") latLong: String): NetworkResponse<MWeather, MBaseModel>
}
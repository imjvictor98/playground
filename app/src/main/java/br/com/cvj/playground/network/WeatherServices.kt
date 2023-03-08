package br.com.cvj.playground.network

import com.haroldadmin.cnradapter.NetworkResponse
import retrofit2.http.GET

interface WeatherServices {
    @GET("")
    suspend fun getWeather(): NetworkResponse<Any, Any>
}
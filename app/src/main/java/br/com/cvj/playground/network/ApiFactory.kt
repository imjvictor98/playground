package br.com.cvj.playground.network

import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object ApiFactory {
    val weatherServices: WeatherServices get() {
        val retrofit = Retrofit.Builder()
            .baseUrl("")
            .addConverterFactory(MoshiConverterFactory.create())
            .build()

        return retrofit.create(WeatherServices::class.java)
    }
}
package br.com.cvj.playground.data.network

import android.content.Context
import br.com.cvj.playground.R
import com.haroldadmin.cnradapter.NetworkResponseAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class ApiFactory(private val context: Context) {
    val weatherServices: IWeatherServices
        get() {
            val httpClient = OkHttpClient.Builder()
            httpClient.addInterceptor {
                val original = it.request()
                val originalHttpUrl = original.url

                val url = originalHttpUrl
                    .newBuilder()
                    .addQueryParameter("key", context.getString(R.string.weather_api_key))
                    .build()

                val requestBuilder = original
                    .newBuilder()
                    .url(url)

                val request = requestBuilder.build()

                it.proceed(request)
            }

            val logging = HttpLoggingInterceptor()
            logging.setLevel(HttpLoggingInterceptor.Level.BASIC)

            httpClient.addInterceptor(logging)

            val moshi = Moshi
                .Builder()
                .add(KotlinJsonAdapterFactory())
                .build()

            return Retrofit.Builder()
                .baseUrl("https://api.weatherapi.com/v1/")
                .addCallAdapterFactory(NetworkResponseAdapterFactory())
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .client(httpClient.build())
                .build()
                .create(IWeatherServices::class.java)
        }
}
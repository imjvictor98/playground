package br.com.cvj.playground.data.network

import android.content.Context
import br.com.cvj.playground.BuildConfig
import br.com.cvj.playground.R
import br.com.cvj.playground.data.repository.place.IPlaceServices
import br.com.cvj.playground.data.repository.search.ISearchCountriesApi
import br.com.cvj.playground.data.repository.weather.IWeatherServices
import br.com.cvj.playground.util.helper.MoshiHelper
import com.haroldadmin.cnradapter.NetworkResponseAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

object ApiFactory {

    private val moshi = MoshiHelper.getInstance()
    fun getWeatherServices(context: Context): IWeatherServices {
        val httpClient = OkHttpClient.Builder()
        httpClient.addInterceptor {
            val original = it.request()
            val originalHttpUrl = original.url

            val url = originalHttpUrl
                .newBuilder()
                .addQueryParameter("key", context.getString(R.string.weather_api_key))
                .addQueryParameter("lang", "pt")
                .build()

            val requestBuilder = original
                .newBuilder()
                .url(url)

            val request = requestBuilder.build()

            it.proceed(request)
        }

        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BASIC)

        httpClient.addInterceptor(logging).callTimeout(10000, TimeUnit.SECONDS)

        return Retrofit.Builder()
            .baseUrl(context.getString(R.string.weather_base_url))
            .addCallAdapterFactory(NetworkResponseAdapterFactory())
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .client(httpClient.build())
            .build()
            .create(IWeatherServices::class.java)
    }

    fun getSearchCountriesServices(context: Context): ISearchCountriesApi {
        val httpClient = OkHttpClient.Builder()
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BASIC)

        httpClient.addInterceptor(logging)

        return Retrofit.Builder()
            .baseUrl(context.getString(R.string.search_countries_base_url))
            .addCallAdapterFactory(NetworkResponseAdapterFactory())
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .client(httpClient.build())
            .build()
            .create(ISearchCountriesApi::class.java)
    }

    fun getPlacesServices(context: Context): IPlaceServices {
        val httpClient = OkHttpClient.Builder()

        httpClient.addInterceptor { chain ->
            val original = chain.request()
            val request = original
                .newBuilder()
                .addHeader("X-Goog-Api-Key", BuildConfig.PLACES_API_KEY)
                .addHeader("Accept-Language", "pt-BR")
                .url(original.url)
                .build()

            chain.proceed(request)
        }

        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)

        httpClient.addInterceptor(logging).callTimeout(10000, TimeUnit.SECONDS)

        return Retrofit.Builder()
            .baseUrl(context.getString(R.string.places_base_url))
            .addCallAdapterFactory(NetworkResponseAdapterFactory())
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .client((httpClient.build()))
            .build()
            .create(IPlaceServices::class.java)
    }
}


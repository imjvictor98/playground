package br.com.cvj.playground.data.network

import android.content.Context
import br.com.cvj.playground.R
import com.haroldadmin.cnradapter.NetworkResponseAdapterFactory
import com.squareup.moshi.FromJson
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonReader
import com.squareup.moshi.JsonWriter
import com.squareup.moshi.Moshi
import com.squareup.moshi.ToJson
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object ApiFactory {
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

        httpClient.addInterceptor(logging)

        val moshi = Moshi
            .Builder()
            .add(KotlinJsonAdapterFactory())
            .add(Date::class.java, object : JsonAdapter<Date>() {
                @FromJson
                override fun fromJson(reader: JsonReader): Date? {
                    val dateString = reader.nextString()
                    return SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault()).parse(dateString)
                }

                @ToJson
                override fun toJson(writer: JsonWriter, value: Date?) {
                    val dateString =
                        value?.let {
                            SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault()).format(
                                it
                            )
                        } ?: run {
                            null
                        }
                    writer.value(dateString)
                }
            })
            .build()

        return Retrofit.Builder()
            .baseUrl(context.getString(R.string.base_url))
            .addCallAdapterFactory(NetworkResponseAdapterFactory())
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .client(httpClient.build())
            .build()
            .create(IWeatherServices::class.java)
    }
}


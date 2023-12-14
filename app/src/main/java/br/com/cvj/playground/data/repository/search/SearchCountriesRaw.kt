package br.com.cvj.playground.data.repository.search

import android.content.res.Resources
import br.com.cvj.playground.R
import br.com.cvj.playground.domain.model.search.SearchCityItem
import br.com.cvj.playground.util.helper.RawHelper
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


class SearchCountriesRaw(private val resources: Resources) {
    fun getCities(): List<SearchCityItem>? {
        return try {
            val listSearchCityToken = object : TypeToken<ArrayList<SearchCityItem>?>() {}.type
            Gson().fromJson<List<SearchCityItem>>(
                RawHelper.jsonToString(resources, R.raw.cities),
                listSearchCityToken
            )
        } catch (e: Exception) {
            null
        }
    }
}
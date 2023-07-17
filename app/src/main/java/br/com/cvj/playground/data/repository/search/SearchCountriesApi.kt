package br.com.cvj.playground.data.repository.search

import br.com.cvj.playground.domain.model.BaseModel
import br.com.cvj.playground.domain.model.search.CountryResponse
import com.haroldadmin.cnradapter.NetworkResponse
import retrofit2.http.GET
import retrofit2.http.Path


interface SearchCountriesApi {
    @GET("/v3.1/name/{name}")
    suspend fun getCountries(
        @Path("name") query: String
    ): NetworkResponse<List<CountryResponse.SearchCountryResponseItem>, BaseModel>
}


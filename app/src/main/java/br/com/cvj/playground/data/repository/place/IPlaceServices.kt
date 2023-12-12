package br.com.cvj.playground.data.repository.place


import br.com.cvj.playground.domain.model.place.SearchNearbyError
import br.com.cvj.playground.domain.model.place.SearchNearbyRequest
import br.com.cvj.playground.domain.model.place.SearchNearbyResponse
import br.com.cvj.playground.util.helper.GooglePlaceHelper
import com.haroldadmin.cnradapter.NetworkResponse
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface IPlaceServices {

    @POST("v1/places:searchNearby")
    suspend fun searchNearby(
        @Header("X-Goog-FieldMask") masksAsString: String = GooglePlaceHelper.getFieldsMask(),
        @Body body: SearchNearbyRequest,

    ): NetworkResponse<SearchNearbyResponse, SearchNearbyError>
}
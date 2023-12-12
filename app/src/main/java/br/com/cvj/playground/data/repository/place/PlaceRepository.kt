package br.com.cvj.playground.data.repository.place


import br.com.cvj.playground.domain.model.place.SearchNearbyRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

class PlaceRepository(
    private val placeServices: IPlaceServices,
    private val dispatcher: CoroutineContext = Dispatchers.IO
) {
    suspend fun getPlacesNearby(
        placeDescription: SearchNearbyRequest
    ) = withContext(dispatcher) {
        placeServices.searchNearby(body = placeDescription)
    }
}
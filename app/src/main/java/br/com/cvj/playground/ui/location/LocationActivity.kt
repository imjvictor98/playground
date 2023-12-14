package br.com.cvj.playground.ui.location

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.location.Location
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import br.com.cvj.playground.R
import br.com.cvj.playground.data.network.ApiFactory
import br.com.cvj.playground.data.repository.place.PlaceRepository
import br.com.cvj.playground.ui.theme.PlaygroundTheme
import br.com.cvj.playground.util.extension.toPrecision
import br.com.cvj.playground.util.helper.BitmapDescriptorHelper
import br.com.cvj.playground.util.helper.FieldSelector
import br.com.cvj.playground.util.helper.LocationHelper
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import timber.log.Timber

class LocationActivity : ComponentActivity() {
    companion object {
        private const val EXTRA_LOCATION = "EXTRA_LOCATION"
        fun startActivity(context: Context, location: Location? = null) {
            Intent(context, LocationActivity::class.java).apply {
                putExtra(EXTRA_LOCATION, location)
                context.startActivity(this)
            }
        }
    }

    private var extraLocation: Location? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        intent.extras?.apply {
            extraLocation = getParcelable(EXTRA_LOCATION) as? Location
        }
        Timber.d("Location: ${extraLocation?.latitude}")

        setContent {
            PlaygroundTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    LocationScreen(extraLocation)
                }
            }
        }
    }
}

@Composable
fun LocationScreen(
    location: Location? = null, viewModel: LocationViewModel = viewModel(
        factory = LocationViewModel.Factory(
            PlaceRepository(ApiFactory.getPlacesServices(LocalContext.current as Activity))
        )
    )
) {
    val locationUiState = viewModel.locationUiState.collectAsState()

    val placeFields = FieldSelector.allExcept(
        Place.Field.ADDRESS_COMPONENTS,
        Place.Field.CURBSIDE_PICKUP,
        Place.Field.CURRENT_OPENING_HOURS,
        Place.Field.DELIVERY,
        Place.Field.DINE_IN,
        Place.Field.OPENING_HOURS,
        Place.Field.PHONE_NUMBER,
        Place.Field.RESERVABLE,
        Place.Field.SECONDARY_OPENING_HOURS,
        Place.Field.SERVES_BEER,
        Place.Field.SERVES_BREAKFAST,
        Place.Field.SERVES_BRUNCH,
        Place.Field.SERVES_DINNER,
        Place.Field.SERVES_LUNCH,
        Place.Field.SERVES_VEGETARIAN_FOOD,
        Place.Field.SERVES_WINE,
        Place.Field.TAKEOUT,
        Place.Field.UTC_OFFSET,
        Place.Field.WEBSITE_URI,
        Place.Field.WHEELCHAIR_ACCESSIBLE_ENTRANCE,
        Place.Field.EDITORIAL_SUMMARY,
    )

    val placesClient = Places.createClient(LocalContext.current)

    var mapProperties by remember {
        mutableStateOf(
            MapProperties(maxZoomPreference = 20f, minZoomPreference = 5f)
        )
    }

    var mapUiSettings by remember {
        mutableStateOf(
            MapUiSettings(mapToolbarEnabled = false)
        )
    }

    val cameraPositionState = rememberCameraPositionState {
        if (location?.latitude != null) {
            position =
                CameraPosition.fromLatLngZoom(LatLng(location.latitude, location.longitude), 13f)
        }
    }

    Box(Modifier.fillMaxSize()) {
        GoogleMap(
            properties = mapProperties,
            uiSettings = mapUiSettings,
            cameraPositionState = cameraPositionState
        ) {
            when (val state = locationUiState.value) {
                is LocationUiState.Success -> {
                    Marker(
                        state = MarkerState(position = LatLng(
                            location!!.latitude,
                            location.longitude
                        )
                        ),
                        title = "You"
                    )
                    state.places.forEach { place ->
                        Marker(
                            state = MarkerState(
                                position = LatLng(
                                    place.location.latitude,
                                    place.location.longitude,
                                ),
                            ),
                            title = "${(LocationHelper.getLocationByLatLong(place.location.latitude, place.location.longitude).distanceTo(location!!)/1000).toPrecision(2)} Km - ${place.displayName?.text}",
                            icon = BitmapDescriptorHelper.bitmapDescriptorFromVector(LocalContext.current, R.drawable.ic_restaurant_pin),
                            onClick = { marker ->
                                Timber.d("OnClick")
                                false
                            }
                        )
                    }
                }
                else -> {}
            }


        }

        Column {

            Button(onClick = {
                mapProperties = mapProperties.copy(
                    isBuildingEnabled = !mapProperties.isBuildingEnabled
                )
            }) {
                Text(text = "Toggle isBuildingEnabled")
            }
            Button(onClick = {
                mapUiSettings = mapUiSettings.copy(
                    mapToolbarEnabled = !mapUiSettings.mapToolbarEnabled
                )
            }) {
                Text(text = "Toggle mapToolbarEnabled")
            }

            Button(onClick = {
                // Move the camera to a new zoom level
                cameraPositionState.move(CameraUpdateFactory.zoomIn())
            }) {
                Text(text = "Zoom In")
            }

            Button(onClick = {
                viewModel.getRestaurantsNearby(location!!)
            }) {
                Text(text = "Current Place")
            }

        }

        if (locationUiState.value is LocationUiState.Success) {
            LocationBottomSheet((locationUiState.value as LocationUiState.Success).places)
        }
    }

}


package br.com.cvj.playground.ui.location

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.staggeredgrid.LazyHorizontalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Call
import androidx.compose.material.icons.outlined.Place
import androidx.compose.material.icons.outlined.Share
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.rememberNestedScrollInteropConnection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.cvj.playground.R
import br.com.cvj.playground.domain.model.place.SearchNearbyResponse
import br.com.cvj.playground.ui.theme.Colors
import br.com.cvj.playground.ui.theme.Typography
import br.com.cvj.playground.ui.widget.RatingBar
import br.com.cvj.playground.util.extension.callPhone
import br.com.cvj.playground.util.extension.isPhoneNumber
import br.com.cvj.playground.util.extension.shareToMaps
import br.com.cvj.playground.util.extension.shareLink
import br.com.cvj.playground.util.helper.GoogleMapsHelper
import com.bumptech.glide.integration.compose.CrossFade
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.placeholder

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LocationBottomSheet(places: List<SearchNearbyResponse.Place>) {
    val scaffoldState = rememberBottomSheetScaffoldState()

    BottomSheetScaffold(
        scaffoldState = scaffoldState,
        sheetPeekHeight = 128.dp,
        sheetContentColor = Colors.White,
        sheetContainerColor = Colors.White,
        containerColor = Colors.White,
        contentColor = Colors.White,
        sheetContent = {
            Surface(
                modifier = Modifier
                    .nestedScroll(rememberNestedScrollInteropConnection())
            ) {
                LazyColumn(Modifier.padding(bottom = 8.dp)) {
                    itemsIndexed(places) { index, place ->
                        Column(
                            modifier = Modifier
                                .background(Colors.Gray)
                                .padding(bottom = 6.dp)
                        ) {
                            Column(
                                modifier = Modifier
                                    .clip(
                                        when (index) {
                                            0 -> {
                                                RoundedCornerShape(
                                                    bottomEnd = 16.dp,
                                                    bottomStart = 16.dp
                                                )
                                            }

                                            places.size - 1 -> {
                                                RoundedCornerShape(topEnd = 16.dp, topStart = 16.dp)
                                            }

                                            else -> {
                                                RoundedCornerShape(16.dp)
                                            }
                                        }
                                    )

                                    .background(Colors.White)
                                    .padding(8.dp)
                            ) {
                                val placePhotosCount = place.photos?.size ?: 0
                                LocationPlaceGridImages(
                                    photos = place.photos?.subList(
                                        0,
                                        if (placePhotosCount >= 6) 6 else placePhotosCount
                                    ) ?: emptyList()
                                )
                                LocationPlaceDetails(place = place)
                            }
                        }
                    }
                }
            }
        }
    ) { innerPadding ->
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun LocationPlaceGridImages(photos: List<SearchNearbyResponse.Place.Photo?>) {
    val state = rememberLazyStaggeredGridState()

    LazyHorizontalStaggeredGrid(
        state = state,
        modifier = Modifier.height(300.dp),
        rows = StaggeredGridCells.Fixed(2),
        contentPadding = PaddingValues(2.dp),

        ) {
        items(photos) { photo ->
            Box(
                modifier = Modifier
                    .padding(4.dp)
                    .clip(RoundedCornerShape(16.dp))
            ) {

                GlideImage(
                    transition = CrossFade,
                    contentScale = ContentScale.Crop,
                    loading = placeholder(R.color.transparent),
                    failure = placeholder(R.drawable.placeholder_image_error),
                    model = GoogleMapsHelper.getPlacePhotoUrl(
                        300,
                        300,
                        photo?.name.toString()
                    ),
                    contentDescription = ""
                )
            }

        }
    }

}

@Composable
fun LocationPlaceDetails(place: SearchNearbyResponse.Place) {
    Column(modifier = Modifier.padding(8.dp)) {
        Text(
            text = place.displayName?.text.toString(),
            color = Colors.Black,
            fontSize = 20.sp,
        )

        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                modifier = Modifier.padding(end = 4.dp),
                text = place.rating.toString(),
                fontSize = Typography.labelLarge.fontSize,
                color = Colors.Gray600
            )
            RatingBar(
                modifier = Modifier.padding(bottom = 2.dp),
                rating = place.rating ?: 0.0,
                starsColor = Colors.Yellow400,
                starSize = 16.dp
            )
        }

        LocationPlaceDetailsOptions(place)
        LocationPlaceDetailsStatus(place)
        LocationPlacesDetailsButtons(place = place)
    }

}

@Composable
fun LocationPlaceDetailsOptions(place: SearchNearbyResponse.Place) {
    val contents = mutableListOf<String>()

    Row(verticalAlignment = Alignment.CenterVertically) {
        if (place.primaryTypeDisplayName?.text?.isNotEmpty() == true) {
            contents.add(place.primaryTypeDisplayName.text)
        }

        if (place.accessibilityOptions?.wheelchairAccessibleEntrance == true) {
            contents.add("wheel")
        }

        if (place.toPriceLevelMoneyDTO().toMoneyString().isNotEmpty()) {
            contents.add(place.toPriceLevelMoneyDTO().toMoneyString())
        }

        val showDots = contents.size >= 2

        contents.forEachIndexed { index, content ->
            val contentPadding = if (index == 0) {
                Modifier.padding(end = 4.dp)
            } else {
                Modifier.padding(horizontal = 4.dp)
            }

            if (content === "wheel") {
                Image(
                    modifier = Modifier
                        .then(contentPadding)
                        .size(14.dp),
                    painter = painterResource(id = R.drawable.ic_wheelchair_line_18_black),
                    contentDescription = ""
                )
            } else {
                Text(
                    modifier = contentPadding,
                    text = content,
                    fontSize = Typography.labelLarge.fontSize,
                    color = Colors.Black100
                )
            }

            if (showDots && index < contents.size - 1) {
                Text(
                    text = "•",
                    fontSize = Typography.labelLarge.fontSize,
                    color = Colors.Black100
                )
            }
        }
    }
}

@Composable
fun LocationPlaceDetailsStatus(place: SearchNearbyResponse.Place) {
    val contents = mutableListOf<String>()

    Row(verticalAlignment = Alignment.CenterVertically) {
        place.currentOpeningHours?.let { openingHours ->
            if (openingHours.openNow == true) {
                contents.add("Aberto")
            } else {
                contents.add("Fechado")
            }
        }

        val showDots = contents.size >= 2

        contents.forEachIndexed { index, content ->
            val contentPadding = if (index == 0) {
                Modifier.padding(end = 4.dp)
            } else {
                Modifier.padding(horizontal = 4.dp)
            }

            if (content == "Aberto") {
                Text(
                    modifier = contentPadding,
                    text = content,
                    fontSize = Typography.labelLarge.fontSize,
                    fontWeight = FontWeight.Bold,
                    color = Colors.Green400
                )
            } else if (content == "Fechado") {
                Text(
                    modifier = contentPadding,
                    text = content,
                    fontSize = Typography.labelLarge.fontSize,
                    fontWeight = FontWeight.Bold,
                    color = Colors.Red400
                )
            }


            if (showDots && index < contents.size - 1) {
                Text(
                    text = "•",
                    fontSize = Typography.labelLarge.fontSize,
                    color = Colors.Black100
                )
            }
        }
    }
}

@Composable
fun LocationPlacesDetailsButtons(place: SearchNearbyResponse.Place) {
    val stateScroll = rememberScrollState()
    val context = LocalContext.current.applicationContext

    Row(
        modifier = Modifier
            .horizontalScroll(stateScroll)
            .padding(top = 8.dp)
    ) {

        Button(
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
            modifier = Modifier.padding(end = 8.dp),
            shape = RoundedCornerShape(24.dp),
            onClick = {
                context.shareToMaps(place.googleMapsUri.toString())
            }
        ) {
            Icon(
                modifier = Modifier.size(24.dp),
                painter = painterResource(id = R.drawable.ic_outline_directions_24),
                contentDescription = ""
            )
            Text(modifier = Modifier.padding(horizontal = 8.dp), text = "Rotas")
        }

        if (place.internationalPhoneNumber?.isPhoneNumber() == true) {
            Button(
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                modifier = Modifier.padding(end = 8.dp),
                shape = RoundedCornerShape(24.dp),
                onClick = {
                    context.callPhone(place.internationalPhoneNumber)
                }
            ) {
                Icon(
                    modifier = Modifier.size(24.dp),
                    imageVector = Icons.Outlined.Call,
                    contentDescription = ""
                )
                Text(modifier = Modifier.padding(horizontal = 8.dp), text = "Ligar")
            }
        }

        Button(
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
            modifier = Modifier.padding(end = 8.dp),
            shape = RoundedCornerShape(24.dp),
            onClick = {
                context.shareLink(place.googleMapsUri.toString())
            }
        ) {
            Icon(
                modifier = Modifier.size(24.dp),
                imageVector = Icons.Outlined.Share,
                contentDescription = ""
            )
            Text(modifier = Modifier.padding(horizontal = 8.dp), text = "Compartilhar")
        }
    }
}

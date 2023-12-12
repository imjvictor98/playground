package br.com.cvj.playground.util.helper

import br.com.cvj.playground.domain.model.place.SearchNearbyResponse

object GooglePlaceHelper {
    fun getFieldsMask() = "places.accessibilityOptions,places.addressComponents,places.adrFormatAddress,places.businessStatus,places.displayName,places.formattedAddress,places.googleMapsUri,places.iconBackgroundColor,places.iconMaskBaseUri,places.id,places.location,places.name,places.photos,places.plusCode,places.primaryType,places.primaryTypeDisplayName,places.shortFormattedAddress,places.subDestinations,places.adrFormatAddress,places.currentOpeningHours,places.currentSecondaryOpeningHours,places.internationalPhoneNumber,places.nationalPhoneNumber,places.priceLevel,places.rating,places.regularOpeningHours,places.regularSecondaryOpeningHours,places.userRatingCount,places.websiteUri,places.allowsDogs,places.curbsidePickup,places.delivery,places.dineIn,places.editorialSummary"
}
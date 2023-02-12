package com.tim.fakegps.feature.geocoder

import com.tim.fakegps.feature.geocoder.model.LocationAddress

class YandexGeocoder : Geocoder {
    override fun isPresent(): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun getLocationForName(name: String): List<LocationAddress> {
        TODO("Not yet implemented")
    }

}
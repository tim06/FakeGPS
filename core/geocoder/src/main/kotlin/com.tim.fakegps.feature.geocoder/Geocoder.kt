package com.tim.fakegps.feature.geocoder

import com.tim.fakegps.feature.geocoder.model.LocationAddress

interface Geocoder {
    fun isPresent(): Boolean
    suspend fun getLocationForName(name: String): List<LocationAddress>
}
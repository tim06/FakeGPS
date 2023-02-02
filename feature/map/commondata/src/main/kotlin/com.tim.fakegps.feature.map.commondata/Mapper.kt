package com.tim.fakegps.feature.map.commondata

import com.google.android.gms.maps.model.LatLng
import com.tim.location.Location

val latLngToLocation: (LatLng) -> Location = {
    Location(it.latitude, it.longitude)
}
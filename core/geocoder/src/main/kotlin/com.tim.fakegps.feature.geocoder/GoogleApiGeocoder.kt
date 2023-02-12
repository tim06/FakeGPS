package com.tim.fakegps.feature.geocoder

import android.location.Address
import android.location.Geocoder.GeocodeListener
import android.os.Build
import com.tim.fakegps.feature.geocoder.model.LocationAddress
import com.tim.location.Location
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

class GoogleApiGeocoder(
    private val geocoder: android.location.Geocoder
) : Geocoder {

    override fun isPresent(): Boolean = android.location.Geocoder.isPresent()

    override suspend fun getLocationForName(name: String): List<LocationAddress> =
        suspendCancellableCoroutine { continuation ->
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                geocoder.getFromLocationName(name, 5, object : GeocodeListener {
                    override fun onGeocode(addresses: List<Address>) {
                        continuation.resume(addresses.toLocationAddresses())
                    }

                    override fun onError(errorMessage: String?) {
                        super.onError(errorMessage)
                        continuation.resume(emptyList())
                    }
                })
            } else {
                runCatching {
                    geocoder.getFromLocationName(name, 5) ?: emptyList()
                }.onSuccess { addresses ->
                    continuation.resume(addresses.toLocationAddresses())
                }.onFailure { throwable ->
                    continuation.resume(emptyList())
                }
            }
        }

    private fun List<Address>.toLocationAddresses() =
        map {
            LocationAddress(
                name = it.countryName,
                location = Location(it.latitude, it.longitude)
            )
        }
}
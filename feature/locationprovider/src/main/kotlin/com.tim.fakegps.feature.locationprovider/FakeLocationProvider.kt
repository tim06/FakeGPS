package com.tim.fakegps.feature.locationprovider

import android.location.Criteria
import android.location.Location
import android.location.LocationManager
import android.location.provider.ProviderProperties
import android.os.Build
import android.os.SystemClock
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class FakeLocationProvider(
    private val locationManager: LocationManager
): CoroutineScope {

    override val coroutineContext: CoroutineContext
        get() = SupervisorJob() + Dispatchers.Main

    private var locationJob: Job? = null

    fun startFakeLocation(location: com.tim.location.Location) {
        startFakeLocation(location.latitude, location.longitude)
    }

    private fun startFakeLocation(latitude: Double, longitude: Double) {
        locationJob = launch {
            while(true) {
                setFakeLocationParams(LocationManager.GPS_PROVIDER, latitude, longitude)
                setFakeLocationParams(LocationManager.NETWORK_PROVIDER, latitude, longitude)
                delay(1000)
            }
        }
    }

    fun stopFakeLocation() {
        locationManager.removeTestProvider(LocationManager.GPS_PROVIDER)
        locationManager.removeTestProvider(LocationManager.NETWORK_PROVIDER)
        locationJob?.cancel()
    }

    private fun setFakeLocationParams(provider: String, latitude: Double, longitude: Double) {
        val powerUsage = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            ProviderProperties.POWER_USAGE_LOW
        } else {
            Criteria.POWER_LOW
        }
        val accuracy = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            ProviderProperties.ACCURACY_COARSE
        } else {
            Criteria.ACCURACY_LOW
        }
        runCatching {
            locationManager.addTestProvider(
                provider,
                false,
                false,
                false,
                false,
                false,
                true,
                true,
                powerUsage,
                accuracy
            )
        }
        val newFakeLocation = Location(provider).apply {
            setLatitude(latitude)
            setLongitude(longitude)
            altitude = 3.0
            time = System.currentTimeMillis()
            speed = 0.01f
            bearing = 1f
            setAccuracy(3f)
            elapsedRealtimeNanos = SystemClock.elapsedRealtimeNanos()
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                bearingAccuracyDegrees = 0.1f
                verticalAccuracyMeters = 0.1f
                speedAccuracyMetersPerSecond = 0.01f
            }
        }
        locationManager.setTestProviderEnabled(provider, true)
        locationManager.setTestProviderLocation(provider, newFakeLocation)
    }

    fun clear() {
        coroutineContext.cancel()
    }
}
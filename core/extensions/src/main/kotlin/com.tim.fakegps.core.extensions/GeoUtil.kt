package com.tim.fakegps.core.extensions

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import androidx.annotation.RequiresPermission
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.produceState
import androidx.compose.ui.platform.LocalContext
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.tasks.Task
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

@RequiresPermission(anyOf = ["android.permission.ACCESS_COARSE_LOCATION", "android.permission.ACCESS_FINE_LOCATION"])
@Composable
fun getCurrentLocation(): State<Result<Location>> {
    val context = LocalContext.current
    return produceState<Result<Location>>(initialValue = Result.Loading) {
        val location = getCurrentLocation(context)
        value = if (location == null) {
            Result.Error
        } else {
            Result.Success(location)
        }
    }
}

sealed class Result<out T> {
    object Loading : Result<Nothing>()
    object Error : Result<Nothing>()
    data class Success<T>(val item: T) : Result<T>()
}

suspend fun getCurrentLocation(context: Context): Location? {
    val locationProvider = LocationServices.getFusedLocationProviderClient(context)
    return getLastKnownDeviceLocation(locationProvider) ?: awaitLocation(locationProvider)
}

suspend fun getCurrentLocation(locationProvider: FusedLocationProviderClient): Location? {
    return getLastKnownDeviceLocation(locationProvider) ?: awaitLocation(locationProvider)
}

/*@SuppressLint("MissingPermission")
fun locationUpdated(locationProvider: FusedLocationProviderClient): Flow<Location> = flow {
    val listener = { location: Location ->
        emit(location)
    }
    val request = LocationRequest.Builder(60000)
        .setWaitForAccurateLocation(true)
        .build()
    locationProvider.requestLocationUpdates(request, listener, null)
    locationProvider.removeLocationUpdates(listener)
}*/

@SuppressLint("MissingPermission")
private suspend fun getLastKnownDeviceLocation(locationProvider: FusedLocationProviderClient) =
    suspendCancellableCoroutine { continuation ->
        locationProvider.lastLocation.addOnCompleteListener { task: Task<Location?> ->
            if (task.isSuccessful) {
                continuation.resume(task.result)
            } else {
                continuation.resume(null)
            }
        }
    }

@SuppressLint("MissingPermission")
private suspend fun awaitLocation(locationProvider: FusedLocationProviderClient) =
    suspendCancellableCoroutine<Location?> { continuation ->
        val request =
            LocationRequest.Builder(60000).setWaitForAccurateLocation(true).setMaxUpdates(1).build()
        locationProvider.requestLocationUpdates(request, continuation::resume, null)
    }
package com.tim.fakegps.feature.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.location.FusedLocationProviderClient
import com.tim.fakegps.core.extensions.getCurrentLocation
import com.tim.fakegps.core.permission.PermissionChecker
import com.tim.fakegps.feature.locationprovider.FakeLocationProvider
import com.tim.fakegps.feature.map.state.Coordinates
import com.tim.fakegps.feature.map.state.UiState
import com.tim.feature.gmschecker.GmsChecker
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor(
    private val permissionChecker: PermissionChecker,
    private val fakeLocationProvider: FakeLocationProvider,
    private val locationProvider: FusedLocationProviderClient,
    private val gmsChecker: GmsChecker
) : ViewModel() {

    val uiState = MutableStateFlow(UiState())

    override fun onCleared() {
        super.onCleared()
        fakeLocationProvider.clear()
    }

    fun isApplicationFakeDataAllowed(): Boolean = permissionChecker.isMockLocationEnabled()

    fun onPermissionsResult(granted: Boolean) {
        if (granted) {
            viewModelScope.launch {
                getCurrentLocation(locationProvider)?.let { location ->
                    uiState.update {
                        it.copy(
                            deviceLocation = location, isPermissionsGranted = true
                        )
                    }
                }
            }
        } else {
            uiState.update { it.copy(isPermissionsGranted = false) }
        }
    }

    fun onMapClicked(coordinates: Coordinates) {
        uiState.update { it.copy(fakeLocation = coordinates) }
    }

    fun onStartStopButtonClick() {
        if (uiState.value.isRunning) {
            onStopProvideFakeGpsDataClick()
        } else {
            onStartProvideFakeGpsDataClick()
        }
    }

    fun isPlayServicesAvailable() = gmsChecker.isPlayServicesAvailable()

    private fun onStartProvideFakeGpsDataClick() {
        val currentState = uiState.value
        if (currentState.isRunning) {
            onStopProvideFakeGpsDataClick()
            onStartProvideFakeGpsDataClick()
        } else {
            val location = currentState.fakeLocation
            if (location != null) {
                fakeLocationProvider.startFakeLocation(location.latitude, location.longitude)
                uiState.update { it.copy(isRunning = true) }
            }
        }
    }

    private fun onStopProvideFakeGpsDataClick() {
        fakeLocationProvider.stopFakeLocation()
        uiState.update { it.copy(isRunning = false) }
    }
}
package com.tim.fakegps.feature.map.google

import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*
import com.tim.fakegps.feature.map.state.Coordinates
import com.tim.fakegps.feature.map.state.UiState
import kotlinx.coroutines.launch

@Composable
fun FakeGpsGoogleMap(
    uiState: UiState,
    onMapClick: (Coordinates) -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition(LatLng(40.52, 34.34), 0f, 0f, 0f)
    }

    val fakePositionMarkerState = rememberMarkerState()
    uiState.fakeLocation?.let {
        fakePositionMarkerState.position = LatLng(it.latitude, it.longitude)
    }

    GoogleMap(cameraPositionState = cameraPositionState, uiSettings = MapUiSettings(
        compassEnabled = true, mapToolbarEnabled = false, myLocationButtonEnabled = false
    ), onMapLoaded = {
        if (uiState.isInitialMoveToDevicePositionAllowed) {
            uiState.deviceLocation?.let { deviceLocation ->
                coroutineScope.launch {
                    cameraPositionState.animate(
                        CameraUpdateFactory.newCameraPosition(
                            CameraPosition(
                                LatLng(
                                    deviceLocation.latitude, deviceLocation.longitude
                                ), 5f, 0f, 0f
                            )
                        )
                    )
                }
            }
        }
    }, onMapClick = { latLng ->
        onMapClick.invoke(Coordinates(latLng.latitude, latLng.longitude))
    }) {
        if (uiState.fakeLocation != null) {
            MarkerOfFakeLocation(fakePositionMarkerState)
        }
    }
}

@Composable
fun MarkerOfFakeLocation(state: MarkerState) {
    Marker(
        state = state,
        title = "Fake location",
        icon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)
    )
}
package com.tim.fakegps.feature.map.provider.google

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.compose.rememberMarkerState
import com.tim.fakegps.feature.map.common.commondata.CommonMapMain.Model
import com.tim.fakegps.feature.map.common.commondata.latLngToLocation
import com.tim.location.Location

@Composable
fun GoogleContent(modifier: Modifier = Modifier, state: Model, onMapClick: (Location) -> Unit) {
    //val coroutineScope = rememberCoroutineScope()
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition(LatLng(40.52, 34.34), 0f, 0f, 0f)
    }

    Box {
        GoogleMap(modifier = modifier, cameraPositionState = cameraPositionState, uiSettings = MapUiSettings(
            compassEnabled = true, mapToolbarEnabled = false, myLocationButtonEnabled = false
        ), onMapLoaded = {
            /*if (uiState.isInitialMoveToDevicePositionAllowed) {
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
            }*/
        }, onMapClick = { latLng ->
            onMapClick.invoke(latLngToLocation(latLng))
        }) {
            val fakePositionMarkerState = rememberMarkerState()
            LaunchedEffect(state.location) {
                state.location?.let {
                    fakePositionMarkerState.position = LatLng(it.latitude, it.longitude)
                }
            }
            state.location?.let {
                MarkerOfFakeLocation(fakePositionMarkerState)
            }
        }
    }
}

@Composable
private fun MarkerOfFakeLocation(state: MarkerState) {
    Marker(
        state = state,
        title = "Fake location",
        icon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)
    )
}
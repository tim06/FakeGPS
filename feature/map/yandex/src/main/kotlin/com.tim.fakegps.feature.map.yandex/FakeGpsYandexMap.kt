package com.tim.fakegps.feature.map.yandex

import androidx.compose.runtime.Composable
import androidx.compose.ui.viewinterop.AndroidView
import com.tim.fakegps.feature.map.state.Coordinates
import com.tim.fakegps.feature.map.state.UiState
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.CameraPosition
import com.yandex.mapkit.mapview.MapView
import com.yandex.runtime.image.ImageProvider

@Composable
fun FakeGpsYandexMap(
    uiState: UiState, onMapClick: (Coordinates) -> Unit
) {
    val mapView = rememberMapViewWithLifecycle(onMapClick)

    AndroidView(factory = { mapView }) { mv ->
        mv.map.apply {
            isZoomGesturesEnabled = true
            val point = Point(40.52, 34.34)
            move(
                CameraPosition(
                    point, 5f, 0f, 0f
                )
            )
        }
    }

    uiState.fakeLocation?.let { coordinates ->
        mapView.addMarker(coordinates)
    }

}

fun MapView.addMarker(coordinates: Coordinates) {
    map.mapObjects.clear()
    map.mapObjects.addPlacemark(
        Point(coordinates.latitude, coordinates.longitude),
        ImageProvider.fromResource(context, R.drawable.pin)
    )
}
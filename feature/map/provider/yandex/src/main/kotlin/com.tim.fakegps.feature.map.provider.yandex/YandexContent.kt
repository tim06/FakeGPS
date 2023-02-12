package com.tim.fakegps.feature.map.provider.yandex

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.tim.fakegps.feature.map.common.commondata.CommonMapMain.Model
import com.tim.location.Location
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.CameraPosition
import com.yandex.mapkit.mapview.MapView
import com.yandex.runtime.image.ImageProvider

@Composable
fun YandexContent(modifier: Modifier = Modifier, state: Model, onMapClick: (Location) -> Unit) {
    val mapView = rememberMapViewWithLifecycle(onMapClick)

    AndroidView(
        modifier = modifier,
        factory = { mapView }
    ) { mv ->
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

    state.location?.let { coordinates ->
        mapView.addMarker(coordinates)
    }
}

fun MapView.addMarker(location: Location) {
    map.mapObjects.clear()
    map.mapObjects.addPlacemark(
        Point(location.latitude, location.longitude),
        ImageProvider.fromResource(context, R.drawable.pin)
    )
}
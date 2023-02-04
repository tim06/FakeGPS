package com.tim.fakegps.feature.map.yandex

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.Lifecycle
import com.tim.fakegps.core.lifecycle.OnLifecycleEvent
import com.tim.location.Location
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.layers.GeoObjectTapListener
import com.yandex.mapkit.mapview.MapView

@Composable
fun rememberMapViewWithLifecycle(onClick: (Location) -> Unit): MapView {
    val context = LocalContext.current

    val mapView = remember { MapView(context) }

    val clickListener = GeoObjectTapListener { geoObjTapEvent ->
        geoObjTapEvent.geoObject.geometry.map { it.point }.firstOrNull()?.let {
            onClick.invoke(Location(it.latitude, it.longitude))
        }
        true
    }

    OnLifecycleEvent { _, event ->
        when (event) {
            Lifecycle.Event.ON_START -> {
                mapView.onStart()
                MapKitFactory.getInstance().onStart()
                mapView.overlay
                mapView.mapWindow
                mapView.map
                mapView.map.addTapListener(clickListener)
            }

            Lifecycle.Event.ON_STOP -> {
                mapView.onStop()
                MapKitFactory.getInstance().onStop()
                mapView.map.removeTapListener(clickListener)
            }

            else -> {}
        }
    }

    return mapView
}
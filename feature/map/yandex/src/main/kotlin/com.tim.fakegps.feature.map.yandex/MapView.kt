package com.tim.fakegps.feature.map.yandex

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.Lifecycle
import com.tim.fakegps.core.lifecycle.OnLifecycleEvent
import com.tim.fakegps.feature.map.state.Coordinates
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.layers.GeoObjectTapListener
import com.yandex.mapkit.mapview.MapView

@Composable
fun rememberMapViewWithLifecycle(onClick: (Coordinates) -> Unit): MapView {
    val context = LocalContext.current

    val mapView = remember { MapView(context) }

    val clickListener = GeoObjectTapListener { geoObjTapEvent ->
        geoObjTapEvent.geoObject.geometry.firstOrNull()?.point?.let {
            onClick.invoke(Coordinates(it.latitude, it.longitude))
        }
        true
    }

    OnLifecycleEvent { _, event ->
        when (event) {
            Lifecycle.Event.ON_START -> {
                mapView.onStart()
                MapKitFactory.getInstance().onStart()
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
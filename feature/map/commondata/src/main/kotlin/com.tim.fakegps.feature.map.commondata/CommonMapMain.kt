package com.tim.fakegps.feature.map.commondata

import com.tim.location.Location
import com.tim.location.MapProvider
import com.arkivanov.decompose.value.Value

interface CommonMapMain {

    val models: Value<Model>

    fun onMapClick(location: Location)

    fun onButtonClick()

    data class Model(
        val provider: MapProvider = MapProvider.Google,
        val location: Location? = null,
        val isRunning: Boolean = false
    ) {
        val isButtonEnabled: Boolean
            get() = location != null
    }
}
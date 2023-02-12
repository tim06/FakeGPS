package com.tim.fakegps.feature.map.common.commondata

import com.tim.location.Location
import com.tim.location.MapProvider
import kotlinx.coroutines.flow.Flow

interface CommonMapMain {

    val models: Flow<Model>

    fun onLocationSelected(location: Location)

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
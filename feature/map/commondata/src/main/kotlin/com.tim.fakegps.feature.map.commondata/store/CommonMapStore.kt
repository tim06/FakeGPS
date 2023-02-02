package com.tim.fakegps.feature.map.commondata.store

import com.arkivanov.mvikotlin.core.store.Store
import com.tim.fakegps.feature.map.commondata.store.CommonMapStore.State
import com.tim.fakegps.feature.map.commondata.store.CommonMapStore.Intent
import com.tim.location.Location
import com.tim.location.MapProvider

internal interface CommonMapStore : Store<Intent, State, Nothing> {
    sealed class Intent {
        data class OnLocationUpdated(val location: Location) : Intent()
        object OnButtonClick : Intent()
    }

    data class State(
        val provider: MapProvider = MapProvider.Google,
        val location: Location? = null,
        val isRunning: Boolean = false
    )
}
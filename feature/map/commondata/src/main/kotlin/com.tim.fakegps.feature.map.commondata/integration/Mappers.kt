package com.tim.fakegps.feature.map.commondata.integration

import com.tim.fakegps.feature.map.commondata.CommonMapMain.Model
import com.tim.fakegps.feature.map.commondata.store.CommonMapStore.State

internal val stateToModel: (State) -> Model = {
    Model(
        provider = it.provider,
        location = it.location,
        isRunning = it.isRunning
    )
}
package com.tim.fakegps.feature.map.common.commondata.integration

import com.tim.fakegps.feature.map.common.commondata.CommonMapMain.Model
import com.tim.fakegps.feature.map.common.commondata.store.CommonMapStore.State

internal val stateToModel: (State) -> Model = {
    Model(
        provider = it.provider,
        location = it.location,
        isRunning = it.isRunning
    )
}
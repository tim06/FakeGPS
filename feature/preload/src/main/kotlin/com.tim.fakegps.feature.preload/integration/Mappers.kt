package com.tim.fakegps.feature.preload.integration

import com.tim.fakegps.feature.preload.PreloadMain
import com.tim.fakegps.feature.preload.store.PreloadStore

internal val stateToModel: (PreloadStore.State) -> PreloadMain.Model = {
    PreloadMain.Model(
        isLoading = it.isLoading,
        hasMockPermission = it.isMockPermissionGranted
    )
}
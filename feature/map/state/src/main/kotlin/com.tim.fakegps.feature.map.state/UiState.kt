package com.tim.fakegps.feature.map.state

import android.location.Location

data class UiState(
    val isPermissionsGranted: Boolean = false,
    val deviceLocation: Location? = null,
    val fakeLocation: Coordinates? = null,
    val isRunning: Boolean = false
) {
    val isInitialMoveToDevicePositionAllowed: Boolean
        get() = isPermissionsGranted && isRunning.not()

}
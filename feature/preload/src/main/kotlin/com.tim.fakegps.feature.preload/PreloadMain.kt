package com.tim.fakegps.feature.preload

import kotlinx.coroutines.flow.Flow

interface PreloadMain {

    val models: Flow<Model>

    fun onPermissionGranted()

    fun checkMockPermission()

    data class Model(
        val isLoading: Boolean = false,
        val hasMockPermission: Boolean = true
    )
}
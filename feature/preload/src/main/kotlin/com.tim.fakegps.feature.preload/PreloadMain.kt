package com.tim.fakegps.feature.preload

import com.arkivanov.decompose.value.Value

interface PreloadMain {

    val models: Value<Model>

    fun onPermissionGranted()

    fun checkMockPermission()

    data class Model(
        val isLoading: Boolean = true,
        val hasMockPermission: Boolean = false
    )
}
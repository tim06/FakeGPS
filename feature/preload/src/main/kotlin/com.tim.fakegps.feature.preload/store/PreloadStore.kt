package com.tim.fakegps.feature.preload.store

import com.arkivanov.mvikotlin.core.store.Store
import com.tim.fakegps.feature.preload.store.PreloadStore.Intent
import com.tim.fakegps.feature.preload.store.PreloadStore.State

internal interface PreloadStore : Store<Intent, State, Nothing> {

    sealed class Intent {
        object CheckMockPermission : Intent()
    }

    data class State(
        val isLoading: Boolean = false,
        val isMockPermissionGranted: Boolean = false
    )
}
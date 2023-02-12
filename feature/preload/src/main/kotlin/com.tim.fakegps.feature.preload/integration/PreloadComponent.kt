package com.tim.fakegps.feature.preload.integration

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.states
import com.tim.fakegps.core.permission.PermissionChecker
import com.tim.fakegps.feature.preload.PreloadMain
import com.tim.fakegps.feature.preload.PreloadMain.Model
import com.tim.fakegps.feature.preload.store.PreloadStore.Intent
import com.tim.fakegps.feature.preload.store.PreloadStoreProvider
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach

class PreloadComponent(
    componentContext: ComponentContext,
    storeFactory: StoreFactory,
    permissionChecker: PermissionChecker,
    private val output: () -> Unit
) : PreloadMain, ComponentContext by componentContext {

    private val store = instanceKeeper.getStore {
        PreloadStoreProvider(
            storeFactory = storeFactory, permissionChecker = permissionChecker
        ).provide()
    }

    override val models: Flow<Model> = store.states.map(stateToModel).onEach {
            if (it.hasMockPermission) {
                onPermissionGranted()
            }
        }

    override fun onPermissionGranted() {
        output.invoke()
    }

    override fun checkMockPermission() {
        store.accept(Intent.CheckMockPermission)
    }
}
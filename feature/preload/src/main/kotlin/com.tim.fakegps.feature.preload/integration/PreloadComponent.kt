package com.tim.fakegps.feature.preload.integration

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.Value
import com.arkivanov.decompose.value.operator.map
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.tim.fakegps.core.permission.PermissionChecker
import com.tim.fakegps.feature.preload.PreloadMain
import com.tim.fakegps.feature.preload.asValue
import com.tim.fakegps.feature.preload.store.PreloadStore.Intent
import com.tim.fakegps.feature.preload.store.PreloadStoreProvider

class PreloadComponent(
    componentContext: ComponentContext,
    storeFactory: StoreFactory,
    permissionChecker: PermissionChecker,
    private val output: () -> Unit
) : PreloadMain, ComponentContext by componentContext {

    private val store =
        instanceKeeper.getStore {
            PreloadStoreProvider(
                storeFactory = storeFactory,
                permissionChecker = permissionChecker
            ).provide()
        }

    override val models: Value<PreloadMain.Model> = store.asValue().map(stateToModel)

    override fun onPermissionGranted() {
        output.invoke()
    }

    override fun checkMockPermission() {
        store.accept(Intent.CheckMockPermission)
    }
}
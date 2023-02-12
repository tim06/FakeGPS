package com.tim.fakegps.feature.map.common.commondata.integration

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.states
import com.tim.fakegps.feature.fakelocationprovider.FakeLocationProvider
import com.tim.fakegps.feature.map.common.commondata.CommonMapMain
import com.tim.fakegps.feature.map.common.commondata.CommonMapMain.Model
import com.tim.fakegps.feature.map.common.commondata.store.CommonMapStore.Intent
import com.tim.fakegps.feature.map.common.commondata.store.CommonMapStoreProvider
import com.tim.feature.gmschecker.GmsChecker
import com.tim.location.Location
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class CommonMapComponent(
    componentContext: ComponentContext,
    storeFactory: StoreFactory,
    gmsChecker: GmsChecker,
    fakeLocationProvider: FakeLocationProvider
) : CommonMapMain, ComponentContext by componentContext {

    private val store = instanceKeeper.getStore {
        CommonMapStoreProvider(
            storeFactory = storeFactory,
            gmsChecker = gmsChecker,
            fakeLocationProvider = fakeLocationProvider
        ).provide()
    }

    override val models: Flow<Model> = store.states.map(stateToModel)

    override fun onLocationSelected(location: Location) {
        store.accept(Intent.OnLocationUpdated(location = location))
    }

    override fun onButtonClick() {
        store.accept(Intent.OnButtonClick)
    }
}
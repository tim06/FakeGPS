package com.tim.fakegps.feature.map.commondata.integration

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.Value
import com.arkivanov.decompose.value.operator.map
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.tim.fakegps.feature.locationprovider.FakeLocationProvider
import com.tim.fakegps.feature.map.commondata.CommonMapMain
import com.tim.fakegps.feature.map.commondata.CommonMapMain.Model
import com.tim.fakegps.feature.map.commondata.asValue
import com.tim.fakegps.feature.map.commondata.store.CommonMapStore.Intent
import com.tim.fakegps.feature.map.commondata.store.CommonMapStoreProvider
import com.tim.feature.gmschecker.GmsChecker
import com.tim.location.Location

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

    override val models: Value<Model> = store.asValue().map(stateToModel)

    override fun onMapClick(location: Location) {
        store.accept(Intent.OnLocationUpdated(location = location))
    }

    override fun onButtonClick() {
        store.accept(Intent.OnButtonClick)
    }
}
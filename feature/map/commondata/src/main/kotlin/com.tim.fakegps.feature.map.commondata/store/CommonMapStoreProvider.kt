package com.tim.fakegps.feature.map.commondata.store

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.tim.fakegps.feature.map.commondata.store.CommonMapStore.State
import com.tim.fakegps.feature.map.commondata.store.CommonMapStore.Intent
import com.tim.fakegps.feature.locationprovider.FakeLocationProvider
import com.tim.feature.gmschecker.GmsChecker
import com.tim.location.Location
import com.tim.location.MapProvider


internal class CommonMapStoreProvider(
    private val storeFactory: StoreFactory,
    private val gmsChecker: GmsChecker,
    private val fakeLocationProvider: FakeLocationProvider
) {
    fun provide(): CommonMapStore =
        object : CommonMapStore, Store<Intent, State, Nothing> by storeFactory.create(
            name = "MapCommonStore",
            initialState = State(),
            bootstrapper = SimpleBootstrapper(Unit),
            executorFactory = ::ExecutorImpl,
            reducer = ReducerImpl
        ) {}

    private sealed class Msg {
        data class ProviderUpdated(val provider: MapProvider) : Msg()
        data class LocationUpdated(val location: Location) : Msg()
        data class FakeLocationStateChanged(val running: Boolean) : Msg()
    }

    private inner class ExecutorImpl : CoroutineExecutor<Intent, Unit, State, Msg, Nothing>() {
        override fun executeAction(action: Unit, getState: () -> State) {
            val provider = if (gmsChecker.isPlayServicesAvailable()) MapProvider.Google else MapProvider.Yandex
            dispatch(Msg.ProviderUpdated(provider))
        }

        override fun executeIntent(intent: Intent, getState: () -> State): Unit = when (intent) {
            is Intent.OnLocationUpdated -> locationSelected(intent.location)
            is Intent.OnButtonClick -> buttonClick(state = getState())
        }

        override fun dispose() {
            super.dispose()
            fakeLocationProvider.clear()
        }

        private fun locationSelected(location: Location) {
            dispatch(Msg.LocationUpdated(location = location))
        }

        private fun buttonClick(state: State) {
            val result = if (state.isRunning) {
                fakeLocationProvider.stopFakeLocation()
                false
            } else {
                state.location?.let {
                    fakeLocationProvider.startFakeLocation(it)
                    true
                } ?: false
            }
            dispatch(Msg.FakeLocationStateChanged(result))
        }
    }

    private object ReducerImpl : Reducer<State, Msg> {
        override fun State.reduce(msg: Msg): State = when (msg) {
            is Msg.LocationUpdated -> copy(location = msg.location)
            is Msg.FakeLocationStateChanged -> copy(isRunning = msg.running)
            is Msg.ProviderUpdated -> copy(provider = msg.provider)
        }
    }
}
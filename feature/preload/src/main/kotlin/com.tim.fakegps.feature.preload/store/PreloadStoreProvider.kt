package com.tim.fakegps.feature.preload.store

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.tim.fakegps.core.permission.PermissionChecker
import com.tim.fakegps.feature.preload.store.PreloadStore.Intent
import com.tim.fakegps.feature.preload.store.PreloadStore.State

internal class PreloadStoreProvider(
    private val storeFactory: StoreFactory, private val permissionChecker: PermissionChecker
) {

    fun provide(): PreloadStore =
        object : PreloadStore, Store<Intent, State, Nothing> by storeFactory.create(
            name = "PreloadStore",
            initialState = State(),
            bootstrapper = SimpleBootstrapper(Unit),
            executorFactory = ::ExecutorImpl,
            reducer = ReducerImpl
        ) {}

    private sealed class Msg {
        object LoadingStarted : Msg()
        data class MockPermissionStatusChanged(val granted: Boolean) : Msg()
    }

    private inner class ExecutorImpl : CoroutineExecutor<Intent, Unit, State, Msg, Nothing>() {
        override fun executeAction(action: Unit, getState: () -> State) {
            checkMockPermission()
        }

        override fun executeIntent(intent: Intent, getState: () -> State) = when (intent) {
            is Intent.CheckMockPermission -> checkMockPermission()
        }

        private fun checkMockPermission() {
            dispatch(Msg.LoadingStarted)
            val current = permissionChecker.isMockLocationEnabled()
            dispatch(Msg.MockPermissionStatusChanged(current))
        }
    }

    private object ReducerImpl : Reducer<State, Msg> {
        override fun State.reduce(msg: Msg): State = when (msg) {
            is Msg.LoadingStarted -> copy(isLoading = true)
            is Msg.MockPermissionStatusChanged -> copy(
                isMockPermissionGranted = msg.granted, isLoading = false
            )
        }
    }
}
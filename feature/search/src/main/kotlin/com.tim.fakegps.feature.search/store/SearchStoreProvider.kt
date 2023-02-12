package com.tim.fakegps.feature.search.store

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.tim.fakegps.feature.geocoder.Geocoder
import com.tim.fakegps.feature.geocoder.model.LocationAddress
import com.tim.fakegps.feature.search.store.SearchStore.Intent
import com.tim.fakegps.feature.search.store.SearchStore.State
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.filterNot
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.withContext

internal class SearchStoreProvider(
    private val storeFactory: StoreFactory, private val geocoder: Geocoder
) {
    fun provide(): SearchStore =
        object : SearchStore, Store<Intent, State, Nothing> by storeFactory.create(
            name = "SearchStore",
            initialState = State(),
            bootstrapper = SimpleBootstrapper(Unit),
            executorFactory = ::ExecutorImpl,
            reducer = ReducerImpl
        ) {}

    private sealed class Msg {
        data class LoadingChanged(val isLoading: Boolean) : Msg()
        data class QueryChanged(val query: String) : Msg()
        data class SelectSuggestion(val location: LocationAddress) : Msg()
        data class SuggestionsLoaded(val suggestions: List<LocationAddress>) : Msg()
    }

    private inner class ExecutorImpl : CoroutineExecutor<Intent, Unit, State, Msg, Nothing>() {
        private val searchQuery = MutableStateFlow("")
        override fun executeAction(action: Unit, getState: () -> State) {
            searchQuery.filterNot { it.isEmpty() }.debounce(500)
                .onEach { dispatch(Msg.LoadingChanged(true)) }.map { query ->
                    withContext(Dispatchers.IO) {
                        geocoder.getLocationForName(query)
                    }
                }.onEach { suggestions ->
                    dispatch(Msg.SuggestionsLoaded(suggestions))
                }.stateIn(
                    scope = scope, started = SharingStarted.Eagerly, initialValue = emptyList()
                )
        }

        override fun executeIntent(intent: Intent, getState: () -> State) = when (intent) {
            is Intent.Clear -> clear()
            is Intent.QueryChanged -> queryChanged(query = intent.query)
            is Intent.SuggestionClick -> suggestionsClick(location = intent.location)
        }

        private fun clear() {
            searchQuery.update { "" }
            dispatch(Msg.QueryChanged(""))
        }

        private fun queryChanged(query: String) {
            dispatch(Msg.QueryChanged(query))
            searchQuery.update { query }
        }

        private fun suggestionsClick(location: LocationAddress) {
            dispatch(Msg.SelectSuggestion(location))
        }
    }

    private object ReducerImpl : Reducer<State, Msg> {
        override fun State.reduce(msg: Msg): State = when (msg) {
            is Msg.LoadingChanged -> copy(isLoading = msg.isLoading)
            is Msg.QueryChanged -> copy(query = msg.query)
            is Msg.SelectSuggestion -> copy(
                query = msg.location.name, selected = msg.location, suggestions = emptyList()
            )

            is Msg.SuggestionsLoaded -> copy(suggestions = msg.suggestions, isLoading = false)
        }
    }
}
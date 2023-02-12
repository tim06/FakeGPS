package com.tim.fakegps.feature.search.store

import com.arkivanov.mvikotlin.core.store.Store
import com.tim.fakegps.feature.geocoder.model.LocationAddress
import com.tim.fakegps.feature.search.store.SearchStore.State
import com.tim.fakegps.feature.search.store.SearchStore.Intent

internal interface SearchStore : Store<Intent, State, Nothing> {

    sealed class Intent {
        data class QueryChanged(val query: String) : Intent()
        data class SuggestionClick(val location: LocationAddress) : Intent()
        object Clear : Intent()
    }

    data class State(
        val isLoading: Boolean = false,
        val query: String = "",
        val selected: LocationAddress? = null,
        val suggestions: List<LocationAddress> = emptyList()
    )
}
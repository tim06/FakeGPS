package com.tim.fakegps.feature.search.integration

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.states
import com.tim.fakegps.feature.geocoder.Geocoder
import com.tim.fakegps.feature.geocoder.model.LocationAddress
import com.tim.fakegps.feature.search.Search
import com.tim.fakegps.feature.search.Search.Model
import com.tim.fakegps.feature.search.store.SearchStore.Intent
import com.tim.fakegps.feature.search.store.SearchStoreProvider
import com.tim.location.Location
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapNotNull

class SearchComponent(
    componentContext: ComponentContext,
    storeFactory: StoreFactory,
    geocoder: Geocoder,
    private val onLocationPick: (Location) -> Unit
) : Search, ComponentContext by componentContext {

    private val store = instanceKeeper.getStore {
        SearchStoreProvider(
            storeFactory = storeFactory, geocoder = geocoder
        ).provide()
    }

    override val models: Flow<Model> = store.states.map(stateToModel)

    override val selectedSuggestion: Flow<LocationAddress> =
        store.states.map(stateToModel).mapNotNull { it.selectedSuggestion }

    override fun onQueryChanged(query: String) {
        store.accept(Intent.QueryChanged(query))
    }

    override fun onSuggestionClick(location: LocationAddress) {
        store.accept(Intent.SuggestionClick(location))
        onLocationPick.invoke(location.location)
    }

    override fun onClear() {
        store.accept(Intent.Clear)
    }
}
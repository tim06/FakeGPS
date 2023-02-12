package com.tim.fakegps.feature.search

import com.tim.fakegps.feature.geocoder.model.LocationAddress
import kotlinx.coroutines.flow.Flow

interface Search {

    val models: Flow<Model>

    val selectedSuggestion: Flow<LocationAddress>

    fun onQueryChanged(query: String)

    fun onSuggestionClick(location: LocationAddress)

    fun onClear()

    data class Model(
        val isLoading: Boolean = false,
        val query: String = "",
        val selectedSuggestion: LocationAddress? = null,
        val suggestions: List<LocationAddress> = emptyList()
    )
}
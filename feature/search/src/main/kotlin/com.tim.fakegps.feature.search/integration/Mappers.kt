package com.tim.fakegps.feature.search.integration

import com.tim.fakegps.feature.search.Search.Model
import com.tim.fakegps.feature.search.store.SearchStore.State

internal val stateToModel: (State) -> Model = {
    Model(
        isLoading = it.isLoading,
        query = it.query,
        selectedSuggestion = it.selected,
        suggestions = it.suggestions
    )
}
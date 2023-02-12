package com.tim.fakegps.feature.map.mapmain

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.tim.fakegps.feature.fakelocationprovider.FakeLocationProvider
import com.tim.fakegps.feature.geocoder.Geocoder
import com.tim.fakegps.feature.map.common.commondata.CommonMapMain
import com.tim.fakegps.feature.map.common.commondata.integration.CommonMapComponent
import com.tim.fakegps.feature.search.Search
import com.tim.fakegps.feature.search.integration.SearchComponent
import com.tim.feature.gmschecker.GmsChecker
import com.tim.location.Location

class MapMainComponentImpl(
    componentContext: ComponentContext,
    storeFactory: StoreFactory,
    gmsChecker: GmsChecker,
    fakeLocationProvider: FakeLocationProvider,
    geocoder: Geocoder
) : ComponentContext by componentContext, MapMainComponent {
    override val commonMapComponent: CommonMapMain = CommonMapComponent(
        componentContext = componentContext,
        storeFactory = storeFactory,
        gmsChecker = gmsChecker,
        fakeLocationProvider = fakeLocationProvider
    )
    override val searchComponent: Search = SearchComponent(
        componentContext = componentContext,
        storeFactory = storeFactory,
        geocoder = geocoder,
        onLocationPick = ::onSuggestionLocationClick
    )

    override val isSearchPresent: Boolean = geocoder.isPresent()

    private fun onSuggestionLocationClick(location: Location) {
        commonMapComponent.onLocationSelected(location)
    }
}
package com.tim.fakegps.feature.map.mapmain

import com.tim.fakegps.feature.map.common.commondata.CommonMapMain
import com.tim.fakegps.feature.search.Search

interface MapMainComponent {
    val commonMapComponent: CommonMapMain
    val isSearchPresent: Boolean
    val searchComponent: Search
}
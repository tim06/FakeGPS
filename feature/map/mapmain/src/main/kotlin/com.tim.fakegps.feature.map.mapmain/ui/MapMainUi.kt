package com.tim.fakegps.feature.map.mapmain.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.tim.fakegps.feature.map.common.commonui.CommonMapContent
import com.tim.fakegps.feature.map.mapmain.MapMainComponent
import com.tim.fakegps.feature.search.SearchField

@Composable
fun MapMainUi(component: MapMainComponent) {
    Box(modifier = Modifier.fillMaxSize()) {
        CommonMapContent(component = component.commonMapComponent)
        if (component.isSearchPresent) {
            SearchField(component = component.searchComponent)
        }
    }
}
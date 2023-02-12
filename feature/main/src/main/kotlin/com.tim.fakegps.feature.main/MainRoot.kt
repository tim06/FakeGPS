package com.tim.fakegps.feature.main

import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value
import com.tim.fakegps.feature.map.mapmain.MapMainComponent
import com.tim.fakegps.feature.preload.PreloadMain

interface MainRoot {

    val childStack: Value<ChildStack<*, Child>>

    sealed class Child {
        data class Preload(val component: PreloadMain) : Child()
        data class CommonParentMap(val component: MapMainComponent) : Child()
    }
}
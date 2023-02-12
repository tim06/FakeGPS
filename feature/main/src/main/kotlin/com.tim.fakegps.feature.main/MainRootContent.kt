package com.tim.fakegps.feature.main

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.arkivanov.decompose.extensions.compose.jetpack.stack.Children
import com.arkivanov.decompose.extensions.compose.jetpack.stack.animation.fade
import com.arkivanov.decompose.extensions.compose.jetpack.stack.animation.plus
import com.arkivanov.decompose.extensions.compose.jetpack.stack.animation.scale
import com.arkivanov.decompose.extensions.compose.jetpack.stack.animation.stackAnimation
import com.tim.fakegps.feature.map.mapmain.ui.MapMainUi
import com.tim.fakegps.feature.preload.PreloadContent

@Composable
fun MainRootContent(component: MainRoot) {
    Children(
        stack = component.childStack,
        animation = stackAnimation(fade() + scale()),
    ) {
        val instance by remember { mutableStateOf(it.instance) }
        when (val child = instance) {
            is MainRoot.Child.Preload -> PreloadContent(child.component)
            is MainRoot.Child.CommonParentMap -> MapMainUi(child.component)
        }
    }
}
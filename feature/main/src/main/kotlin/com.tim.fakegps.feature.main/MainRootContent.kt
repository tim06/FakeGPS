package com.tim.fakegps.feature.main

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.arkivanov.decompose.ExperimentalDecomposeApi
import com.arkivanov.decompose.extensions.compose.jetpack.stack.Children
import com.tim.fakegps.feature.map.commonui.CommonMapContent
import com.tim.fakegps.feature.preload.PreloadContent

@OptIn(ExperimentalDecomposeApi::class)
@Composable
fun MainRootContent(component: MainRoot) {
    Children(
        stack = component.childStack,
        //animation = stackAnimation(fade() + scale()),
    ) {
        val instance by remember { mutableStateOf(it.instance) }
        when (val child = instance) {
            is MainRoot.Child.Preload -> PreloadContent(child.component)
            is MainRoot.Child.CommonMap -> CommonMapContent(child.component)
        }
    }
}
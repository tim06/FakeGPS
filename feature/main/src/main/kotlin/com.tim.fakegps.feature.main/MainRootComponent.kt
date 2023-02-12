package com.tim.fakegps.feature.main

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.replaceCurrent
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.tim.fakegps.core.permission.PermissionChecker
import com.tim.fakegps.feature.fakelocationprovider.FakeLocationProvider
import com.tim.fakegps.feature.geocoder.Geocoder
import com.tim.fakegps.feature.map.mapmain.MapMainComponent
import com.tim.fakegps.feature.map.mapmain.MapMainComponentImpl
import com.tim.fakegps.feature.preload.PreloadMain
import com.tim.fakegps.feature.preload.integration.PreloadComponent
import com.tim.feature.gmschecker.GmsChecker
import kotlinx.parcelize.Parcelize

class MainRootComponent internal constructor(
    componentContext: ComponentContext,
    private val preloadMain: (ComponentContext, () -> Unit) -> PreloadMain,
    private val commonMapMain: (ComponentContext) -> MapMainComponent
) : MainRoot, ComponentContext by componentContext {

    constructor(
        componentContext: ComponentContext,
        storeFactory: StoreFactory,
        gmsChecker: GmsChecker,
        permissionChecker: PermissionChecker,
        fakeLocationProvider: FakeLocationProvider,
        geocoder: Geocoder
    ) : this(componentContext = componentContext, preloadMain = { childContext, output ->
        PreloadComponent(
            componentContext = childContext,
            storeFactory = storeFactory,
            permissionChecker = permissionChecker,
            output = output
        )
    }, commonMapMain = { childContext ->
        MapMainComponentImpl(
            componentContext = childContext,
            storeFactory = storeFactory,
            gmsChecker = gmsChecker,
            fakeLocationProvider = fakeLocationProvider,
            geocoder = geocoder
        )
    })

    private val navigation = StackNavigation<Configuration>()

    private val stack = childStack(
        source = navigation,
        initialConfiguration = Configuration.Preload,
        handleBackButton = true,
        childFactory = ::createChild
    )

    override val childStack: Value<ChildStack<*, MainRoot.Child>> = stack

    private fun createChild(
        configuration: Configuration, componentContext: ComponentContext
    ): MainRoot.Child = when (configuration) {
        is Configuration.Preload -> MainRoot.Child.Preload(preloadMain(componentContext, ::onPreloadOutput))
        is Configuration.CommonParentMap -> MainRoot.Child.CommonParentMap(commonMapMain(componentContext))
    }

    private fun onPreloadOutput() {
        navigation.replaceCurrent(Configuration.CommonParentMap)
    }

    private sealed class Configuration : Parcelable {
        @Parcelize
        object Preload : Configuration()
        @Parcelize
        object CommonParentMap : Configuration()
    }
}

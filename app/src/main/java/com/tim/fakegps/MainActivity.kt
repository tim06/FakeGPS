package com.tim.fakegps

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.defaultComponentContext
import com.arkivanov.mvikotlin.logging.store.LoggingStoreFactory
import com.arkivanov.mvikotlin.timetravel.store.TimeTravelStoreFactory
import com.tim.fakegps.core.permission.PermissionChecker
import com.tim.fakegps.feature.fakelocationprovider.FakeLocationProvider
import com.tim.fakegps.feature.geocoder.Geocoder
import com.tim.fakegps.feature.main.MainRoot
import com.tim.fakegps.feature.main.MainRootComponent
import com.tim.fakegps.feature.main.MainRootContent
import com.tim.fakegps.ui.theme.FakeGPSTheme
import com.tim.feature.gmschecker.GmsChecker
import com.yandex.mapkit.MapKitFactory
import org.koin.android.ext.android.inject

class MainActivity : ComponentActivity() {

    private val gmsChecker: GmsChecker by inject()
    private val fakeLocationProvider: FakeLocationProvider by inject()
    private val permissionChecker: PermissionChecker by inject()
    private val geocoder: Geocoder by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (gmsChecker.isPlayServicesAvailable().not()) {
            MapKitFactory.initialize(this)
        }

        val root = todoRoot(defaultComponentContext())

        setContent {
            FakeGPSTheme {
                MainRootContent(root)
            }
        }
    }

    private fun todoRoot(componentContext: ComponentContext): MainRoot = MainRootComponent(
        componentContext = componentContext,
        storeFactory = LoggingStoreFactory(TimeTravelStoreFactory()),
        gmsChecker = gmsChecker,
        permissionChecker = permissionChecker,
        fakeLocationProvider = fakeLocationProvider,
        geocoder = geocoder
    )
}
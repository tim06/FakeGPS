package com.tim.fakegps

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.tim.fakegps.feature.main.MainScreen
import com.tim.fakegps.ui.theme.FakeGPSTheme
import com.tim.feature.gmschecker.GmsChecker
import com.yandex.mapkit.MapKitFactory
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var gmsChecker: GmsChecker

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (gmsChecker.isPlayServicesAvailable().not()) {
            MapKitFactory.initialize(this)
        }
        setContent {
            FakeGPSTheme {
                MainScreen()
            }
        }
    }
}
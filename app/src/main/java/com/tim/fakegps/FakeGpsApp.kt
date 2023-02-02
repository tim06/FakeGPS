package com.tim.fakegps

import android.app.Application
import com.tim.fakegps.core.permission.di.permissionsModule
import com.tim.fakegps.di.appModule
import com.tim.fakegps.feature.locationprovider.di.fakeLocationProviderModule
import com.tim.feature.gmschecker.di.gmsCheckerModule
import com.yandex.mapkit.MapKitFactory
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class FakeGpsApp : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@FakeGpsApp)
            modules(listOf(appModule, gmsCheckerModule, fakeLocationProviderModule, permissionsModule))
        }

        MapKitFactory.setApiKey(BuildConfig.YANDEX_MAPS_API_KEY)
    }
}
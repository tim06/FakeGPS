package com.tim.fakegps.di

import android.content.Context
import android.location.LocationManager
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val appModule = module {
    single<LocationManager> { androidApplication().getSystemService(Context.LOCATION_SERVICE) as LocationManager }
    single<FusedLocationProviderClient> {
        LocationServices.getFusedLocationProviderClient(
            androidApplication()
        )
    }
}

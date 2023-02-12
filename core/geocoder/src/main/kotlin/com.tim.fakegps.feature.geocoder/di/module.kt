package com.tim.fakegps.feature.geocoder.di

import com.tim.fakegps.feature.geocoder.Geocoder
import com.tim.fakegps.feature.geocoder.GoogleApiGeocoder
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module
import java.util.Locale

val geocoderProviderModule = module {
    single { android.location.Geocoder(androidApplication(), Locale.getDefault()) }
    single<Geocoder> { GoogleApiGeocoder(get()) }
}
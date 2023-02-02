package com.tim.fakegps.feature.locationprovider.di

import com.tim.fakegps.feature.locationprovider.FakeLocationProvider
import org.koin.dsl.module

val fakeLocationProviderModule = module {
    single { FakeLocationProvider(get()) }
}
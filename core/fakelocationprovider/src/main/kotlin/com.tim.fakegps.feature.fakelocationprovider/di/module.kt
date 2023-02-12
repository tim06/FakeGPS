package com.tim.fakegps.feature.fakelocationprovider.di

import com.tim.fakegps.feature.fakelocationprovider.FakeLocationProvider
import org.koin.dsl.module

val fakeLocationProviderModule = module {
    single { FakeLocationProvider(get()) }
}
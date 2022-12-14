package com.tim.fakegps.feature.locationprovider

import android.location.LocationManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocationProviderModule {
    @Provides
    @Singleton
    fun provideFakeLocation(locationManager: LocationManager): FakeLocationProvider {
        return FakeLocationProvider(locationManager)
    }
}
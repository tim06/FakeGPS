package com.tim.feature.gmschecker.di

import android.content.Context
import com.google.android.gms.common.GoogleApiAvailability
import com.tim.feature.gmschecker.GmsChecker
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CheckerProvider {
    @Provides
    @Singleton
    fun provideGoogleApiAvailability(): GoogleApiAvailability {
        return GoogleApiAvailability.getInstance()
    }

    @Provides
    @Singleton
    fun provideGmsChecker(@ApplicationContext context: Context, googleApiAvailability: GoogleApiAvailability): GmsChecker {
        return GmsChecker(context, googleApiAvailability)
    }
}
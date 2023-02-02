package com.tim.feature.gmschecker.di

import com.google.android.gms.common.GoogleApiAvailability
import com.tim.feature.gmschecker.GmsChecker
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val gmsCheckerModule = module {
    single { GoogleApiAvailability.getInstance() }
    single { GmsChecker(androidApplication(), get()) }
}
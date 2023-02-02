package com.tim.fakegps.core.permission.di

import android.app.AppOpsManager
import android.content.Context
import com.tim.fakegps.core.permission.PermissionChecker
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val permissionsModule = module {
    single<AppOpsManager> { androidApplication().getSystemService(Context.APP_OPS_SERVICE) as AppOpsManager }
    single { PermissionChecker(androidApplication(), get()) }
}

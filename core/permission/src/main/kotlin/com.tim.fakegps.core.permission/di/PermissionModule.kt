package com.tim.fakegps.core.permission.di

import android.app.AppOpsManager
import android.content.Context
import com.tim.fakegps.core.permission.PermissionChecker
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PermissionModule {

    @Provides
    @Singleton
    fun providePermissionChecker(
        @ApplicationContext context: Context, appOpsManager: AppOpsManager
    ): PermissionChecker {
        return PermissionChecker(context, appOpsManager)
    }
}
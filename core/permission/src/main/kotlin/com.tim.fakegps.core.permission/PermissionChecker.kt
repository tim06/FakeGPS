package com.tim.fakegps.core.permission

import android.app.AppOpsManager
import android.content.Context
import android.os.Build
import dagger.hilt.android.qualifiers.ApplicationContext

class PermissionChecker(
    @ApplicationContext private val context: Context, private val appOpsManager: AppOpsManager
) {
    fun isMockLocationEnabled(): Boolean {
        return try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                (appOpsManager.checkOp(
                    AppOpsManager.OPSTR_MOCK_LOCATION,
                    android.os.Process.myUid(),
                    context.packageName
                ) == AppOpsManager.MODE_ALLOWED)
            } else {
                !android.provider.Settings.Secure.getString(
                    context.contentResolver, "mock_location"
                ).equals("0")
            }
        } catch (e: Exception) {
            return false
        }
    }
}
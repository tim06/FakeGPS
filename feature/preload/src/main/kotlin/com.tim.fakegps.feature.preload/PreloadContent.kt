package com.tim.fakegps.feature.preload

import android.content.Intent
import android.provider.Settings
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.arkivanov.decompose.extensions.compose.jetpack.subscribeAsState
import com.tim.fakegps.core.lifecycle.OnResumeEvent

@Composable
fun PreloadContent(component: PreloadMain) {
    val state by component.models.subscribeAsState()
    LaunchedEffect(key1 = state.hasMockPermission) {
        if (state.hasMockPermission) {
            component.onPermissionGranted()
        }
    }
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        if (state.isLoading) {
            CircularProgressIndicator()
        } else if (state.hasMockPermission.not()) {
            PermissionNotGranted()
        }
    }

    OnResumeEvent(component::checkMockPermission)
}

@Composable
private fun PermissionNotGranted() {
    val context = LocalContext.current
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = "Mock permission is not granted")
        Button(onClick = {
            val isDevelopersModeEnabled = Settings.Secure.getInt(
                context.contentResolver, Settings.Global.DEVELOPMENT_SETTINGS_ENABLED, 0
            ) != 0
            if (isDevelopersModeEnabled) {
                context.startActivity(
                    Intent(Settings.ACTION_APPLICATION_DEVELOPMENT_SETTINGS), null
                )
            } else {
                context.startActivity(Intent(Settings.ACTION_DEVICE_INFO_SETTINGS), null)
            }
        }) {
            Text(text = "Grant permission")
        }
    }
}
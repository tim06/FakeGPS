package com.tim.fakegps.feature.preload

import android.content.Intent
import android.provider.Settings
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.tim.fakegps.core.lifecycle.OnResumeEvent
import com.tim.fakegps.feature.preload.PreloadMain.Model

@Composable
fun PreloadContent(component: PreloadMain) {
    val state by component.models.collectAsState(initial = Model())
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background)
    ) {
        AnimatedVisibility(
            modifier = Modifier.align(Alignment.Center), visible = state.isLoading
        ) {
            CircularProgressIndicator()
        }
        AnimatedVisibility(
            visible = state.hasMockPermission.not()
        ) {
            PermissionNotGranted()
        }
    }

    OnResumeEvent(component::checkMockPermission)
}

@Composable
private fun PermissionNotGranted(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    Box(modifier = modifier.fillMaxSize()) {
        Text(
            modifier = Modifier.align(Alignment.Center),
            text = "Mock permission is not granted",
            color = MaterialTheme.colorScheme.onBackground
        )
        Button(modifier = Modifier
            .align(Alignment.BottomCenter)
            .fillMaxWidth()
            .padding(horizontal = 16.dp), onClick = {
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
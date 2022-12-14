package com.tim.fakegps.feature.main

import android.content.Context
import android.content.Intent
import android.provider.Settings
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.startActivity
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.tim.fakegps.core.ds.FakeGpsToolbar
import com.tim.fakegps.core.lifecycle.OnLifecycleEvent
import com.tim.fakegps.core.permission.LocationPermissions
import com.tim.fakegps.feature.map.google.FakeGpsGoogleMap
import com.tim.fakegps.feature.map.yandex.FakeGpsYandexMap
import kotlinx.coroutines.launch

@Suppress("MissingPermission")
@OptIn(
    ExperimentalMaterial3Api::class, ExperimentalLifecycleComposeApi::class
)
@Composable
fun MainScreen(
    viewModel: MapViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LocationPermissions { result -> viewModel.onPermissionsResult(result) }

    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(snackbarHost = { SnackbarHost(snackbarHostState) }) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)) {
            if (viewModel.isPlayServicesAvailable()) {
                FakeGpsGoogleMap(uiState = uiState) { latLng ->
                    viewModel.onMapClicked(latLng)
                }
            } else {
                FakeGpsYandexMap(uiState = uiState) { coordinates ->
                    viewModel.onMapClicked(coordinates)
                }
            }

            IconButton(modifier = Modifier
                .padding(16.dp)
                .padding(bottom = 16.dp)
                .size(56.dp)
                .align(Alignment.BottomStart)
                .background(
                    color = MaterialTheme.colorScheme.primaryContainer,
                    shape = RoundedCornerShape(10.dp)
                ),
                enabled = uiState.fakeLocation != null,
                onClick = { viewModel.onStartStopButtonClick() }) {
                Icon(
                    imageVector = if (uiState.isRunning) Icons.Default.Close else Icons.Default.PlayArrow,
                    contentDescription = if (uiState.isRunning) "Stop fake location" else "Start fake location"
                )
            }
        }
    }

    OnLifecycleEvent { _, event ->
        when (event) {
            Lifecycle.Event.ON_RESUME -> {
                if (viewModel.isApplicationFakeDataAllowed().not()) {
                    coroutineScope.launch {
                        snackbarHostState.showApplicationPermissionError(context)
                    }
                }
            }

            Lifecycle.Event.ON_DESTROY -> {
                viewModel.onStartStopButtonClick()
            }

            else -> {}
        }
    }
}

suspend fun SnackbarHostState.showApplicationPermissionError(context: Context) {
    val result = showSnackbar(
        message = "Application is not selected in developers settings",
        actionLabel = "Allow",
        duration = SnackbarDuration.Indefinite
    )
    if (result == SnackbarResult.ActionPerformed) {
        val isDevelopersModeEnabled = Settings.Secure.getInt(
            context.contentResolver, Settings.Global.DEVELOPMENT_SETTINGS_ENABLED, 0
        ) != 0
        if (isDevelopersModeEnabled) {
            startActivity(context, Intent(Settings.ACTION_APPLICATION_DEVELOPMENT_SETTINGS), null)
        } else {
            startActivity(context, Intent(Settings.ACTION_DEVICE_INFO_SETTINGS), null)
        }
    }
}
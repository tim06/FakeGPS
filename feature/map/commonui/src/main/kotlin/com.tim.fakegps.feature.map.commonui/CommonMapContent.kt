package com.tim.fakegps.feature.map.commonui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.jetpack.subscribeAsState
import com.tim.fakegps.feature.map.commondata.CommonMapMain
import com.tim.fakegps.feature.map.google.GoogleContent
import com.tim.fakegps.feature.map.yandex.YandexContent
import com.tim.location.MapProvider

@Composable
fun CommonMapContent(component: CommonMapMain) {
    val state by component.models.subscribeAsState()
    Box(modifier = Modifier.fillMaxSize()) {
        when (state.provider) {
            MapProvider.Google -> GoogleContent(state = state, onMapClick = component::onMapClick)
            MapProvider.Yandex -> YandexContent(state = state, onMapClick = component::onMapClick)
        }

        IconButton(
            modifier = Modifier
                .padding(16.dp)
                .padding(bottom = 16.dp)
                .size(56.dp)
                .align(Alignment.BottomStart)
                .background(
                    color = MaterialTheme.colorScheme.primaryContainer,
                    shape = RoundedCornerShape(10.dp)
                ), enabled = state.isButtonEnabled, onClick = component::onButtonClick
        ) {
            Icon(
                imageVector = if (state.isRunning) Icons.Default.Close else Icons.Default.PlayArrow,
                contentDescription = if (state.isRunning) "Stop fake location" else "Start fake location"
            )
        }
    }
}
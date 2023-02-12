package com.tim.fakegps.feature.map.common.commonui

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
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import com.tim.fakegps.feature.map.common.commondata.CommonMapMain
import com.tim.fakegps.feature.map.common.commondata.CommonMapMain.Model
import com.tim.fakegps.feature.map.common.commonui.test.TestTags.GOOGLE_MAP
import com.tim.fakegps.feature.map.common.commonui.test.TestTags.YANDEX_MAP
import com.tim.fakegps.feature.map.provider.google.GoogleContent
import com.tim.fakegps.feature.map.provider.yandex.YandexContent
import com.tim.location.MapProvider

@Composable
fun CommonMapContent(component: CommonMapMain) {
    val state by component.models.collectAsState(initial = Model())
    Box(modifier = Modifier.fillMaxSize()) {
        when (state.provider) {
            MapProvider.Google -> GoogleContent(
                modifier = Modifier.testTag(GOOGLE_MAP),
                state = state,
                onMapClick = component::onLocationSelected
            )

            MapProvider.Yandex -> YandexContent(
                modifier = Modifier.testTag(YANDEX_MAP),
                state = state,
                onMapClick = component::onLocationSelected
            )
        }

        IconButton(
            modifier = Modifier
                .padding(16.dp)
                .padding(bottom = 16.dp)
                .size(56.dp)
                .align(Alignment.BottomStart)
                .clip(shape = RoundedCornerShape(10.dp)),
            colors = IconButtonDefaults.iconButtonColors(
                containerColor = MaterialTheme.colorScheme.background,
                disabledContainerColor = Color.LightGray,
                contentColor = MaterialTheme.colorScheme.onBackground,
                disabledContentColor = Color.Gray
            ),
            enabled = state.isButtonEnabled, onClick = component::onButtonClick
        ) {
            Icon(
                imageVector = if (state.isRunning) Icons.Default.Close else Icons.Default.PlayArrow,
                contentDescription = if (state.isRunning) "Stop" else "Start"
            )
        }
    }
}
package com.tim.fakegps.feature.search

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.tim.fakegps.feature.search.Search.Model

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun SearchField(component: Search) {
    val state by component.models.collectAsState(initial = Model())
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = MaterialTheme.colorScheme.background,
                shape = RoundedCornerShape(bottomStart = 10.dp, bottomEnd = 10.dp)
            )
            .padding(16.dp)
            .animateContentSize()
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TextField(modifier = Modifier.fillMaxWidth(),
                value = state.query,
                onValueChange = component::onQueryChanged,
                placeholder = { Text(text = "Search") })
            Spacer(modifier = Modifier.height(16.dp))
            if (state.isLoading) {
                CircularProgressIndicator()
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(state.suggestions) { suggestion ->
                        Text(
                            modifier = Modifier
                                .fillMaxWidth()
                                .border(width = 1.dp, color = Color.LightGray)
                                .padding(16.dp)
                                .clickable { component.onSuggestionClick(suggestion) }
                                .animateItemPlacement(),
                            color = MaterialTheme.colorScheme.onBackground,
                            text = suggestion.name
                        )
                    }
                }
            }
        }
    }
}
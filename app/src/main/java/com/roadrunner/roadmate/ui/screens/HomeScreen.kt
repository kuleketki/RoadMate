package com.roadrunner.roadmate.ui.screens

import android.R
import android.graphics.Color
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.roadrunner.roadmate.ui.viewmodel.NewsViewModel
import kotlinx.coroutines.flow.collectLatest

@Composable
fun HomeScreen(newsViewModel: NewsViewModel = hiltViewModel()) {
    Surface(modifier = Modifier.fillMaxSize()) {
        newsViewModel.getPerson()
    }
}

@Preview
@Composable
fun HomeScreenPreview() {
    HomeScreen()
}
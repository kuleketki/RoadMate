package com.roadrunner.roadmate.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import com.roadrunner.roadmate.R
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavController,modifier: Modifier = Modifier) {

    LaunchedEffect(Unit) {
        delay(3000)
        navController.navigate(RoadMateScreenNav.HomeScreen.name)
    }

    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painterResource(R.drawable.logo),
            contentDescription = "LOGO",
            modifier = Modifier.fillMaxWidth(0.8f)
        )
    }
}

package com.roadrunner.roadmate

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.roadrunner.roadmate.api.NewsApi
import com.roadrunner.roadmate.screens.ArticleDetailsScreen
import com.roadrunner.roadmate.screens.ArticlesListScreen
import com.roadrunner.roadmate.screens.HomeScreen
import com.roadrunner.roadmate.screens.RoadMateScreenNav
import com.roadrunner.roadmate.screens.SplashScreen
import com.roadrunner.roadmate.ui.navigation.AppNavigationGraph
import com.roadrunner.roadmate.ui.theme.RoadMateTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var newsApi: NewsApi

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RoadMateTheme {
                App()
            }
        }
    }
}

@Composable
fun App() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = RoadMateScreenNav.SplashScreen.name) {
        composable(route = RoadMateScreenNav.SplashScreen.name) {
            SplashScreen(navController)
        }

        composable(route = RoadMateScreenNav.HomeScreen.name) {
            HomeScreen(navController = navController)
        }

        composable(route = RoadMateScreenNav.NewsListScreen.name) {
            ArticlesListScreen(navController = navController)
        }

        composable(route = RoadMateScreenNav.NewsDetailScreen.name) {
            ArticleDetailsScreen(navController)
        }

    }
}

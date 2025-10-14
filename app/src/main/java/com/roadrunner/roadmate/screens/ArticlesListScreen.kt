package com.roadrunner.roadmate.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.roadrunner.roadmate.R
import com.roadrunner.roadmate.models.Article
import com.roadrunner.roadmate.viewmodels.NewsViewModel
import com.skydoves.landscapist.glide.GlideImage
import formatPublishedAt

@Composable
fun ArticlesListScreen(navController: NavController) {
    val viewModel: NewsViewModel = hiltViewModel()
    val articles: State<List<Article>> = viewModel.articles.collectAsState()
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = WindowInsets.statusBars.asPaddingValues()
    ) {
        items(items = articles.value) { article ->
            ArticleListItem(article, navController)
        }
    }
}

@Composable
fun ArticleListItem(article: Article, navController: NavController) {
    val displayDate = remember(article.publishedAt) {
        article.publishedAt?.let { formatPublishedAt(it) }
    }
    Card(
        onClick = {
            navController.currentBackStackEntry
                ?.savedStateHandle
                ?.set("article", article)
            navController.navigate(RoadMateScreenNav.NewsDetailScreen.name)
        },
        shape = RoundedCornerShape(8.dp),
        border = BorderStroke(1.dp, Color.Black),                  // or use OutlinedCard()
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Column(modifier = Modifier.padding(6.dp)) {
            GlideImage(
                imageModel = { article.urlToImage },
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(16f / 9f),
                failure = {
                    Image(
                        painter = painterResource(R.drawable.placeholder),
                        contentDescription = "Couldn't load image"
                    )
                },
                loading = {
                    Image(
                        painter = painterResource(R.drawable.placeholder),
                        contentDescription = null
                    )
                }
            )
            Text(
                text = article.title ?: "Untitled",
                fontSize = 13.sp,
                color = Color.Black,
                modifier = Modifier.padding(0.dp, 5.dp),
                style = MaterialTheme.typography.bodyMedium
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                Text(
                    text = "Author:  ${article.author}",
                    fontSize = 10.sp,
                    textAlign = TextAlign.Start
                )
                Text(
                    text = "Published At: $displayDate",
                    fontSize = 10.sp,
                    textAlign = TextAlign.End
                )
            }

        }

    }
}
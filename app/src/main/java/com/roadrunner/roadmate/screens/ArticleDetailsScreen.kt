package com.roadrunner.roadmate.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.roadrunner.roadmate.models.Article
import com.roadrunner.roadmate.models.Source
import com.skydoves.landscapist.glide.GlideImage
import formatPublishedAt
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.roadrunner.roadmate.R
import com.roadrunner.roadmate.utilities.parseNewsContent
import com.skydoves.landscapist.ImageOptions

@Composable
fun ArticleDetailsScreen(navController: NavController) {

    val article = navController.previousBackStackEntry
        ?.savedStateHandle
        ?.get<Article>("article")
    article?.let {
        ArticleDetailItem(article)
    }
}

@Composable
fun ArticleDetailItem(article: Article) {
    val colors = MaterialTheme.colorScheme
    val uri = LocalUriHandler.current
    val displayDate = remember(article.publishedAt) { formatPublishedAt(article.publishedAt ?: "") }

    // Parse NewsAPI "… [+1234 chars]" and return preview + hasMore
    val (content, hasMore) = remember(article.content) { parseNewsContent(article.content ?: "") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp) // uniform, tight spacing
    ) {
        Text(
            text = article.title ?: "Untitled",
            style = MaterialTheme.typography.headlineLarge,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            modifier = Modifier.padding(top = 10.dp)
        )

        GlideImage(
            imageModel = { article.urlToImage },
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(6.dp))
                .aspectRatio(16f / 9f)
                .clickable { article.url?.takeIf { it.isNotBlank() }?.let(uri::openUri) },
            imageOptions = ImageOptions(contentScale = ContentScale.Crop),
            loading = {
                androidx.compose.foundation.Image(
                    painterResource(R.drawable.placeholder), null,
                    Modifier
                        .fillMaxWidth()
                        .aspectRatio(16f / 9f)
                )
            },
            failure = {
                androidx.compose.foundation.Image(
                    painterResource(R.drawable.placeholder), null,
                    Modifier
                        .fillMaxWidth()
                        .aspectRatio(16f / 9f)
                )
            }
        )

        Text(
            text = article.description ?: "No description",
            style = MaterialTheme.typography.bodyLarge,
            fontSize = 16.sp,
            color = colors.onSurface
        )

        HorizontalDivider(thickness = 0.5.dp, color = colors.outlineVariant)

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 1.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                "Author: ${article.author ?: "Unknown"}",
                fontSize = 10.sp,
                color = colors.onSurfaceVariant,
                fontFamily = FontFamily(Font(R.font.play_write_us_modern_regular))
            )
            Text(
                displayDate,
                fontSize = 10.sp,
                color = colors.onSurfaceVariant,
                textAlign = TextAlign.End
            )
        }

        Text("Source: ${article.source?.name.orEmpty()}", fontSize = 12.sp, color = colors.onSurfaceVariant, fontFamily = FontFamily(Font(R.font.play_write_us_modern_regular)) )

        HorizontalDivider(thickness = 0.5.dp, color = colors.outlineVariant)

        // Content preview + inline "Read more"
        val annotated = remember(content, hasMore, article.url) {
            buildAnnotatedString {
                append(content) // ends with "…" if truncated by parseNewsContent
                if (hasMore && !article.url.isNullOrBlank()) {
                    append(" ")
                    pushStringAnnotation(tag = "URL", annotation = article.url!!)
                    withStyle(
                        SpanStyle(
                            color = colors.primary,
                            textDecoration = TextDecoration.Underline
                        )
                    ) { append("Read more") }
                    pop()
                }
            }
        }

        ClickableText(
            text = annotated,
            style = MaterialTheme.typography.bodyLarge.copy(
                color = colors.onSurface,
                fontSize = 14.sp
            ),
            onClick = { offset ->
                annotated.getStringAnnotations("URL", offset, offset)
                    .firstOrNull()?.let { uri.openUri(it.item) }
            }
        )
    }
}


@Composable
@Preview
fun ArticleDetailScreenPreview() {
    val article = Article(
        source = Source("null", "ABC"),
        author = "Bruce Gil",
        title = "Feds Scrutinizing Potential Insider Trading in Major Crypto Deals",
        description = "Wall Street regulators are reportedly flagging suspicious trading in the stocks of companies that have announced big crypto bets.",
        url = "https://gizmodo.com/feds-scrutinizing-potential-insider-trading-in-major-crypto-deals-2000664439",
        urlToImage = "https://gizmodo.com/app/uploads/2024/09/An-image-of-Bitcoin-cryptocurrency.jpg",
        publishedAt = "2025-09-27T13:00:35Z",
        content = "Federal regulators are scrutinizing a growing number of companies that have embraced so-called crypto-treasury strategies this year, after unusual trading patterns in their shares caught their attent… [+2741 chars]"
    )
    ArticleDetailItem(article)
}
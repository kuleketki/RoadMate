package com.roadrunner.roadmate.models

data class NewsArticles(
    val articles: List<Article>,
    val status: String,
    val totalResults: Int
)
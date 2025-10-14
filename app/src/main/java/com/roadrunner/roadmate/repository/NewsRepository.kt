package com.roadrunner.roadmate.repository

import com.roadrunner.roadmate.api.NewsApi
import com.roadrunner.roadmate.models.Article
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class NewsRepository @Inject constructor(private val newsApi: NewsApi) {
    private val _articles = MutableStateFlow<List<Article>>(emptyList())
    val articles: StateFlow<List<Article>> get() = _articles

    suspend fun getNewsArticles(type: String, from: String) {
        val response = newsApi.getNews(q = type, from = from)
        _articles.emit(response.articles)

    }
}
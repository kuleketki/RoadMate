package com.roadrunner.roadmate.api

import com.roadrunner.roadmate.models.Article
import com.roadrunner.roadmate.models.NewsArticles
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {
    // https://newsapi.org/v2/everything?q=DMV&from=2025-09-05&sortBy=popularity&apiKey=5de882df853246ceb957d45ebc5b43cd


    @GET("v2/everything")
    suspend fun getNews(
        @Query("q") q: String,
        @Query("from") from: String,
        @Query("sortBy") sortBy: String = "popularity",
        @Query("apiKey") apiKey: String = "5de882df853246ceb957d45ebc5b43cd"
    ): NewsArticles
}
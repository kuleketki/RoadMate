package com.roadrunner.roadmate.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.roadrunner.roadmate.models.Article
import com.roadrunner.roadmate.repository.NewsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(private val repository: NewsRepository) : ViewModel() {
    val articles: StateFlow<List<Article>>
        get() = repository.articles

    private val _selectedArticle = MutableStateFlow<Article?>(null)
    val selectedArticle: StateFlow<Article?> = _selectedArticle

    fun selectArticle(article: Article?) {
        _selectedArticle.value = article
    }

    init {
        viewModelScope.launch {
            val dateString = LocalDate.now().minusDays(28).toString()
            repository.getNewsArticles(from = dateString, type = "DMV")
        }
    }

}
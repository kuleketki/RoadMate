package com.roadrunner.roadmate.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.roadrunner.roadmate.data.Person
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor() : ViewModel() {

    var sharedFlow = MutableSharedFlow<Person>()

    init {
        Log.d(TAG, "Init block of NewsViewModel")
    }

    fun getPerson() {
        viewModelScope.launch {
            sharedFlow.emit(Person("Ketki",31))
        }
    }


    companion object {
        const val TAG = "NewsViewModel"
    }
}
package com.example.myapplication.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.usecase.GetPlayersUseCase
import com.example.myapplication.usecase.GetRecentMatchesUseCase

class MainViewModelFactory(
    private val getPlayersUseCase: GetPlayersUseCase,
    private val getRecentMatchesUseCase: GetRecentMatchesUseCase
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MainViewModel(getPlayersUseCase, getRecentMatchesUseCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

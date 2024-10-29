package com.example.myapplication

import org.koin.dsl.module

// Example repository class
class MyRepository {
    fun getData(): String {
        return "Hello from MyRepository"
    }
}

// Example ViewModel class
class MyViewModel(private val repository: MyRepository) {
    fun getGreeting(): String {
        return repository.getData()
    }
}

val AppModule = module {
    // Singleton for the repository
    single { MyRepository() }

    // Factory for the ViewModel
    factory { MyViewModel(get()) }
}

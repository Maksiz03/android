package com.example.myapplication.di

<<<<<<< Updated upstream
import com.example.myapplication.network.DotaApi
import com.example.myapplication.repository.PlayerRepository
import com.example.myapplication.repository.PlayerRepositoryImpl
import com.example.myapplication.usecase.GetPlayersUseCase
import com.example.myapplication.usecase.GetRecentMatchesUseCase
import com.example.myapplication.viewmodel.MainViewModel
import okhttp3.OkHttpClient
=======
import com.example.myapplication.usecase.GetPlayersUseCase
import com.example.myapplication.usecase.GetRecentMatchesUseCase
import com.example.myapplication.viewmodel.MainViewModel
>>>>>>> Stashed changes
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

<<<<<<< Updated upstream
// Dependency Injection Module
val AppModule = module {
    // Register DotaApi
    single<DotaApi> {
        Retrofit.Builder()
            .baseUrl("https://api.opendota.com/api/") // Ensure the correct base URL
            .addConverterFactory(GsonConverterFactory.create())
            .client(OkHttpClient.Builder().build())
            .build()
            .create(DotaApi::class.java)
    }

    // Register PlayerRepository using DotaApi
    single<PlayerRepository> { PlayerRepositoryImpl(get()) }

    // Register UseCases with the repository
    single { GetPlayersUseCase(get()) }
    single { GetRecentMatchesUseCase(get()) }

    // Register ViewModel with the registered UseCases
=======
val appModule = module {
    // Регистрация UseCase как синглтоны
    single { GetPlayersUseCase() }
    single { GetRecentMatchesUseCase() }

    // Регистрация MainViewModel
>>>>>>> Stashed changes
    viewModel { MainViewModel(get(), get()) }
}

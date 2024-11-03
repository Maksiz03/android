package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.navigation.Navigation
import com.example.myapplication.network.DotaApiImpl
import com.example.myapplication.repository.MatchRepositoryImpl
import com.example.myapplication.usecase.GetRecentMatchesUseCase
import com.example.myapplication.viewmodel.MatchViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppContent()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppContent() {
    val navController = rememberNavController()

    val dotaApi = DotaApiImpl()
    val matchRepository = MatchRepositoryImpl(dotaApi)
    val getRecentMatchesUseCase = GetRecentMatchesUseCase(matchRepository)

    val viewModel = MatchViewModel(getRecentMatchesUseCase)

    Navigation(navController = navController, viewModel = viewModel)
}
package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.navigation.BottomNavigationBar
import com.example.myapplication.navigation.Navigation
import com.example.myapplication.network.DotaApiImpl
import com.example.myapplication.repository.MatchRepositoryImpl
import com.example.myapplication.usecase.GetRecentMatchesUseCase
import com.example.myapplication.usecase.GetMatchesByIdsUseCase
import com.example.myapplication.viewmodel.MatchViewModel
import com.example.myapplication.datastore.FavoritesDataStore
import com.example.myapplication.datastore.PreferencesDataStore

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

    // Initialize dependencies
    val dotaApi = DotaApiImpl()
    val matchRepository = MatchRepositoryImpl(dotaApi)
    val getRecentMatchesUseCase = GetRecentMatchesUseCase(matchRepository)
    val getMatchesByIdsUseCase = GetMatchesByIdsUseCase(matchRepository)

    val viewModel = MatchViewModel(getRecentMatchesUseCase, getMatchesByIdsUseCase)

    // Initialize FavoritesDataStore and PreferencesDataStore
    val favoritesDataStore = FavoritesDataStore()
    val preferencesDataStore = PreferencesDataStore(context = LocalContext.current)

    Scaffold(
        bottomBar = {
            BottomNavigationBar(navController = navController)
        }
    ) { innerPadding ->
        Navigation(
            navController = navController,
            viewModel = viewModel,
            favoritesDataStore = favoritesDataStore,
            preferencesDataStore = preferencesDataStore, // Ensure this is passed
            paddingValues = innerPadding
        )
    }
}

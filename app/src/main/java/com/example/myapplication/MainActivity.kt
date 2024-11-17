package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.datastore.FavoritesDataStore
import com.example.myapplication.datastore.PreferencesDataStore
import com.example.myapplication.navigation.BottomNavigationBar
import com.example.myapplication.navigation.Navigation
import com.example.myapplication.viewmodel.MatchViewModel
import dagger.hilt.android.AndroidEntryPoint
import androidx.hilt.navigation.compose.hiltViewModel

// Аннотируем активность для использования Hilt
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppContent()
        }
    }
}

// Функция для контента с Bottom Navigation и навигацией
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppContent(
    viewModel: MatchViewModel = hiltViewModel() // Use Hilt to provide ViewModel
) {
    val navController = rememberNavController()

    // Get the current context
    val context = LocalContext.current

    // Initialize DataStore objects with context
    val favoritesDataStore = FavoritesDataStore(context)
    val preferencesDataStore = PreferencesDataStore(context)

    // Scaffold for main UI with BottomBar
    Scaffold(
        bottomBar = {
            BottomNavigationBar(navController = navController)
        }
    ) { innerPadding ->
        // Navigation with passing necessary data
        Navigation(
            navController = navController,
            viewModel = viewModel,
            favoritesDataStore = favoritesDataStore,
            preferencesDataStore = preferencesDataStore,
            paddingValues = innerPadding
        )
    }
}

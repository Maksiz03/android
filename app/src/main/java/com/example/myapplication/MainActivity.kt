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
import com.example.myapplication.datastore.BadgeCache
import com.example.myapplication.ui.theme.MyApplicationTheme // Assuming you have a theme set up
import com.example.myapplication.viewmodel.ProfileViewModel

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                AppContent()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppContent(
    matchViewModel: MatchViewModel = hiltViewModel(),
    profileViewModel: ProfileViewModel = hiltViewModel() // Inject ProfileViewModel
) {
    val navController = rememberNavController()
    val context = LocalContext.current

    // Initialize DataStore objects with context
    val favoritesDataStore = FavoritesDataStore(context)
    val preferencesDataStore = PreferencesDataStore(context)
    val badgeCache = BadgeCache()

    Scaffold(
        bottomBar = {
            BottomNavigationBar(navController = navController)
        }
    ) { innerPadding ->
        Navigation(
            navController = navController,
            viewModel  = matchViewModel,
            profileViewModel = profileViewModel, // Pass ProfileViewModel here
            favoritesDataStore = favoritesDataStore,
            preferencesDataStore = preferencesDataStore,
            badgeCache = badgeCache,
            paddingValues = innerPadding
        )
    }
}
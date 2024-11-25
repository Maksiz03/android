package com.example.myapplication

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
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
import com.example.myapplication.ui.theme.MyApplicationTheme
import com.example.myapplication.viewmodel.ProfileViewModel
import android.content.Context
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.core.content.ContextCompat

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                AppContent() // Ensure your theme is properly applied here
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppContent(
    matchViewModel: MatchViewModel = hiltViewModel(),
    profileViewModel: ProfileViewModel = hiltViewModel()
) {
    val navController = rememberNavController()
    val context = LocalContext.current

    // Инициализация DataStore объектов с контекстом
    val favoritesDataStore = FavoritesDataStore(context)
    val preferencesDataStore = PreferencesDataStore(context)
    val badgeCache = BadgeCache()

    // Лаунчер для запроса разрешения на уведомления (API 33+)
    val notificationPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted ->
            if (isGranted) {
                // Разрешение предоставлено, можно отправлять уведомления
            } else {
                // Разрешение не предоставлено, уведомления не будут работать
            }
        }
    )

    // Эффект для запроса разрешения
    LaunchedEffect(Unit) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                notificationPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        }
    }

    // Scaffold для отображения нижней навигации и контента
    Scaffold(
        bottomBar = {
            BottomNavigationBar(navController = navController)
        }
    ) { innerPadding ->
        Navigation(
            navController = navController,
            viewModel = matchViewModel,
            profileViewModel = profileViewModel,
            favoritesDataStore = favoritesDataStore,
            preferencesDataStore = preferencesDataStore,
            badgeCache = badgeCache,
            paddingValues = innerPadding
        )
    }
}

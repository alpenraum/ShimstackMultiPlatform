package com.alpenraum.shimstack

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.alpenraum.shimstack.data.datastore.ShimstackDatastore
import com.alpenraum.shimstack.domain.InitializeAppUseCase
import com.alpenraum.shimstack.domain.model.PreferredTheme
import com.alpenraum.shimstack.domain.userSettings.GetUserSettingsUseCase
import com.alpenraum.shimstack.ui.base.compose.theme.AppTheme
import com.alpenraum.shimstack.ui.base.navigation.ShimstackNavHost
import kotlinx.coroutines.flow.collectLatest
import org.koin.compose.koinInject

@Composable
fun App() {
    initializeApp()

    val preferredTheme = remember { mutableStateOf(PreferredTheme.default) }
    val userSettingsUseCase = koinInject<GetUserSettingsUseCase>()
    LaunchedEffect(Unit) {
        userSettingsUseCase().collectLatest {
            preferredTheme.value = it.preferredTheme
        }
    }

    AppTheme(preferredTheme = preferredTheme.value) {
        Surface(Modifier.fillMaxSize()) {
            val navController = rememberNavController()
            ShimstackNavHost(navController, modifier = Modifier)
        }
    }
}

@Composable
private fun initializeApp() {
    val datastore = koinInject<ShimstackDatastore>()
    val initializeAppUseCase = koinInject<InitializeAppUseCase>()
    LaunchedEffect(Unit) {
        datastore.isOnboardingCompleted.collect {
            if (!it) {
                initializeAppUseCase()
            }
        }
    }
}
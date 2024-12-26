package com.alpenraum.shimstack

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.alpenraum.shimstack.base.di.ShimstackGeneratedModule
import com.alpenraum.shimstack.base.di.databaseModule
import com.alpenraum.shimstack.base.di.navigationModule
import com.alpenraum.shimstack.base.di.platformModule
import com.alpenraum.shimstack.data.datastore.ShimstackDatastore
import com.alpenraum.shimstack.domain.InitializeAppUseCase
import com.alpenraum.shimstack.ui.base.compose.theme.AppTheme
import com.alpenraum.shimstack.ui.base.navigation.ShimstackNavHost
import org.koin.compose.KoinApplication
import org.koin.compose.koinInject
import org.koin.ksp.generated.module

@Composable
fun App() {
    KoinApplication(application = {
        modules(navigationModule(), ShimstackGeneratedModule().module, databaseModule(), platformModule())
    }) {
        initializeApp()

        AppTheme {
            Surface(Modifier.fillMaxSize()) {
                val navController = rememberNavController()
                ShimstackNavHost(navController, modifier = Modifier)
            }
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
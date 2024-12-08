package com.alpenraum.shimstack

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.alpenraum.shimstack.base.di.navigationModule
import com.alpenraum.shimstack.base.di.shimstackModule
import com.alpenraum.shimstack.ui.base.compose.theme.AppTheme
import com.alpenraum.shimstack.ui.base.navigation.ShimstackNavHost
import org.koin.compose.KoinApplication

@Composable
fun App() {
    KoinApplication(application = {
        modules(shimstackModule(), navigationModule())
    }) {
        AppTheme {
            Surface(Modifier.fillMaxSize()) {
//                var showContent by remember { mutableStateOf(false) }
//                Column(
//                    Modifier.fillMaxWidth().safeDrawingPadding(),
//                    horizontalAlignment = Alignment.CenterHorizontally,
//                ) {
//                    Button(onClick = { showContent = !showContent }) {
//                        Text("Click me!")
//                    }
//
//                    val greet = koinInject<Greeting>()
//                    AnimatedVisibility(showContent) {
//                        val greeting = remember { greet.greet() }
//                        Column(
//                            Modifier.fillMaxWidth(),
//                            horizontalAlignment = Alignment.CenterHorizontally,
//                        ) {
//                            Image(painterResource(Res.drawable.compose_multiplatform), null)
//                            Text("Compose: $greeting")
//                        }
//                    }
//                }
                val navController = rememberNavController()
                ShimstackNavHost(navController, modifier = Modifier)
            }
        }
    }
}
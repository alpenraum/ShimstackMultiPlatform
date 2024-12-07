package com.alpenraum.shimstack

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.alpenraum.shimstack.base.di.shimstackModule
import com.alpenraum.shimstack.ui.base.compose.AppTheme
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.KoinApplication
import org.koin.compose.koinInject
import shimstackmultiplatform.composeapp.generated.resources.Res
import shimstackmultiplatform.composeapp.generated.resources.compose_multiplatform

@Composable
@Preview
private fun App() {
    KoinApplication(application = {
        modules(shimstackModule())
    }) {
        AppTheme {
            Surface(Modifier.fillMaxSize()) {
                var showContent by remember { mutableStateOf(false) }
                Column(
                    Modifier.fillMaxWidth().safeDrawingPadding(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Button(onClick = { showContent = !showContent }) {
                        Text("Click me!")
                    }

                    val greet = koinInject<Greeting>()
                    AnimatedVisibility(showContent) {
                        val greeting = remember { greet.greet() }
                        Column(
                            Modifier.fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                        ) {
                            Image(painterResource(Res.drawable.compose_multiplatform), null)
                            Text("Compose: $greeting")
                        }
                    }
                }
            }
        }
    }
}

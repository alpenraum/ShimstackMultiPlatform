package com.alpenraum.shimstack.ui.onboarding

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun OnboardingFeature(
    navigator: NavController,
    viewModel: OnboardingViewModel = koinViewModel()
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.surfaceVariant
    ) {
        OnboardingScreen(
            onSkipButtonClicked = { viewModel.onSkipClicked(navigator) },
            onAutoFillClick = { viewModel.onAutoFillClick(navigator) },
            modifier =
                Modifier
                    .padding(it)
                    .padding(16.dp),
            onAddBikeClicked = {
                viewModel.onAddBikeNavigationClicked(navigator)
            }
        )
    }
}
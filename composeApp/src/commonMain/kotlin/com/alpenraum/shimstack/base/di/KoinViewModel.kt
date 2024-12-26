package com.alpenraum.shimstack.base.di

import androidx.lifecycle.ViewModel
import org.koin.core.component.KoinComponent

abstract class KoinViewModel :
    ViewModel(),
    KoinComponent
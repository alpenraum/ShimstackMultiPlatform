package com.alpenraum.shimstack.domain

import com.alpenraum.shimstack.base.DispatchersProvider
import com.alpenraum.shimstack.domain.bikeTemplate.BikeTemplateRepository
import com.alpenraum.shimstack.domain.userSettings.DecideUserMeasurementUnitUseCase
import kotlinx.coroutines.withContext
import org.koin.core.annotation.Single

@Single
class InitializeAppUseCase(
    private val bikeTemplateRepository: BikeTemplateRepository,
    private val decideUserMeasurementUnitUseCase: DecideUserMeasurementUnitUseCase,
    private val dispatchersProvider: DispatchersProvider
) {
    suspend operator fun invoke() =
        withContext(dispatchersProvider.io) {
            bikeTemplateRepository.prepopulateData()
            decideUserMeasurementUnitUseCase()
        }
}
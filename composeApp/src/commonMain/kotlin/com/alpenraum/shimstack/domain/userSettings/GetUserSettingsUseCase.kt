package com.alpenraum.shimstack.domain.userSettings

import kotlinx.coroutines.flow.Flow
import org.koin.core.annotation.Single

@Single
class GetUserSettingsUseCase(
    private val userSettingsRepository: UserSettingsRepository
) {
    operator fun invoke(): Flow<UserSettings> = userSettingsRepository.getUserSettings()
}
package com.alpenraum.shimstack.domain.bikeTemplate

import com.alpenraum.shimstack.domain.model.biketemplate.BikeTemplate

interface BikeTemplateRepository {
    suspend fun prepopulateData()

    suspend fun createBikeTemplates(list: List<BikeTemplate>)

    suspend fun getBikeTemplatesFilteredByName(name: String): List<BikeTemplate>
}
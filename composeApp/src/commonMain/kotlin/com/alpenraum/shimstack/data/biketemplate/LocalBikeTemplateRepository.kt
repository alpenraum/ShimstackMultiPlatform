package com.alpenraum.shimstack.data.biketemplate

import com.alpenraum.shimstack.data.db.BikeTemplateDAO
import com.alpenraum.shimstack.data.model.bike.BikeTemplateDTO
import com.alpenraum.shimstack.data.toDTO
import com.alpenraum.shimstack.data.toDomain
import com.alpenraum.shimstack.domain.bikeTemplate.BikeTemplateRepository
import com.alpenraum.shimstack.domain.model.biketemplate.BikeTemplate
import kotlinx.serialization.json.Json
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.koin.core.annotation.Single
import shimstackmultiplatform.composeapp.generated.resources.Res

@Single(binds = [BikeTemplateRepository::class])
class LocalBikeTemplateRepository(
    private val bikeTemplateDAO: BikeTemplateDAO
) : BikeTemplateRepository {
    override suspend fun prepopulateData() {
        val json = loadFileFromAssets(fileName = "bike_templates", fileEnding = "json")

        createBikeTemplates(Json.decodeFromString<List<BikeTemplateDTO>>(json))
    }

    private suspend fun createBikeTemplates(
        list: List<BikeTemplateDTO>,
        x: String = ""
    ) {
        bikeTemplateDAO.insertBike(list)
    }

    override suspend fun createBikeTemplates(list: List<BikeTemplate>) {
        createBikeTemplates(list.map { it.toDTO() })
    }

    override suspend fun getBikeTemplatesFilteredByName(name: String): List<BikeTemplate> =
        bikeTemplateDAO.getBikeTemplatesFilteredByName(name).map {
            it.toDomain()
        }

    @OptIn(ExperimentalResourceApi::class)
    private suspend fun loadFileFromAssets(
        fileName: String,
        fileEnding: String
    ): String {
        val readBytes = Res.readBytes("files/$fileName.$fileEnding")
        val jsonString = readBytes.decodeToString()
        return Json.decodeFromString(jsonString)
    }
}
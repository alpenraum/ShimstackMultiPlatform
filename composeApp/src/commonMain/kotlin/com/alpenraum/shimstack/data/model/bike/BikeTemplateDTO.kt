package com.alpenraum.shimstack.data.model.bike

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.alpenraum.shimstack.data.db.AppDatabase
import kotlinx.serialization.Serializable

@Serializable
@Entity(tableName = AppDatabase.TABLE_BIKE_TEMPLATE)
class BikeTemplateDTO(
    @PrimaryKey(autoGenerate = true) val id: Int? = null,
    val name: String,
    val type: Int,
    val isEBike: Boolean,
    val frontSuspensionTravelInMM: Int,
    val rearSuspensionTravelInMM: Int,
    val frontTireWidthInMM: Double = 0.0,
    val frontRimWidthInMM: Double = 0.0,
    val rearTireWidthInMM: Double = 0.0,
    val rearRimWidthInMM: Double = 0.0
)
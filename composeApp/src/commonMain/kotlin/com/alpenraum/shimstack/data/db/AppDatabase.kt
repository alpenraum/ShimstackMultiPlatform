package com.alpenraum.shimstack.data.db

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import com.alpenraum.shimstack.base.DispatchersProvider
import com.alpenraum.shimstack.data.model.bike.BikeDTO
import com.alpenraum.shimstack.data.model.bike.BikeTemplateDTO

@Database(
    entities = [BikeDTO::class, BikeTemplateDTO::class],
    version = 1,
    exportSchema = true
)
@ConstructedBy(AppDatabaseConstructor::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun bikeDao(): BikeDAO

    abstract fun bikeTemplateDao(): BikeTemplateDAO

    companion object {
        const val TABLE_BIKE = "user_bikes"
        const val TABLE_BIKE_TEMPLATE = "template_bikes"
    }
}

// The Room compiler generates the `actual` implementations.
@Suppress("NO_ACTUAL_FOR_EXPECT")
expect object AppDatabaseConstructor : RoomDatabaseConstructor<AppDatabase> {
    override fun initialize(): AppDatabase
}

fun createDatabase(
    builder: RoomDatabase.Builder<AppDatabase>,
    dispatchersProvider: DispatchersProvider
): AppDatabase =
    builder
        .fallbackToDestructiveMigrationOnDowngrade(true) // .addMigration
        .setDriver(BundledSQLiteDriver())
        .setQueryCoroutineContext(dispatchersProvider.io)
        .build()

internal const val dbFileName = "shimstack_db.db"
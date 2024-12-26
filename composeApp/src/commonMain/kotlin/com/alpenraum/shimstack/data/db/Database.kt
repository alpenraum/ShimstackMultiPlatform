package com.alpenraum.shimstack.data.db

import androidx.room.RoomDatabase
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import com.alpenraum.shimstack.base.DispatchersProvider

fun getRoomDatabase(
    builder: RoomDatabase.Builder<AppDatabase>,
    dispatchersProvider: DispatchersProvider
): AppDatabase =
    builder
        .fallbackToDestructiveMigrationOnDowngrade(true)
        .setDriver(BundledSQLiteDriver())
        .setQueryCoroutineContext(dispatchersProvider.io)
        .build()
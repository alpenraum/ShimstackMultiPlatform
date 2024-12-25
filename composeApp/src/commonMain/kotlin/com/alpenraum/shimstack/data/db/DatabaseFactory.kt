package com.alpenraum.shimstack.data.db

import androidx.room.RoomDatabase

expect class DatabaseFactory {
    fun getDatabaseBuilder(): RoomDatabase.Builder<AppDatabase>
}
package com.alpenraum.shimstack.base.di

import com.alpenraum.shimstack.data.db.AppDatabase
import com.alpenraum.shimstack.data.db.BikeDAO
import com.alpenraum.shimstack.data.db.BikeTemplateDAO
import com.alpenraum.shimstack.data.db.createDatabase
import org.koin.dsl.module

fun databaseModule() =
    module {
        single<AppDatabase> {
            createDatabase(get(), get())
        }

        single<BikeDAO> {
            val db = get<AppDatabase>()
            db.bikeDao()
        }
        single<BikeTemplateDAO> {
            val db = get<AppDatabase>()
            db.bikeTemplateDao()
        }
    }
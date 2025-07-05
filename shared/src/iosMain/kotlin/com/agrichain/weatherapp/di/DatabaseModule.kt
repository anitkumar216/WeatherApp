package com.agrichain.weatherapp.di

import app.cash.sqldelight.db.SqlDriver
import com.agrichain.weatherapp.db.DatabaseDriverFactory
import com.agrichain.weatherapp.db.WeatherDatabase
import org.koin.dsl.module

val databaseModule = module {

    single<SqlDriver> { DatabaseDriverFactory().createDriver() }

    single<WeatherDatabase> { WeatherDatabase(get()) }

}
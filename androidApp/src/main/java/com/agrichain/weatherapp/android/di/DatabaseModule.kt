package com.agrichain.weatherapp.android.di

import app.cash.sqldelight.db.SqlDriver
import com.agrichain.weatherapp.db.DatabaseDriverFactory
import com.agrichain.weatherapp.db.WeatherDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val databaseModule = module {

    single<SqlDriver> { DatabaseDriverFactory(androidContext()).createDriver() }
    single<WeatherDatabase> { WeatherDatabase(get()) }

}
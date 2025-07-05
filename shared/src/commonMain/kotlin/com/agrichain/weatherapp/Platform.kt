package com.agrichain.weatherapp

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform
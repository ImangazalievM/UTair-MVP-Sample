package com.utair.global.appinitializer

interface AppInitializersProvider {

    fun getInitializers(): List<AppInitializerElement>
}
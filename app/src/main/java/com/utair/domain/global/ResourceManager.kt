package com.utair.domain.global

interface ResourceManager {

    fun getString(resourceId: Int): String

    fun getInteger(resourceId: Int): Int

}
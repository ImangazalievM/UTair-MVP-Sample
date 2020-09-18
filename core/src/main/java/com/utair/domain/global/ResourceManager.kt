package com.utair.domain.global

interface ResourceManager {

    fun getString(resourceId: Int, vararg args: Any): String

    fun getInteger(resourceId: Int): Int

}
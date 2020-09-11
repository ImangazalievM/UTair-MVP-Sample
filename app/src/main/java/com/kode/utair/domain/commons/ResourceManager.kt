package com.kode.utair.domain.commons

interface ResourceManager {

    fun getString(resourceId: Int): String

    fun getInteger(resourceId: Int): Int

}
package com.utair.presentation.mvp.global

import android.content.Context
import com.utair.domain.global.ResourceManager
import javax.inject.Inject

class AndroidResourceManager @Inject constructor(
        private val context: Context
) : ResourceManager {

    override fun getString(resourceId: Int): String {
        return context.resources.getString(resourceId)
    }

    override fun getInteger(resourceId: Int): Int {
        return context.resources.getInteger(resourceId)
    }

}
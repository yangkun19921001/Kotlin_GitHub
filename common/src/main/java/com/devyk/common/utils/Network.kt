package com.devyk.common.utils

import com.devyk.common.App
import org.jetbrains.anko.connectivityManager


object Network {
    fun isAvailable(): Boolean = App.getInstance().connectivityManager.activeNetworkInfo?.isAvailable ?: false
}
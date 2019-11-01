package com.devyk.common.ext

import android.content.Context
import android.provider.Settings

/**
 * Created by benny on 1/7/18.
 */
val Context.deviceId: String
    get() = Settings.Secure.getString(
            contentResolver,
            Settings.Secure.ANDROID_ID
    )
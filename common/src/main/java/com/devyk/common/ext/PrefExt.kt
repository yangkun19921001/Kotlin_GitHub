package com.devyk.common.ext

import com.devyk.common.App
import kotlin.reflect.jvm.jvmName

/**
 * Created by benny on 6/23/17.
 */
inline fun <reified R, T> R.pref(default: T) = Preference(App.getInstance(), "", default, R::class.jvmName)
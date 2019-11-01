package com.devyk.common.ext

import com.google.gson.Gson

/**
 * Created by benny on 1/20/18.
 */
inline fun <reified T> Gson.fromJson(json: String) = fromJson(json, T::class.java)
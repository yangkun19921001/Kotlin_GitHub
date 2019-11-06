package com.devyk.common.ext

import android.app.Activity
import android.support.v4.app.Fragment
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

fun <U, T> Activity.bindExtra(key: String) = BindLoader<U, T>(key)

fun <U, T> Fragment.bindArgument(key: String) = BindLoader<U, T>(key)

fun <U, T> android.app.Fragment.bindArgument(key: String) = BindLoader<U, T>(key)

private class IntentDelegate<in U, out T>(private val key: String) : ReadOnlyProperty<U, T> {
    override fun getValue(thisRef: U, property: KProperty<*>): T {
        @Suppress("UNCHECKED_CAST")
        return when (thisRef) {
            is Fragment -> thisRef.arguments?.get(key) as T
            is android.app.Fragment -> thisRef.arguments?.get(key) as T
            else -> (thisRef as Activity).intent?.extras?.get(key) as T
        }
    }



}

class BindLoader<in U, out T>(private val key: String) {
    operator fun provideDelegate(thisRef: U, prop: KProperty<*>): ReadOnlyProperty<U, T> {
        // 创建委托
        return IntentDelegate(key)
    }

}

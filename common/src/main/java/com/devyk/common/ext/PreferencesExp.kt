package com.devyk.common.ext

import android.content.Context
import java.nio.channels.spi.AbstractSelectionKey
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 * <pre>
 *     author  : devyk on 2019-10-29 16:13
 *     blog    : https://juejin.im/user/578259398ac2470061f3a3fb/posts
 *     github  : https://github.com/yangkun19921001
 *     mailbox : yang1001yk@gmail.com
 *     desc    : This is PreferencesExp  SharedpreFerences  扩展
 * </pre>
 */

class Preference<T>(var context: Context, val name: String, val default: T, val prefName: String = "defaut") :
    ReadWriteProperty<Any?, T> {


    /**
     * lazy 变量委托
     */
    private val prefs by lazy {
        context.getSharedPreferences(prefName, Context.MODE_PRIVATE)
    }


    override fun getValue(thisRef: Any?, property: KProperty<*>): T {
        return findPreference(findProperName(property));
    }

    /**
     * 根据 default 来判断存入的类型
     */
    private fun findPreference(key: String): T {
        return when (default) {
            is Long -> prefs.getLong(key, default)
            is Int -> prefs.getInt(key, default)
            is Boolean -> prefs.getBoolean(key, default)
            is String -> prefs.getString(key, default)
            else -> throw IllegalAccessException("Unsupported type.")
        } as T
    }

    /**
     * find 查询名称
     */
    private fun findProperName(property: KProperty<*>): String = if (name.isEmpty()) property.name else name

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        putPreference(findProperName(property), value);
    }

    /**
     * 根据 value 类型来获取数据
     * with: @see https://www.jianshu.com/p/87b338516358
     */
    private fun putPreference(key: String, value: T) {
        //定义 with 代表内部通过 this能够调用到 prefs.edit 返回的对象
        with(prefs.edit()) {
            when (value) {
                is Long -> putLong(key, value)
                is Int -> putInt(key, value)
                is Boolean -> putBoolean(key, value)
                is String -> putString(key, value)
                else -> throw IllegalAccessException("Unsupported type.")
            }.apply()

        }
    }
}
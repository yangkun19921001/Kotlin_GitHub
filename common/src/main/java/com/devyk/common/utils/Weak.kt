package com.devyk.common.utils

import android.util.Log
import java.lang.ref.WeakReference
import kotlin.reflect.KProperty

/**
 * <pre>
 *     author  : devyk on 2019-11-04 10:13
 *     blog    : https://juejin.im/user/578259398ac2470061f3a3fb/posts
 *     github  : https://github.com/yangkun19921001
 *     mailbox : yang1001yk@gmail.com
 *     desc    : This is Weak
 * </pre>
 */
class Weak <T : Any>(initializer: () -> T?) {
    var weakReference = WeakReference<T?>(initializer())

    constructor():this({
        null
    })

    operator fun getValue(thisRef: Any?, property: KProperty<*>): T? {
        Log.d("Weak Delegate","-----------getValue")
        return weakReference.get()
    }

    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: T?) {
        Log.d("Weak Delegate","-----------setValue")
        weakReference = WeakReference(value)
    }

}


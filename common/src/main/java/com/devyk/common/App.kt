package com.devyk.common

import android.app.Application
import android.content.Context
import com.devyk.common.App.instance as instance1

/**
 * <pre>
 *     author  : devyk on 2019-10-31 13:59
 *     blog    : https://juejin.im/user/578259398ac2470061f3a3fb/posts
 *     github  : https://github.com/yangkun19921001
 *     mailbox : yang1001yk@gmail.com
 *     desc    : This is App
 * </pre>
 */
object App {

    private lateinit var instance: Context


    fun init(context: Context) {
        instance = context
    }


    fun getInstance(): Context {
        return instance;
    }
}
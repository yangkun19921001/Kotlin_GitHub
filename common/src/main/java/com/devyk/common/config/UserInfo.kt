package com.devyk.common.config

import com.devyk.common.ext.pref

/**
 * <pre>
 *     author  : devyk on 2019-11-01 09:47
 *     blog    : https://juejin.im/user/578259398ac2470061f3a3fb/posts
 *     github  : https://github.com/yangkun19921001
 *     mailbox : yang1001yk@gmail.com
 *     desc    : This is UserInfo
 * </pre>
 */
object UserInfo {
    /**
     * 属性委托代理执行
     */
    var authID by pref(-1)
    var username by pref("")
    var password by pref("")
    var token by pref("")

    /**
     * 判断是否登录
     *
     */
    fun isLoginIn(): Boolean = token.isNotEmpty()
}
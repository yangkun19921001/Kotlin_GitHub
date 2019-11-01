package com.devyk.kotlin_github.config

import com.devyk.common.ext.PropertiesDelegate

/**
 * <pre>
 *     author  : devyk on 2019-10-31 11:11
 *     blog    : https://juejin.im/user/578259398ac2470061f3a3fb/posts
 *     github  : https://github.com/yangkun19921001
 *     mailbox : yang1001yk@gmail.com
 *     desc    : This is UserConfig 用户信息管理类
 * </pre>
 */
class UserConfig(path: String) : PropertiesDelegate.AbsProperties(path) {
    /**
     * 通过属性代理
     * 将接口的实现委托给另一个对象
     * 将属性访问器的实现委托给另一个对象
     */
    var name: String by PROPERTIES
    var email: String by PROPERTIES


    fun config(
        name: String = "yangkun19921001",
        email: String = "yang1001yk@gmail.com"

        /**
         * let 语法支持传递当前this 进去，默认就返回当前 this
         */
    ) = let {
        it.name = name
        it.email = email;
    }

}




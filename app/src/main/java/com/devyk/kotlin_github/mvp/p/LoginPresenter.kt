package com.devyk.kotlin_github.mvp.p

import BasePresenter
import com.devyk.common.config.UserInfo
import com.devyk.kotlin_github.mvp.m.AccountManager
import com.devyk.kotlin_github.mvp.v.login.LoginActivity

/**
 * <pre>
 *     author  : devyk on 2019-10-30 15:58
 *     blog    : https://juejin.im/user/578259398ac2470061f3a3fb/posts
 *     github  : https://github.com/yangkun19921001
 *     mailbox : yang1001yk@gmail.com
 *     desc    : This is LoginPresenter
 * </pre>
 */
class LoginPresenter : BasePresenter<LoginActivity>() {


    fun doLogin(name: String, passwd: String) {
        UserInfo.username = name
        UserInfo.password = passwd
        //开始登录
        v.onRequest()
        AccountManager.login()
            .subscribe(
                {
                    v.onSuccess()
                },
                {
                    v.onError(it)
                }
            )
    }

    fun checkUser(name: String, pwd:String):Boolean{
        return true
    }



    //进行用户信息重绘
    override fun onResume() {
        super.onResume()
            v.onDataInit(UserInfo.username,UserInfo.password)

    }
}
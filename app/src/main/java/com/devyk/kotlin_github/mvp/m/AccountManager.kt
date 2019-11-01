package com.devyk.kotlin_github.mvp.m

import com.bennyhuo.github.network.entities.User
import com.devyk.common.config.UserInfo
import com.devyk.common.ext.fromJson
import com.devyk.common.ext.pref
import com.devyk.common.ext.yes
import com.devyk.kotlin_github.api.AuthService
import com.devyk.kotlin_github.api.UserService
import com.devyk.kotlin_github.mvp.m.entity.AuthorizationReq
import com.devyk.kotlin_github.mvp.m.entity.AuthorizationRsp
import com.google.gson.Gson
import rx.Observable
import rx.Observer
import rx.android.schedulers.AndroidSchedulers

/**
 * <pre>
 *     author  : devyk on 2019-10-31 15:09
 *     blog    : https://juejin.im/user/578259398ac2470061f3a3fb/posts
 *     github  : https://github.com/yangkun19921001
 *     mailbox : yang1001yk@gmail.com
 *     desc    : This is AccountManager
 * </pre>
 */

/**
 * 定义一个回调
 */
interface OnAccountStateChangeLister {
    fun onLogin(user: User)
    fun onLogOut()
}


/**
 * 直接把 AccountManager 以 object 定义成单例
 */
object AccountManager {


    private var userJson by pref("");

    var currentUser: User? = null
        get() {
            if (field == null && userJson.isNotEmpty()) {
                field = Gson().fromJson<User>(userJson)
            }
            return field
        }
        set(value) {
            if (value == null) {
                userJson = ""
            } else {
                userJson = Gson().toJson(value)
            }

            field = value
        }
    val onAccountStateChangeLister = ArrayList<OnAccountStateChangeLister>()

    /**
     * 通知退出
     */
    private fun notifyLogout() {
        onAccountStateChangeLister.forEach { it.onLogOut() }
    }




    /**
     * 定义登录接口,V 层调用
     */
    fun login() =
        AuthService.createAuthorization(AuthorizationReq())
            .doOnNext {
                //请求之前判断，也可以进行进度显示
                it.token.isEmpty().yes {
                    throw AccountException(it)
                }

                //出现错误会执行此处
            }.retryWhen {
                it.flatMap {
                    if (it is AccountException) {
                        AuthService.deleteAuthorization(it.authorizationRsp.id)
                    } else {
                        Observable.error(it)
                    }
                }
            }.flatMap {
                UserInfo.token = it.token
                UserInfo.authID = it.id
                UserService.getAuthenticatedUser()
            }.observeOn(AndroidSchedulers.mainThread())
            .map {
                currentUser = it
                notifyLogin(it as User)
                println("login->$it")
            }


    private fun notifyLogin(user: User) {
        onAccountStateChangeLister.forEach {
            it.onLogin(user)
        }
    }


    class AccountException(val authorizationRsp: AuthorizationRsp) : Exception("already logged in.")

}
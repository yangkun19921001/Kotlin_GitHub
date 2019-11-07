package com.devyk.kotlin_github.mvp.p

import BasePresenter
import com.bennyhuo.common.log.logger
import com.devyk.kotlin_github.mvp.m.AccountManager
import com.devyk.kotlin_github.mvp.v.MainActivity
import org.jetbrains.anko.toast

/**
 * <pre>
 *     author  : devyk on 2019-11-01 11:10
 *     blog    : https://juejin.im/user/578259398ac2470061f3a3fb/posts
 *     github  : https://github.com/yangkun19921001
 *     mailbox : yang1001yk@gmail.com
 *     desc    : This is MainPersenter
 * </pre>
 */
class MainPersenter : BasePresenter<MainActivity>() {
    fun logout() {
        AccountManager.logout()
            .subscribe({
                v.onSuccess()
            }, {
                v.onError(it)
            })
    }

}
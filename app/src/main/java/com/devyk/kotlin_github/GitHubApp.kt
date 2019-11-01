package com.devyk.kotlin_github

import android.app.Application
import android.content.Context
import android.support.multidex.MultiDex
import android.support.v7.app.AppCompatDelegate
import com.bennyhuo.swipefinishable.SwipeFinishable
import com.devyk.common.App

/**
 * <pre>
 *     author  : devyk on 2019-10-30 16:09
 *     blog    : https://juejin.im/user/578259398ac2470061f3a3fb/posts
 *     github  : https://github.com/yangkun19921001
 *     mailbox : yang1001yk@gmail.com
 *     desc    : This is GitHubApp
 * </pre>
 */


class GitHubApp : Application(){




    /**
     * 声明一个伴生对象
     */
    companion object {
        /**
         * lateinit : 允许在构造函数之外初始化非空属性
         * 为应用级别的 APP 提供全局 Context
         */
        lateinit var instance: GitHubApp;

    }


    /**
     * 重写 Application onCreate 生命周期函数
     */
    override fun onCreate() {
        super.onCreate()
        //init GitHubApp 全局上下文
        instance = this;
        //滑动关闭 Activity
        SwipeFinishable.INSTANCE.init(this);
        //适配低版本向量图
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
        //init App 可以获取内部的 Application 级别的 Context
        App.init(this);



    }


    /**
     * 重写 Application 生命周期函数，刚刚创建出来，比 onCreate 更早
     */
    override fun attachBaseContext(base: Context?) {
        MultiDex.install(this);
        super.attachBaseContext(base)
    }
}
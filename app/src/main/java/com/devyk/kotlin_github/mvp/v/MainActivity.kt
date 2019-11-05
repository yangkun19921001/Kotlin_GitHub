package com.devyk.kotlin_github.mvp.v

import android.os.Bundle
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import com.bennyhuo.common.log.logger
import com.bennyhuo.github.network.entities.User
import com.bennyhuo.mvp.impl.BaseActivity
import com.devyk.common.config.UserInfo
import com.devyk.common.ext.*
import com.devyk.kotlin_github.R
import com.devyk.kotlin_github.config.NavViewItem
import com.devyk.kotlin_github.config.Themer
import com.devyk.kotlin_github.config.afterClosed
import com.devyk.kotlin_github.mvp.m.AccountManager
import com.devyk.kotlin_github.mvp.m.OnAccountStateChangeLister
import com.devyk.kotlin_github.mvp.p.MainPersenter
import com.devyk.kotlin_github.mvp.v.login.LoginActivity
import com.devyk.kotlin_github.widget.ActionBarController
import com.devyk.kotlin_github.widget.NavigationController
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

/**
 * 主页面
 */
class MainActivity : BaseActivity<MainPersenter>(), OnAccountStateChangeLister {


    /**
     * 初始化 actionBar
     */
    val actionBarController by lazy {
        ActionBarController(this)
    }




    /**
     * 初始化 抽屉
     */
    private val navigationController by lazy {
        NavigationController(navigationView, ::onNavItemChanged, ::handleNavigationHeaderClickEvent)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Themer.applyProperTheme(this)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)

        val actionBarDrawerToggle = ActionBarDrawerToggle(
            this,
            drawer_layout,
            toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        drawer_layout.setDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.syncState()

        initNavigationView()

        AccountManager.onAccountStateChangeLister.add(this)
    }

    private fun initNavigationView() {

        UserInfo.isLoginIn().yes {
            navigationController.useLoginLayout()
        }.otherwise {
            navigationController.useNoLoginLayout()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        AccountManager.onAccountStateChangeLister.remove(this);
    }

    private fun onNavItemChanged(navViewItem: NavViewItem) {
        drawer_layout.afterClosed {
            showFragment(R.id.fragmentContainer, navViewItem.fragmentClass, navViewItem.arguements)
            title = navViewItem.title
        }
    }

    private fun handleNavigationHeaderClickEvent() {
        UserInfo.isLoginIn().no {
            //如果没有登录
            startActivity<LoginActivity>()
        }.otherwise {
            //如果登录了，就弹框提示是否退出
            //使用协程
            MainScope().launch() {
                if (confirm("提醒", "是否确认退出？")) {
                    p.logout()

                }else{
                    toast("取消！")
                }

            }
        }

    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else
            super.onBackPressed()
    }


    override fun onRequest() {

    }

    override fun onSuccess() {
        toast("注销成功！")
    }

    override fun onError(error: Throwable) {
        error.message?.let { toast(it) }
        error.message?.let { logger.error("$TAG onError $it") }
    }

    override fun onLogin(user: User) {
        navigationController.useLoginLayout()
    }

    override fun onLogOut() {
        navigationController.useNoLoginLayout()
    }
}

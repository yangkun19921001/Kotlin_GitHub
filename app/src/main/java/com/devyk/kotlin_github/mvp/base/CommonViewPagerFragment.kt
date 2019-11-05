package com.devyk.kotlin_github.mvp.base

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.view.ViewPager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bennyhuo.github.network.entities.User
import com.devyk.common.config.UserInfo
import com.devyk.common.ext.yes
import com.devyk.kotlin_github.R
import com.devyk.kotlin_github.mvp.m.AccountManager
import com.devyk.kotlin_github.mvp.m.OnAccountStateChangeLister
import com.devyk.kotlin_github.mvp.v.MainActivity
import org.jetbrains.anko.support.v4.UI
import org.jetbrains.anko.support.v4._ViewPager
import org.jetbrains.anko.support.v4.viewPager
import org.jetbrains.anko.verticalLayout

/**
 * <pre>
 *     author  : devyk on 2019-11-01 17:55
 *     blog    : https://juejin.im/user/578259398ac2470061f3a3fb/posts
 *     github  : https://github.com/yangkun19921001
 *     mailbox : yang1001yk@gmail.com
 *     desc    : This is CommonViewPager
 * </pre>
 */
abstract class CommonViewPagerFragment : Fragment(), OnAccountStateChangeLister, ViewPagerFragmentConfig {

    /**
     * lateinit 延迟初始化 ViewPager
     */
    private lateinit var viewPager: ViewPager
    /**
     * 延迟加载 ViewPagerAdapter
     */
    private val viewPageAdapter by lazy {
        CommonViewPageAdapter(childFragmentManager)
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        //使用 anko 创建动态布局,比 XML 快 4 倍 但是能使用 XML 的地方还是尽力使用 XML 布局
        return UI {
            verticalLayout {
                viewPager = viewPager {
                    id = R.id.viewPager
                }
                viewPager.adapter = viewPageAdapter
            }
        }.view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // tablayout set ViewPager
        (activity as MainActivity).actionBarController.setupWithViewPager(viewPager)
        //根据配置添加 主页面
        viewPageAdapter.fragmentPages.addAll(
            if (UserInfo.isLoginIn()) {
                //交于子类决定
                getFragmentPagesLoggedIn()
            } else {
                //交于子类决定
                getFragmentPagesNotLoggedIn()
            }
        )
    }


    override fun onLogin(user: User) {
        viewPageAdapter.fragmentPages.clear()
        viewPageAdapter.fragmentPages.addAll(getFragmentPagesLoggedIn())
    }

    override fun onLogOut() {
        viewPageAdapter.fragmentPages.clear()
        viewPageAdapter.fragmentPages.addAll(getFragmentPagesNotLoggedIn())
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //添加登录成功或失败的监听
        AccountManager.onAccountStateChangeLister.add(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        AccountManager.onAccountStateChangeLister.remove(this)
    }
}


interface ViewPagerFragmentConfig {
    /**
     * 登录成功需要的模块
     */
    fun getFragmentPagesLoggedIn(): List<FragmentPage>

    /**
     * 登录失败需要的模块
     */
    fun getFragmentPagesNotLoggedIn(): List<FragmentPage>

}

/**
 * 封装模块 data
 */
data class FragmentPage(val fragment: Fragment, val title: String)
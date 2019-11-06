package com.devyk.kotlin_github.mvp.v.people

import android.os.Bundle
import com.devyk.kotlin_github.mvp.base.CommonViewPagerFragment
import com.devyk.kotlin_github.mvp.base.FragmentPage
import com.devyk.kotlin_github.mvp.m.AccountManager
import com.devyk.kotlin_github.mvp.m.PeoplePage

/**
 * <pre>
 *     author  : devyk on 2019-11-01 11:31
 *     blog    : https://juejin.im/user/578259398ac2470061f3a3fb/posts
 *     github  : https://github.com/yangkun19921001
 *     mailbox : yang1001yk@gmail.com
 *     desc    : This is PeopleFragment
 * </pre>
 */
class PeopleFragment : CommonViewPagerFragment() {


    /**
     * 当登录成功的时候加载 Followers、Following、ALL 3 个页面
     */
    override fun getFragmentPagesNotLoggedIn(): List<FragmentPage> {
        return listOf(
            FragmentPage(PeopleListFragment().also {
                it.arguments = Bundle().apply {
                    putString("type", PeoplePage.Type.ALL.name)

                }
            }, "All")
        )
    }

    /**
     * 登录失败的时候加载 PeopleListFragment ALL 页面
     */
    override fun getFragmentPagesLoggedIn(): List<FragmentPage> {
        //alse 会返回自身引用
        return listOf(
            FragmentPage(PeopleListFragment().also {
                it.arguments = Bundle().apply {
                    putString("login", AccountManager.currentUser?.login)
                    putString("type", PeoplePage.Type.FOLLOWER.name)
                }
            }, "Followers"),
            FragmentPage(PeopleListFragment().also {
                it.arguments = Bundle().apply {
                    putString("login", AccountManager.currentUser?.login)
                    putString("type", PeoplePage.Type.FOLLOWING.name)
                }
            }, "Following"),
            FragmentPage(PeopleListFragment().also {
                it.arguments = Bundle().apply {
                    putString("login", AccountManager.currentUser?.login)
                    putString("type", PeoplePage.Type.ALL.name)
                }
            }, "All")
        )
    }
}
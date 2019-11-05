package com.devyk.kotlin_github.mvp.v.repo

import android.os.Bundle
import android.support.v4.app.Fragment
import com.bennyhuo.mvp.impl.BaseFragment
import com.devyk.kotlin_github.mvp.base.CommonViewPagerFragment
import com.devyk.kotlin_github.mvp.base.FragmentPage
import com.devyk.kotlin_github.mvp.m.AccountManager


/**
 * <pre>
 *     author  : devyk on 2019-11-01 11:30
 *     blog    : https://juejin.im/user/578259398ac2470061f3a3fb/posts
 *     github  : https://github.com/yangkun19921001
 *     mailbox : yang1001yk@gmail.com
 *     desc    : This is RepoFragment
 * </pre>
 */
class RepoFragment : CommonViewPagerFragment() {
    override fun getFragmentPagesLoggedIn(): List<FragmentPage> =
        listOf(
            FragmentPage(RepoListFragment().apply {
                arguments = Bundle().apply { putParcelable("user", AccountManager.currentUser) }
            }, "My"),
            FragmentPage(RepoListFragment(), "All")
        )

    override fun getFragmentPagesNotLoggedIn(): List<FragmentPage> = listOf(FragmentPage(RepoListFragment(), "All"))


}
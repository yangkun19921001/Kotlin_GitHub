package com.devyk.kotlin_github.mvp.v.issue

import com.devyk.kotlin_github.mvp.base.CommonViewPagerFragment
import com.devyk.kotlin_github.mvp.base.FragmentPage


/**
 * <pre>
 *     author  : devyk on 2019-11-01 11:31
 *     blog    : https://juejin.im/user/578259398ac2470061f3a3fb/posts
 *     github  : https://github.com/yangkun19921001
 *     mailbox : yang1001yk@gmail.com
 *     desc    : This is MyIssueFragment
 * </pre>
 */
class MyIssueFragment : CommonViewPagerFragment() {
    override fun getFragmentPagesLoggedIn(): List<FragmentPage> {
        return listOf(FragmentPage(MyIssueListFragment(),"My"))

    }



    override fun getFragmentPagesNotLoggedIn(): List<FragmentPage> {
        return listOf(
            FragmentPage(MyIssueListFragment(),"My")
        )
    }


}
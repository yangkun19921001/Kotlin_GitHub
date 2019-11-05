package com.devyk.kotlin_github.mvp.p

import com.devyk.kotlin_github.mvp.base.CommonListPresenter
import com.devyk.kotlin_github.mvp.base.ListPage
import com.devyk.kotlin_github.mvp.m.MyIssuePage
import com.devyk.kotlin_github.mvp.m.entity.Issue
import com.devyk.kotlin_github.mvp.v.issue.MyIssueListFragment

/**
 * <pre>
 *     author  : devyk on 2019-11-05 10:53
 *     blog    : https://juejin.im/user/578259398ac2470061f3a3fb/posts
 *     github  : https://github.com/yangkun19921001
 *     mailbox : yang1001yk@gmail.com
 *     desc    : This is MyIssuePresenter
 * </pre>
 */
class MyIssuePresenter() : CommonListPresenter<Issue,MyIssueListFragment>(){
    override val listPage: ListPage<Issue> = MyIssuePage()
}
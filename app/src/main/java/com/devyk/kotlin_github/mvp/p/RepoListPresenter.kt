package com.devyk.kotlin_github.mvp.p

import com.devyk.kotlin_github.mvp.base.CommonListPresenter
import com.devyk.kotlin_github.mvp.base.ListPage
import com.devyk.kotlin_github.mvp.m.RepoListPage
import com.devyk.kotlin_github.mvp.m.entity.Repository
import com.devyk.kotlin_github.mvp.v.repo.RepoListFragment

/**
 * <pre>
 *     author  : devyk on 2019-11-05 13:06
 *     blog    : https://juejin.im/user/578259398ac2470061f3a3fb/posts
 *     github  : https://github.com/yangkun19921001
 *     mailbox : yang1001yk@gmail.com
 *     desc    : This is RepoListPresenter
 * </pre>
 */
class RepoListPresenter : CommonListPresenter<Repository,RepoListFragment>(){
    override val listPage: ListPage<Repository> by lazy {
        RepoListPage(v.user)
    }
}
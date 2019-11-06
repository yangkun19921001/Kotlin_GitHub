package com.devyk.kotlin_github.mvp.v.repo

import com.bennyhuo.github.network.entities.User
import com.devyk.common.ext.bindArgument
import com.devyk.kotlin_github.adapter.RepoListAdapter
import com.devyk.kotlin_github.mvp.base.CommonListFragment
import com.devyk.kotlin_github.mvp.m.entity.Repository
import com.devyk.kotlin_github.mvp.p.RepoListPresenter

/**
 * <pre>
 *     author  : devyk on 2019-11-05 13:05
 *     blog    : https://juejin.im/user/578259398ac2470061f3a3fb/posts
 *     github  : https://github.com/yangkun19921001
 *     mailbox : yang1001yk@gmail.com
 *     desc    : This is RepoListFragment
 * </pre>
 */
class RepoListFragment : CommonListFragment<Repository, RepoListPresenter>() {

    val user: User  by bindArgument("user")

    override val adapter = RepoListAdapter()


}
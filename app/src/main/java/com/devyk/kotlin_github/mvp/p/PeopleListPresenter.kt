package com.devyk.kotlin_github.mvp.p

import com.bennyhuo.github.network.entities.User
import com.devyk.kotlin_github.mvp.base.CommonListPresenter
import com.devyk.kotlin_github.mvp.base.ListPage
import com.devyk.kotlin_github.mvp.m.PeoplePage
import com.devyk.kotlin_github.mvp.m.PeoplePageParams
import com.devyk.kotlin_github.mvp.v.people.PeopleListFragment

/**
 * <pre>
 *     author  : devyk on 2019-11-06 09:58
 *     blog    : https://juejin.im/user/578259398ac2470061f3a3fb/posts
 *     github  : https://github.com/yangkun19921001
 *     mailbox : yang1001yk@gmail.com
 *     desc    : This is PeopleListPresenter
 * </pre>
 */
class PeopleListPresenter : CommonListPresenter<User,PeopleListFragment>() {
    override val listPage: ListPage<User> by lazy {
        PeoplePage(PeoplePageParams(v.type,v.login))
    }
}
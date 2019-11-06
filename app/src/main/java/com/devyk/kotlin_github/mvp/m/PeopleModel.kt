package com.devyk.kotlin_github.mvp.m

import com.bennyhuo.github.network.entities.User
import com.devyk.kotlin_github.api.UserService
import com.devyk.kotlin_github.mvp.base.GitHubPaging
import com.devyk.kotlin_github.mvp.base.ListPage
import io.reactivex.Observable

/**
 * <pre>
 *     author  : devyk on 2019-11-06 09:52
 *     blog    : https://juejin.im/user/578259398ac2470061f3a3fb/posts
 *     github  : https://github.com/yangkun19921001
 *     mailbox : yang1001yk@gmail.com
 *     desc    : This is PeopleModel
 * </pre>
 */

class PeoplePageParams(val type: String, val login: String?)

class PeoplePage(val params: PeoplePageParams) : ListPage<User>() {
    enum class Type {
        FOLLOWER, FOLLOWING, ALL
    }

    override fun getData(page: Int): Observable<GitHubPaging<User>> =
        when (Type.valueOf(params.type)) {
            Type.FOLLOWER -> UserService.followers(params.login!!, page = page)
            Type.FOLLOWING -> UserService.following(params.login!!, page = page)
            Type.ALL -> UserService.allUsers(data.since)
        }

}
package com.devyk.kotlin_github.mvp.m

import com.bennyhuo.github.network.entities.User
import com.devyk.common.ext.otherwise
import com.devyk.common.ext.yes
import com.devyk.common.utils.format
import com.devyk.kotlin_github.api.RepositoryService
import com.devyk.kotlin_github.mvp.base.GitHubPaging
import com.devyk.kotlin_github.mvp.base.ListPage
import com.devyk.kotlin_github.mvp.m.entity.Repository
import io.reactivex.Observable
import java.util.*

/**
 * <pre>
 *     author  : devyk on 2019-11-05 13:09
 *     blog    : https://juejin.im/user/578259398ac2470061f3a3fb/posts
 *     github  : https://github.com/yangkun19921001
 *     mailbox : yang1001yk@gmail.com
 *     desc    : This is RepoModel
 * </pre>
 */
class RepoListPage(val owner: User?) : ListPage<Repository>() {
    override fun getData(page: Int): Observable<GitHubPaging<Repository>> {
        return if (owner == null) {
            RepositoryService.allRepositories(page, "pushed:<" + Date().format("yyyy-MM-dd"))
                .map {
                    GitHubPaging<Repository>().also { git ->
                        git.addAll(it.getElements())
                    }
                }
        } else {
            RepositoryService.listRepositoriesOfUser(owner.login, page)

        }
    }


}



package com.devyk.kotlin_github.mvp.m

import com.devyk.kotlin_github.api.IssueService
import com.devyk.kotlin_github.mvp.base.GitHubPaging
import com.devyk.kotlin_github.mvp.base.ListPage
import com.devyk.kotlin_github.mvp.m.entity.Issue
import io.reactivex.Observable

/**
 * <pre>
 *     author  : devyk on 2019-11-05 10:55
 *     blog    : https://juejin.im/user/578259398ac2470061f3a3fb/posts
 *     github  : https://github.com/yangkun19921001
 *     mailbox : yang1001yk@gmail.com
 *     desc    : This is IssueModel
 * </pre>
 */
class MyIssuePage : ListPage<Issue>() {
    override fun getData(page: Int): Observable<GitHubPaging<Issue>> = IssueService.listIssuesOfAuthenticatedUser(page = page)

}
package com.devyk.kotlin_github.mvp.base

import io.reactivex.Observable


/**
 * <pre>
 *     author  : devyk on 2019-11-04 16:00
 *     blog    : https://juejin.im/user/578259398ac2470061f3a3fb/posts
 *     github  : https://github.com/yangkun19921001
 *     mailbox : yang1001yk@gmail.com
 *     desc    : This is DataProvider
 * </pre>
 */
interface DataProvider<DataType> {
    fun getData(page: Int): Observable<GitHubPaging<DataType>>
}
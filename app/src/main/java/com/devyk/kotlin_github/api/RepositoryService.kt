package com.devyk.kotlin_github.api

import com.devyk.common.network.FORCE_NETWORK
import com.devyk.common.network.RETROFIT
import com.devyk.kotlin_github.mvp.base.GitHubPaging
import com.devyk.kotlin_github.mvp.m.entity.Repository
import com.devyk.kotlin_github.mvp.m.entity.SearchRepositories
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * <pre>
 *     author  : devyk on 2019-11-05 13:18
 *     blog    : https://juejin.im/user/578259398ac2470061f3a3fb/posts
 *     github  : https://github.com/yangkun19921001
 *     mailbox : yang1001yk@gmail.com
 *     desc    : This is RepositoryService
 * </pre>
 */

interface RepositoryApi{

    @GET("/users/{owner}/repos?type=all")
    fun listRepositoriesOfUser(@Path("owner") owner: String, @Query("page") page: Int = 1, @Query("per_page") per_page: Int = 20): Observable<GitHubPaging<Repository>>

    @GET("/search/repositories?order=desc&sort=updated")
    fun allRepositories(@Query("page") page: Int = 1, @Query("q") q: String, @Query("per_page") per_page: Int = 20): Observable<SearchRepositories>

    @GET("/repos/{owner}/{repo}")
    fun getRepository(@Path("owner") owner: String, @Path("repo") repo: String, @Query(FORCE_NETWORK) forceNetwork: Boolean = false): Observable<Repository>
}

object RepositoryService : RepositoryApi by RETROFIT.create(RepositoryApi::class.java)
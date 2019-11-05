package com.devyk.kotlin_github.api

import com.devyk.common.network.RETROFIT
import com.devyk.kotlin_github.mvp.base.GitHubPaging
import com.devyk.kotlin_github.mvp.m.entity.Issue
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by benny on 2/4/18.
 */
interface IssueApi{
    @GET("/issues?filter=all&state=all")
    fun listIssuesOfAuthenticatedUser(@Query("page") page: Int = 1, @Query("per_page") per_page: Int = 20): Observable<GitHubPaging<Issue>>

}

object IssueService : IssueApi by RETROFIT.create(IssueApi::class.java)
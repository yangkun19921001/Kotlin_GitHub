package com.devyk.kotlin_github.api

import com.bennyhuo.github.network.entities.User
import com.devyk.common.network.RETROFIT
import com.devyk.kotlin_github.mvp.base.GitHubPaging
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface UserApi {

    @GET("/user")
    fun getAuthenticatedUser(): Observable<User>

    @GET("/users")
    fun allUsers(@Query("since") since: Int, @Query("per_page") per_page: Int = 20): Observable<GitHubPaging<User>>

    @GET("/users/{login}/following")
    fun following(@Path("login") login: String, @Query("page") page: Int = 1, @Query("per_page") per_page: Int = 20): Observable<GitHubPaging<User>>

    @GET("/users/{login}/followers")
    fun followers(@Path("login") login: String, @Query("page") page: Int = 1): Observable<GitHubPaging<User>>


}

object UserService: UserApi by RETROFIT.create(UserApi::class.java)
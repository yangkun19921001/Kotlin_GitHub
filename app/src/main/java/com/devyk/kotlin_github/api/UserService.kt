package com.devyk.kotlin_github.api

import com.bennyhuo.github.network.entities.User
import com.devyk.common.network.RETROFIT
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface UserApi {

    @GET("/user")
    fun getAuthenticatedUser(): Observable<User>


}

object UserService: UserApi by RETROFIT.create(UserApi::class.java)
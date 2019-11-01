package com.devyk.kotlin_github.api

import com.devyk.common.network.RETROFIT
import com.devyk.kotlin_github.mvp.m.entity.AuthorizationReq
import com.devyk.kotlin_github.mvp.m.entity.AuthorizationRsp
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.PUT
import retrofit2.http.Path
import rx.Observable

interface AuthApi{

    @PUT("/authorizations/clients/${Configs.Account.clientId}/{fingerPrint}")
    fun createAuthorization(@Body req: AuthorizationReq, @Path("fingerPrint") fingerPrint: String = Configs.Account.fingerPrint)
            : Observable<AuthorizationRsp>

    @DELETE("/authorizations/{id}")
    fun deleteAuthorization(@Path("id") id: Int): Observable<Response<Any>>

}

object AuthService: AuthApi by RETROFIT.create(AuthApi::class.java)
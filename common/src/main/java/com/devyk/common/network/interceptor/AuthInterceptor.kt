package com.devyk.common.network.interceptor

import android.accounts.AccountManager
import android.util.Base64
import com.devyk.common.config.UserInfo
import okhttp3.Interceptor
import okhttp3.Interceptor.Chain
import okhttp3.Response

class AuthInterceptor: Interceptor{
    override fun intercept(chain: Chain): Response {
        val original = chain.request()
        return chain.proceed(original.newBuilder()
                .apply {
                    when{
                        original.url().pathSegments().contains("authorizations") ->{
                            val userCredentials = UserInfo.username + ":" + UserInfo.password
                            val auth = "Basic " + String(Base64.encode(userCredentials.toByteArray(), Base64.DEFAULT)).trim()
                            header("Authorization", auth)
                        }
                        UserInfo.isLoginIn() -> {
                            val auth = "Token " + UserInfo.token
                            header("Authorization", auth)
                        }
                        else -> removeHeader("Authorization")
                    }
                }
                .build())
        return chain.proceed(chain.request())
    }
}
package com.devyk.common.network.interceptor


import com.bennyhuo.common.log.logger
import com.devyk.common.App
import com.devyk.common.ext.no
import com.devyk.common.ext.otherwise
import com.devyk.common.ext.yes
import com.devyk.common.network.FORCE_NETWORK
import com.devyk.common.utils.Network
import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.Interceptor.Chain
import okhttp3.Response
import java.util.concurrent.TimeUnit


class CacheInterceptor : Interceptor {
    override fun intercept(chain: Chain): Response {
        var request = chain.request()
        request = Network.isAvailable()
                .no {
                    request.newBuilder()
                            .cacheControl(CacheControl.FORCE_CACHE)
                            .build()
                }
                .otherwise {
                    request.url().queryParameter(FORCE_NETWORK)?.toBoolean()?.let {
                        it.yes {
                            //注意 noCache | noStore，前者不会读缓存；后者既不读缓存，也不对响应进行缓存
                            //尽管看上去 noCache 比较符合我们的需求，但服务端仍然可能返回服务端的缓存
                            //request.newBuilder().cacheControl(CacheControl.Builder().noCache().build()).build()
                            request.newBuilder().cacheControl(CacheControl.Builder().maxAge(0, TimeUnit.SECONDS).build()).build()
                        }.otherwise {
                            request
                        }
                    } ?: request
                }

        request = request.newBuilder().url(request.url().newBuilder().removeAllQueryParameters(FORCE_NETWORK).build()).build()
        return chain.proceed(request).also { response ->
            logger.error("Cache: ${response.cacheResponse()}, Network: ${response.networkResponse()}")

        }
    }
}
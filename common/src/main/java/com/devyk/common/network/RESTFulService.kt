package com.devyk.common.network

import com.bennyhuo.github.network.compat.enableTls12OnPreLollipop
import com.devyk.common.App
import com.devyk.common.ext.ensureDir
import com.devyk.common.network.interceptor.AcceptInterceptor
import com.devyk.common.network.interceptor.AuthInterceptor
import com.devyk.common.network.interceptor.CacheInterceptor
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import rx.schedulers.Schedulers
import java.io.File
import java.util.concurrent.TimeUnit

/**
 * <pre>
 *     author  : devyk on 2019-10-31 12:53
 *     blog    : https://juejin.im/user/578259398ac2470061f3a3fb/posts
 *     github  : https://github.com/yangkun19921001
 *     mailbox : yang1001yk@gmail.com
 *     desc    : This is RESTFulService
 * </pre>
 */

private const val BASE_URL = "https://api.github.com"


//通过一个 QueryParameter 让 CacheInterceptor 添加 no-cache
public const val FORCE_NETWORK = "forceNetwork"
/**
 * 缓存大小
 */
private const val OKHTTP_CACHE_SIZE  = 1024 * 1024 * 10L

/**
 * 定义一个 cache
 */
private val cacheFile by lazy {
    File(App.getInstance().cacheDir,"Kotlin_GitHub").apply { ensureDir() }
}

/**
 * 定义 retrofit 对象
 */
val RETROFIT by lazy {
    Retrofit.Builder()
            //支持 Rxjava
        .addCallAdapterFactory(RxJavaCallAdapterFactory.createWithScheduler(Schedulers.io()))
            //支持数据转换
        .addConverterFactory(GsonConverterFactory.create())
        .client(OKHTTPCLIENT)
        .baseUrl(BASE_URL)
        .build()


}



/**
 * 定义 OKHttpClient
 */
private val OKHTTPCLIENT by lazy {
    OkHttpClient.Builder()
        .addInterceptor(AcceptInterceptor())
        .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        .addInterceptor(CacheInterceptor())
        .addInterceptor(AuthInterceptor())
        .connectTimeout(60,TimeUnit.SECONDS)
        .readTimeout(60,TimeUnit.SECONDS)
        .writeTimeout(60,TimeUnit.SECONDS)
        .cache(Cache(cacheFile,OKHTTP_CACHE_SIZE))
        .enableTls12OnPreLollipop()
        .build()
}
package com.bennyhuo.github.network.compat

import android.os.Build
import android.util.Log
import com.devyk.common.compat.Tls12SocketFactory
import okhttp3.ConnectionSpec
import okhttp3.OkHttpClient
import okhttp3.TlsVersion
import javax.net.ssl.SSLContext


/**
 * Created by benny on 2018/4/25.
 */
fun OkHttpClient.Builder.enableTls12OnPreLollipop(): OkHttpClient.Builder {
    if (Build.VERSION.SDK_INT in 16..21) {
        try {
            val sc = SSLContext.getInstance("TLSv1.2")
            sc.init(null, null, null)
            sslSocketFactory(Tls12SocketFactory(sc.getSocketFactory()))

            val cs = ConnectionSpec.Builder(ConnectionSpec.MODERN_TLS)
                    .tlsVersions(TlsVersion.TLS_1_2)
                    .build()

            val specs = listOf(cs, ConnectionSpec.COMPATIBLE_TLS, ConnectionSpec.CLEARTEXT)
            connectionSpecs(specs)
        } catch (exc: Exception) {
            Log.e("OkHttpTLSCompat", "Error while setting TLS 1.2", exc)
        }

    }

    return this
}
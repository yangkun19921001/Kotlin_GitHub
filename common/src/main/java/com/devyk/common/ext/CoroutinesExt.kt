package com.devyk.common.ext

import kotlinx.coroutines.*



suspend fun <T> Deferred<T>.awaitOrError(): Result<T>{
    return try {
        Result.of(await())
    }catch(e: Exception){
        Result.of(e)
    }
}
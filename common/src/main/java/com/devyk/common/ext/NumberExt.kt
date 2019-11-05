package com.devyk.common.ext

/**
 * <pre>
 *     author  : devyk on 2019-11-05 13:57
 *     blog    : https://juejin.im/user/578259398ac2470061f3a3fb/posts
 *     github  : https://github.com/yangkun19921001
 *     mailbox : yang1001yk@gmail.com
 *     desc    : This is NumberExt
 * </pre>
 */
fun Int.toKilo(): String{
    return if(this > 700){
        "${(Math.round(this / 100f) / 10f)}k"
    }else{
        "$this"
    }
}

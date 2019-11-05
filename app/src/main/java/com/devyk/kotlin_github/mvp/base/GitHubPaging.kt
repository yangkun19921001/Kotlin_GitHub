package com.devyk.kotlin_github.mvp.base

import com.bennyhuo.common.log.logger
import okhttp3.HttpUrl

/**
 * <pre>
 *     author  : devyk on 2019-11-04 14:58
 *     blog    : https://juejin.im/user/578259398ac2470061f3a3fb/posts
 *     github  : https://github.com/yangkun19921001
 *     mailbox : yang1001yk@gmail.com
 *     desc    : This is GitHubPaging
 * </pre>
 */

class GitHubPaging<T> : ArrayList<T>() {
    companion object {
        const val URL_PATTERN = """(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]"""
    }

    private val relMap = HashMap<String, String?>().withDefault {
        null
    }

    private val first by relMap
    private val last by relMap
    private val next by relMap
    private val prev by relMap

    val isLast
        get() = last == null

    val hasNext
        get() = next != null

    val isFirst
        get() = first == null

    val hasFirst
        get() = first == null

    var since: Int = 0

    operator fun get(key: String): String? = relMap[key]

    fun setupLinks(link: String) {
        logger.warn("setupLinks:$link")

        Regex("""<($URL_PATTERN)>; rel="(\w+)"""").findAll(link).asIterable()
            .map { matchResult ->
                val url = matchResult.groupValues[1]
                relMap[matchResult.groupValues[3]] = url
                if (url.contains("since")) {
                    HttpUrl.parse(url)?.queryParameter("since")?.let {
                        since = it.toInt()
                    }
                }
                logger.warn("${matchResult.groupValues[3]} => ${matchResult.groupValues[1]}")
            }
    }

    fun mergeData(paging: GitHubPaging<T>): GitHubPaging<T> {
        addAll(paging)
        relMap.clear()
        relMap.putAll(paging.relMap)
        since = paging.since
        return this
    }
}
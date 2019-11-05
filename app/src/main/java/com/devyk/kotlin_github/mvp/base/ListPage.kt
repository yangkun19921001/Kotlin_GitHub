package com.devyk.kotlin_github.mvp.base

import com.bennyhuo.common.log.logger
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * <pre>
 *     author  : devyk on 2019-11-04 15:56
 *     blog    : https://juejin.im/user/578259398ac2470061f3a3fb/posts
 *     github  : https://github.com/yangkun19921001
 *     mailbox : yang1001yk@gmail.com
 *     desc    : This is ListPage
 * </pre>
 */

abstract class ListPage<DataType> : DataProvider<DataType> {

    //声明伴生对象
    companion object {
        const val PAGE_SIZE = 20
    }

    var currentPage = 1
        private set

    val data = GitHubPaging<DataType>()

    fun loadMore() = getData(currentPage + 1)
        .doOnNext {
            currentPage + 1
        }.doOnError {
            logger.error("loadMore Error", it)
        }.map {
            data.mergeData(it)
            data
        }

    fun loadFromFirst(pageCount: Int = currentPage) =
        Observable.range(1, pageCount)
            //concatMap() 转发出来的事件是有序的，而 flatMap() 是无序的
            .concatMap {
                getData(it)
            }.doOnError {
                logger.error("loadFromFirst,pageCount=$pageCount", it)
                //与 scan() 操作符的作用也是将发送数据以一定逻辑聚合起来，
                // 这两个的区别在于 scan() 每处理一次数据就会将事件发送给观察者，
                // 而 reduce() 会将所有数据聚合在一起才会发送事件给观察者。
            }.reduce { acc, page ->
                acc.mergeData(page)
            }.doAfterSuccess {
                data.clear()
                data.mergeData(it)
            }


}
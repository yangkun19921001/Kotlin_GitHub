package com.devyk.kotlin_github.mvp.base

import BasePresenter
import com.devyk.common.ext.otherwise
import com.devyk.common.ext.yes
import io.reactivex.disposables.Disposable
import org.reactivestreams.Subscription

/**
 * <pre>
 *     author  : devyk on 2019-11-04 14:20
 *     blog    : https://juejin.im/user/578259398ac2470061f3a3fb/posts
 *     github  : https://github.com/yangkun19921001
 *     mailbox : yang1001yk@gmail.com
 *     desc    : This is CommonListPresenter
 * </pre>
 */
abstract class CommonListPresenter<DataType, out View : CommonListFragment<DataType, CommonListPresenter<DataType, View>>> :
    BasePresenter<View>() {
    abstract val listPage: ListPage<DataType>

    private var firstInView = true
    private val subscriptionList = ArrayList<Disposable>()

    /**
     * 初始化数据
     */
    fun initData() {
        listPage.loadFromFirst()
            .subscribe(
                {
                    it.isEmpty().yes {
                        v.onDataInitWithNothing()
                    }.otherwise {
                        v.onDataInit(it)
                    }
                }, {
                    v.onDataInitWithError(it.message ?: it.toString())
                }).let(subscriptionList::add)
    }

    /**
     * 刷新数据
     */
    fun refreshData() {
        listPage.loadFromFirst()
            .subscribe(
                {
                    it.isEmpty().yes {
                        v.onDataRefreshWithNothing()
                    }.otherwise {
                        v.onDataRefresh(it)
                    }
                }, {
                    v.onDataRefreshWithError(it.message ?: it.toString())
                }).let(subscriptionList::add)

    }

    /**
     * 加载更多
     */
    fun loadMore() {
        listPage.loadMore()
            .subscribe(
                { v.onMoreDataLoaded(it) },
                { v.onMoreDataLoadedWithError(it.message ?: it.toString()) }
            )

    }

    override fun onResume() {
        super.onResume()
        (!firstInView).yes {
            refreshData()
        }
        firstInView = false
    }

    override fun onDestroy() {
        super.onDestroy()
        subscriptionList.forEach {
            it.dispose()
        }
        subscriptionList.clear()

    }

}
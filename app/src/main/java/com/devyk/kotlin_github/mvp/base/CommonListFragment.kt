package com.devyk.kotlin_github.mvp.base

import android.os.Bundle
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.bennyhuo.mvp.impl.BaseFragment
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.devyk.common.weiget.ErrorInfoView
import com.devyk.kotlin_github.R
import com.github.jdsjlzx.recyclerview.LuRecyclerViewAdapter
import kotlinx.android.synthetic.main.fragment_common_list.*
import org.jetbrains.anko.sdk15.coroutines.onClick
import org.jetbrains.anko.support.v4.onRefresh
import org.jetbrains.anko.support.v4.toast

/**
 * <pre>
 *     author  : devyk on 2019-11-04 14:20
 *     blog    : https://juejin.im/user/578259398ac2470061f3a3fb/posts
 *     github  : https://github.com/yangkun19921001
 *     mailbox : yang1001yk@gmail.com
 *     desc    : This is CommonListFragment
 * </pre>
 */
abstract class CommonListFragment<DataType, out Presenter : CommonListPresenter<DataType, CommonListFragment<DataType, Presenter>>> :
    BaseFragment<Presenter>() {

    /**
     * 获取适配器
     */
    protected abstract val adapter: CommonListAdapter<DataType>

    protected val errorInfoView by lazy {
        ErrorInfoView(rootView)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_common_list, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        refreshView.setColorSchemeResources(
            R.color.google_red,
            R.color.google_yellow,
            R.color.google_green,
            R.color.google_blue
        )
        recyclerView.adapter = LuRecyclerViewAdapter(adapter)
        recyclerView.setLoadMoreEnabled(true)
        recyclerView.layoutManager = LinearLayoutManager(view?.context, LinearLayoutManager.VERTICAL, false)
        recyclerView.itemAnimator = DefaultItemAnimator()

        refreshView.isRefreshing = true

        recyclerView.setOnLoadMoreListener(p::loadMore)

        refreshView.onRefresh(p::refreshData)
        p.initData()
    }

    fun setLoadMoreEnable(isEnable: Boolean) {
        recyclerView.setLoadMoreEnabled(isEnable)
    }



    override fun onRequest() {

    }

    override fun onSuccess() {
    }

    override fun onError(error: Throwable) {
    }

    fun onDataInitWithNothing() {


    }

    fun onDataInit(data: GitHubPaging<DataType>) {
        adapter.data.clear()
        adapter.data.addAll(data)
        recyclerView.setNoMore(data.isLast)
        recyclerView.refreshComplete(ListPage.PAGE_SIZE)
        refreshView.isRefreshing = false
        dismissError()
    }

    private fun dismissError() {
        errorInfoView.dismiss()

    }

    fun onDataInitWithError(error: String) {
        showError("访问出错：$error")

    }

    fun onDataRefreshWithNothing() {
        showError("No Data.")
        recyclerView.setNoMore(true)
        recyclerView.refreshComplete(ListPage.PAGE_SIZE)
        refreshView.isRefreshing = false
        errorInfoView.isClickable = false
    }

    fun onDataRefresh(data: GitHubPaging<DataType>) {
        onDataInit(data)
    }

    fun onDataRefreshWithError(error: String) {
        if (adapter.data.isEmpty()) {
            showError(error)
            errorInfoView.onClick {
                p.initData()
            }
        } else {
            toast(error)
        }

    }

    fun onMoreDataLoaded(data: GitHubPaging<DataType>) {
        adapter.data.update(data)
        recyclerView.refreshComplete(ListPage.PAGE_SIZE)
        recyclerView.setNoMore(data.isLast)
        dismissError()

    }

    fun onMoreDataLoadedWithError(error: String) {
        showError(error)
        recyclerView.refreshComplete(ListPage.PAGE_SIZE)
        errorInfoView.onClick {
            p.initData()
        }

    }

    protected open fun showError(error: String) {
        errorInfoView.show(error)
    }
}
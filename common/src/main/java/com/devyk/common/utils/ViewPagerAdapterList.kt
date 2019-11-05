package com.devyk.common.utils

import android.support.v4.view.PagerAdapter

/**
 * <pre>
 *     author  : devyk on 2019-11-01 18:05
 *     blog    : https://juejin.im/user/578259398ac2470061f3a3fb/posts
 *     github  : https://github.com/yangkun19921001
 *     mailbox : yang1001yk@gmail.com
 *     desc    : This is ViewPagerAdapterList
 * </pre>
 */
class ViewPagerAdapterList<T>(val adapter: PagerAdapter) : ArrayList<T>() {

    override fun removeAt(index: Int): T {
        return super.removeAt(index).apply { adapter.notifyDataSetChanged() }
    }

    override fun remove(element: T): Boolean {
        return super.remove(element).apply { adapter.notifyDataSetChanged() }
    }

    override fun removeRange(fromIndex: Int, toIndex: Int) {
        super.removeRange(fromIndex, toIndex).apply { adapter.notifyDataSetChanged() }
    }

    override fun removeAll(elements: Collection<T>): Boolean {
        return super.removeAll(elements).apply { adapter.notifyDataSetChanged() }
    }

    override fun add(element: T): Boolean {
        return super.add(element).apply { adapter.notifyDataSetChanged() }
    }

    override fun add(index: Int, element: T) {
        super.add(index, element).apply { adapter.notifyDataSetChanged() }
    }

    override fun addAll(elements: Collection<T>): Boolean {
        return super.addAll(elements).apply { adapter.notifyDataSetChanged() }
    }

    override fun addAll(index: Int, elements: Collection<T>): Boolean {
        return super.addAll(index, elements).apply { adapter.notifyDataSetChanged() }
    }

    fun update(elements: Collection<T>) {
        super.clear()
        super.addAll(elements).let { adapter.notifyDataSetChanged() }
    }
}
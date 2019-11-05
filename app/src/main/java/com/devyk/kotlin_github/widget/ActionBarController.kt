package com.devyk.kotlin_github.widget

import android.database.DataSetObserver
import android.support.design.widget.TabLayout
import android.support.v4.view.ViewPager
import android.view.View
import com.devyk.common.utils.Weak
import com.devyk.kotlin_github.mvp.v.MainActivity
import kotlinx.android.synthetic.main.app_bar_main.*
import java.lang.ref.WeakReference

class ActionBarController(mainActivity: MainActivity) {


    /**
     * 使用弱引用对 MainActivity 包裹，避免内存泄漏
     */
    var activity by Weak<MainActivity>(
        initializer = { mainActivity }
    )


    /**
     * 初始化 tabLayout
     */
    private val tablayout by lazy {
        activity?.tabLayout
    }



    /**
     * 定义一个观察者模式
     */
    class ViewPagerDataSetObserver(val tabLayout: TabLayout?) : DataSetObserver() {
        var viewPager: ViewPager? = null
            set(value) {
                viewPager?.adapter?.unregisterDataSetObserver(this)
                value?.adapter?.registerDataSetObserver(this)
                field = value
            }

        /**
         * 观察到改变，动态更新布局
         */
        override fun onChanged() {
            super.onChanged()
            viewPager?.run {
                //传递 it
                if (viewPager?.adapter?.count ?: 0 <= 1) {
                    tabLayout?.visibility = View.GONE
                } else {
                    tabLayout?.visibility = View.VISIBLE
                    tabLayout?.tabMode = if(viewPager?.adapter?.count?:0 > 3) TabLayout.MODE_SCROLLABLE else TabLayout.MODE_FIXED
                }
            }
        }
    }


    private val dataSetObserver by lazy {
        ViewPagerDataSetObserver(tablayout)
    }

    fun setupWithViewPager(viewPager: ViewPager?){
        viewPager.let { dataSetObserver::viewPager ::set}.run { tablayout?.visibility = View.GONE }
        tablayout?.setupWithViewPager(viewPager);
    }

}

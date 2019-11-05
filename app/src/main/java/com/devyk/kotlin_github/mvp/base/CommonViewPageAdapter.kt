package com.devyk.kotlin_github.mvp.base

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.PagerAdapter
import android.view.View
import com.devyk.common.utils.ViewPagerAdapterList

class CommonViewPageAdapter(childFragmentManager: FragmentManager) : FragmentPagerAdapter(childFragmentManager) {

    val fragmentPages = ViewPagerAdapterList<FragmentPage>(this)

    override fun getItem(p0: Int): Fragment = fragmentPages[p0].fragment


    override fun getCount(): Int =
        fragmentPages.size

    override fun getItemPosition(`object`: Any): Int {
        for ((index, page) in fragmentPages.withIndex()) {
            if (`object` == page.fragment)
                return index

        }
        return PagerAdapter.POSITION_NONE
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return fragmentPages[position].title
    }
}
package com.devyk.kotlin_github.config

import android.annotation.SuppressLint
import android.support.annotation.IdRes
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v4.view.ViewCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.view.menu.MenuItemImpl
import android.view.View
import com.bennyhuo.common.log.logger
import com.devyk.common.ext.otherwise
import com.devyk.common.ext.yes


inline fun NavigationView.doOnLayoutAvailable(crossinline block: () -> Unit) {
    ViewCompat.isLaidOut(this).yes {
        block()
    }.otherwise {
        addOnLayoutChangeListener(object : View.OnLayoutChangeListener {
            override fun onLayoutChange(v: View?, left: Int, top: Int, right: Int, bottom: Int, oldLeft: Int, oldTop: Int, oldRight: Int, oldBottom: Int) {
                removeOnLayoutChangeListener(this)
                block()
            }
        })
    }
}


/**
 * 选择指定的菜单，并执行相应的操作
 */
@SuppressLint("RestrictedApi")
fun NavigationView.selectItem(@IdRes resId: Int){
    doOnLayoutAvailable {
        logger.debug("select Item: ${NavViewItem[resId].title}")
        setCheckedItem(resId)
        (menu.findItem(resId) as MenuItemImpl)()
    }
}

inline fun DrawerLayout.afterClosed(crossinline block: () -> Unit){
    if(isDrawerOpen(GravityCompat.START)) {
        closeDrawer(GravityCompat.START)
        addDrawerListener(
                object : DrawerLayout.DrawerListener {
                    override fun onDrawerStateChanged(newState: Int) = Unit
                    override fun onDrawerSlide(drawerView: View, slideOffset: Float) = Unit
                    override fun onDrawerOpened(drawerView: View) = Unit

                    override fun onDrawerClosed(drawerView: View) {
                        removeDrawerListener(this)
                        block()
                    }
                })
    } else {
        block()
    }
}

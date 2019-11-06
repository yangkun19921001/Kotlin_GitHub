package com.devyk.kotlin_github.mvp.base

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.GestureDetector
import android.view.MenuItem
import android.view.MotionEvent
import com.bennyhuo.swipefinishable.SwipeFinishable
import com.devyk.kotlin_github.R
import com.devyk.kotlin_github.config.Themer
import org.jetbrains.anko.dip

/**
 * <pre>
 *     author  : devyk on 2019-11-06 10:22
 *     blog    : https://juejin.im/user/578259398ac2470061f3a3fb/posts
 *     github  : https://github.com/yangkun19921001
 *     mailbox : yang1001yk@gmail.com
 *     desc    : This is BaseDetailActivity
 * </pre>
 */
abstract class BaseDetailActivity : AppCompatActivity() {


    /**
     * 定义一个滑动返回的委托属性
     */
    private val swipeBackTouchDelegate by lazy {
        SwipeBackTouchDelegate(this, ::finish)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Themer.applyProperTheme(this)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item!!.itemId) {
            android.R.id.home -> {
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun finish() {
        super.finish()
        overridePendingTransition(R.anim.left_in, R.anim.rignt_out)
    }

    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        return swipeBackTouchDelegate.onTouchEvent(ev) || super.dispatchTouchEvent(ev)
    }
}


class SwipeBackTouchDelegate(val activity: AppCompatActivity, block: () -> Unit) {
    companion object {
        private const val MIN_FLING_TO_BACK = 2000
    }

    private val minFLlingToBack by lazy {
        activity.dip(MIN_FLING_TO_BACK)
    }

    private val swipeBackDelegate by lazy {
        GestureDetector(activity, object : GestureDetector.SimpleOnGestureListener() {
            override fun onFling(e1: MotionEvent?, e2: MotionEvent?, velocityX: Float, velocityY: Float): Boolean {
                //如果滑动的距离超过 2000 就关闭
                return if (velocityX > minFLlingToBack) {
                    block()
                    true
                } else {
                    false
                }
            }
        })

    }

    fun onTouchEvent(event: MotionEvent) = swipeBackDelegate.onTouchEvent(event)

}

abstract class BaseDetailSwipeFinishableActivity : AppCompatActivity(), SwipeFinishable.SwipeFinishableActivity {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Themer.applyProperTheme(this, true)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item!!.itemId) {
            android.R.id.home
            -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun finishThisActivity() {
        super.finish()
    }

    override fun finish() {
        SwipeFinishable.INSTANCE.finishCurrentActivity()
    }
}
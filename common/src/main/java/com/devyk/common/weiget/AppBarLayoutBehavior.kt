package com.devyk.common.weiget

import android.content.Context
import android.support.design.widget.AppBarLayout
import android.support.design.widget.CoordinatorLayout
import android.util.AttributeSet
import android.view.View
import com.devyk.common.R

class AppBarLayoutBehavior(context: Context, attrs: AttributeSet?) : CoordinatorLayout.Behavior<View>() {
    companion object {
        const val INVALID_VALUE = 0
    }

    private var targetTop: Int
    private var targetLeft: Int
    private var originalTop: Int
    private var originalLeft: Int

    private var targetHeight: Int = INVALID_VALUE
    private var targetWidth: Int = INVALID_VALUE
    private var originalHeight: Int = INVALID_VALUE
    private var originalWidth: Int = INVALID_VALUE

    private var totalOffsetX: Int = INVALID_VALUE
    private var totalOffsetY: Int = INVALID_VALUE

    private var offsetRatio = 0f

    init {
        if (attrs == null) {
            targetTop = INVALID_VALUE
            targetLeft = INVALID_VALUE
            originalTop = INVALID_VALUE
            originalLeft = INVALID_VALUE
        } else {
            val a = context.obtainStyledAttributes(attrs, R.styleable.AppBarLayoutBehavior)
            targetTop = a.getDimensionPixelSize(R.styleable.AppBarLayoutBehavior_targetTop, INVALID_VALUE)
            targetLeft = a.getDimensionPixelSize(R.styleable.AppBarLayoutBehavior_targetLeft, INVALID_VALUE)
            originalTop = a.getDimensionPixelSize(R.styleable.AppBarLayoutBehavior_originalTop, INVALID_VALUE)
            originalLeft = a.getDimensionPixelSize(R.styleable.AppBarLayoutBehavior_originalLeft, INVALID_VALUE)
            a.recycle()
        }
    }

    private fun initializeProperties(child: View, appBarLayout: AppBarLayout){
        if(targetHeight != INVALID_VALUE || child.height == 0) return

        targetHeight = appBarLayout.height - appBarLayout.totalScrollRange - targetTop * 2
        targetWidth = child.width * targetHeight / child.height
        originalWidth = child.width
        originalHeight = child.height

        if(originalLeft == INVALID_VALUE){
            originalLeft = child.x.toInt()
        } else {
            child.x = originalLeft.toFloat()
        }

        if(originalTop == INVALID_VALUE){
            originalTop = child.y.toInt()
        } else {
            child.y = originalTop.toFloat()
        }

        if(targetLeft == INVALID_VALUE){
            targetLeft = (originalLeft + (originalWidth - targetWidth) * child.pivotX / originalWidth).toInt()
        }

        if(targetTop == INVALID_VALUE){
            targetTop = (originalTop + (originalHeight - targetHeight) * child.pivotY / originalHeight).toInt()
        }

        totalOffsetX = (targetLeft - originalLeft + (targetWidth - originalWidth) * child.pivotX / originalWidth).toInt()
        totalOffsetY = (targetTop - originalTop + (targetHeight - originalHeight) * child.pivotY / originalHeight).toInt()
    }

    override fun layoutDependsOn(parent: CoordinatorLayout, child: View, dependency: View): Boolean {
        (dependency as? AppBarLayout)?.let {
            initializeProperties(child, dependency)
        }
        return dependency is AppBarLayout
    }

    override fun onDependentViewChanged(parent: CoordinatorLayout, child: View, dependency: View): Boolean {
        (dependency as? AppBarLayout)?.let {
            val offsetRatio = (it.height - it.bottom).toFloat() / it.totalScrollRange
            child.x += totalOffsetX * (offsetRatio - this.offsetRatio)
            child.y += totalOffsetY * (offsetRatio - this.offsetRatio)

            child.scaleX = 1 - (1 - targetWidth.toFloat() / originalWidth) * offsetRatio
            child.scaleY = 1 - (1 - targetHeight.toFloat() / originalHeight) * offsetRatio

            this.offsetRatio = offsetRatio
        }
        return super.onDependentViewChanged(parent, child, dependency)
    }
}

package com.devyk.kotlin_github.widget

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.RelativeLayout
import com.devyk.common.ext.delegateOf
import com.devyk.common.ext.subscribeIgnoreError
import com.devyk.kotlin_github.R
import io.reactivex.Observable
import kotlinx.android.synthetic.main.detail_item.view.*
import org.jetbrains.anko.sdk15.coroutines.onClick
import kotlin.reflect.KProperty

typealias CheckEvent = (Boolean) -> Observable<Boolean>

class ObjectPropertyDelegate<T, R>(val receiver: R, val getter: ((R) -> T)? = null, val setter: ((R, T) -> Unit)? = null, defaultValue: T? = null) {
    private var value: T? = defaultValue

    operator fun getValue(ref: Any, property: KProperty<*>): T {
        return getter?.invoke(receiver) ?: value!!
    }

    operator fun setValue(ref: Any, property: KProperty<*>, value: T) {
        setter?.invoke(receiver, value)
        this.value = value
    }

}

class DetailItemView
@JvmOverloads
constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0)
    : RelativeLayout(context, attrs, defStyleAttr) {

    init {
        View.inflate(context, R.layout.detail_item, this)
    }

    var title by delegateOf(titleView::getText, titleView::setText)

    var content by delegateOf(contentView::getText, contentView::setText, "")

    var icon by delegateOf(iconView::setImageResource, 0)

    var operatorIcon by delegateOf(operatorIconView::setBackgroundResource, 0)

    var isChecked by delegateOf(operatorIconView::isChecked, operatorIconView::setChecked)

    var checkEvent: CheckEvent? = null

    init {
        attrs?.let {
            val a = context.obtainStyledAttributes(it, R.styleable.DetailItemView)
            title = a.getString(R.styleable.DetailItemView_item_title) ?: ""
            content = a.getString(R.styleable.DetailItemView_item_content) ?: ""
            icon = a.getResourceId(R.styleable.DetailItemView_item_icon, 0)
            operatorIcon = a.getResourceId(R.styleable.DetailItemView_item_op_icon, 0)
            a.recycle()
        }

        onClick {
            checkEvent?.invoke(isChecked)
                    ?.subscribeIgnoreError {
                        isChecked = it
                    }
        }
    }
}
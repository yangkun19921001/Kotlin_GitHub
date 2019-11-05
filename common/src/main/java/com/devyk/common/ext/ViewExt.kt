package com.devyk.common.ext

import android.content.Context
import android.view.ViewManager
import android.widget.TextView
import com.devyk.common.weiget.AppCompatAvatarImageView
import com.zzhoujay.richtext.RichText
import org.jetbrains.anko.AnkoViewDslMarker
import org.jetbrains.anko.custom.ankoView
import org.w3c.dom.Text

/**
 * <pre>
 *     author  : devyk on 2019-11-05 11:07
 *     blog    : https://juejin.im/user/578259398ac2470061f3a3fb/posts
 *     github  : https://github.com/yangkun19921001
 *     mailbox : yang1001yk@gmail.com
 *     desc    : This is ViewExt
 * </pre>
 */

var TextView.markdownText: String
    set(value) {
        RichText.fromMarkdown(value).into(this)
    }
    get() = text.toString()

var TextView.htmlText: String
    set(value) {
        RichText.fromHtml(value).into(this)
    }
    get() = text.toString()

inline fun ViewManager.avatarImageView(): AppCompatAvatarImageView = avatarImageView() {}
inline fun ViewManager.avatarImageView(init: (@AnkoViewDslMarker AppCompatAvatarImageView).() -> Unit): AppCompatAvatarImageView {
    return ankoView({ ctx: Context -> AppCompatAvatarImageView(ctx) }, theme = 0) { init() }
}
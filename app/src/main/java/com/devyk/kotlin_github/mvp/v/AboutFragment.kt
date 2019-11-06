package com.devyk.kotlin_github.mvp.v

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.devyk.common.ext.markdownText
import com.devyk.kotlin_github.R
import com.devyk.kotlin_github.mvp.base.CommonSinglePageFragment
import kotlinx.android.synthetic.main.activity_repo_details.view.*
import org.jetbrains.anko.*
import org.jetbrains.anko.support.v4.UI
import org.jetbrains.anko.support.v4.nestedScrollView
import kotlin.reflect.jvm.internal.impl.builtins.jvm.JvmBuiltInClassDescriptorFactory


/**
 * <pre>
 *     author  : devyk on 2019-11-01 11:32
 *     blog    : https://juejin.im/user/578259398ac2470061f3a3fb/posts
 *     github  : https://github.com/yangkun19921001
 *     mailbox : yang1001yk@gmail.com
 *     desc    : This is AboutFragment
 * </pre>
 */
class AboutFragment : CommonSinglePageFragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return AboutFragmentUIManager().createView(AnkoContext.Companion.create(context!!, this))
    }
}

class AboutFragmentUIManager : AnkoComponent<AboutFragment> {
    override fun createView(ui: AnkoContext<AboutFragment>) = ui.apply {
        nestedScrollView {
            verticalLayout {
                imageView {
                    imageResource = R.mipmap.github_about
                }.lparams(width = wrapContent, height = wrapContent) {
                    gravity = Gravity.CENTER_HORIZONTAL
                }

                themedTextView("Kotlin_GitHub", R.style.detail_title).lparams(){
                    gravity = Gravity.CENTER_HORIZONTAL
                }

                themedTextView().apply {
                    markdownText = "**GitHub:** https://github.com/yangkun19921001"
                    padding = dip(5)
                }

                themedTextView().apply {
                    markdownText = "**掘金博客:** https://juejin.im/user/578259398ac2470061f3a3fb"
                    padding = dip(5)
                }

                themedTextView().apply {
                    markdownText = "**项目地址:** https://juejin.im/user/578259398ac2470061f3a3fb"
                    padding = dip(5)
                }
            }.lparams() {
                gravity = Gravity.CENTER
                padding = dip(20)
            }

        }
    }.view


}

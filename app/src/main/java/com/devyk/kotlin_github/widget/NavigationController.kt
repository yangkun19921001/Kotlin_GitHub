package com.devyk.kotlin_github.widget

import android.support.design.widget.NavigationView
import android.view.MenuItem
import com.bennyhuo.common.log.logger
import com.bennyhuo.github.network.entities.User
import com.devyk.common.ext.loadWithGlide
import com.devyk.kotlin_github.R
import com.devyk.kotlin_github.config.NavViewItem
import com.devyk.kotlin_github.config.doOnLayoutAvailable
import com.devyk.kotlin_github.config.selectItem
import com.devyk.kotlin_github.mvp.m.AccountManager
import kotlinx.android.synthetic.main.nav_header_main.view.*
import org.jetbrains.anko.imageResource
import org.jetbrains.anko.sdk15.coroutines.onClick


class NavigationController(
    private val navigationView: NavigationView,
    private val onNavItemChanged: (NavViewItem) -> Unit,
    private val onHeaderClick: () -> Unit
) : NavigationView.OnNavigationItemSelectedListener {

    init {
        navigationView.setNavigationItemSelectedListener(this)
    }

    private var currentItem: NavViewItem? = null

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        navigationView.apply {
            Settings.lastPage = item.itemId
            val navItem = NavViewItem[item.itemId]
            onNavItemChanged(navItem)
        }
        return true
    }

    fun useLoginLayout() {
        navigationView.menu.clear()
        navigationView.inflateMenu(R.menu.activity_main_drawer) //inflate new items.
        onUpdate(AccountManager.currentUser)
        selectProperItem()
    }

    fun useNoLoginLayout() {
        navigationView.menu.clear()
        navigationView.inflateMenu(R.menu.activity_main_drawer_no_logged_in) //inflate new items.
        onUpdate(AccountManager.currentUser)
        selectProperItem()
    }

    private fun onUpdate(user: User?) {
        navigationView.doOnLayoutAvailable {
            navigationView.apply {
                usernameView.text = user?.login ?: "请登录"
                emailView.text = user?.email ?: "yang1001yk@mail.com"
                if (user == null) {
                    avatarView.imageResource = R.drawable.ic_github
                } else {
                    avatarView.loadWithGlide(user.avatar_url, user.login.first())
                }

                navigationHeader.onClick { onHeaderClick() }
            }
        }
    }

    fun selectProperItem() {
        logger.debug("selectProperItem")
        navigationView.doOnLayoutAvailable {
            logger.debug("selectProperItem onLayout: $currentItem")
            ((currentItem?.let { NavViewItem[it] } ?: Settings.lastPage)
                    .takeIf { navigationView.menu.findItem(it) != null } ?: run { Settings.defaultPage })
                    .let(navigationView::selectItem)
        }
    }
}
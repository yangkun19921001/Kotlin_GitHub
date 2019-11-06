package com.devyk.kotlin_github.adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import com.bennyhuo.github.network.entities.User
import com.devyk.common.ext.loadWithGlide
import com.devyk.kotlin_github.R
import com.devyk.kotlin_github.mvp.base.CommonListAdapter
import kotlinx.android.synthetic.main.item_user.view.*

/**
 * <pre>
 *     author  : devyk on 2019-11-06 10:02
 *     blog    : https://juejin.im/user/578259398ac2470061f3a3fb/posts
 *     github  : https://github.com/yangkun19921001
 *     mailbox : yang1001yk@gmail.com
 *     desc    : This is PeopleListAdapter
 * </pre>
 */
class PeopleListAdapter : CommonListAdapter<User>(R.layout.item_user) {
    override fun onBindData(viewHolder: RecyclerView.ViewHolder, user: User) {
        viewHolder.itemView.apply {
            avatarView.loadWithGlide(user.avatar_url, user.login.first())
            nameView.text = user.login

        }
    }

    override fun onItemClicked(itemView: View, item: User) {
    }
}
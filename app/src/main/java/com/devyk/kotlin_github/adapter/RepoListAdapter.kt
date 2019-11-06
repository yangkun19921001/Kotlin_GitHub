package com.devyk.kotlin_github.adapter

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.FragmentActivity
import android.support.v7.widget.RecyclerView
import android.view.View
import com.devyk.common.App
import com.devyk.common.ext.loadWithGlide
import com.devyk.common.ext.toKilo
import com.devyk.kotlin_github.R
import com.devyk.kotlin_github.mvp.base.CommonListAdapter
import com.devyk.kotlin_github.mvp.m.entity.Repository
import com.devyk.kotlin_github.mvp.v.RepoDetailActivity
import kotlinx.android.synthetic.main.item_repo.view.*
import org.jetbrains.anko.newTask

/**
 * <pre>
 *     author  : devyk on 2019-11-05 13:56
 *     blog    : https://juejin.im/user/578259398ac2470061f3a3fb/posts
 *     github  : https://github.com/yangkun19921001
 *     mailbox : yang1001yk@gmail.com
 *     desc    : This is RepoListAdapter
 * </pre>
 */
class RepoListAdapter() : CommonListAdapter<Repository>(R.layout.item_repo) {
    override fun onBindData(viewHolder: RecyclerView.ViewHolder, repository: Repository) {
        viewHolder.itemView.apply {
            avatarView.loadWithGlide(repository.owner.avatar_url, repository.owner.login.first())
            repoNameView.text = repository.name
            descriptionView.text = repository.description
            langView.text = repository.language ?: "Unknown"
            starView.text = repository.stargazers_count.toKilo()
            forkView.text = repository.forks_count.toKilo()
        }
    }

    override fun onItemClicked(itemView: View, item: Repository) {
        var intent = Intent(App.getInstance(), RepoDetailActivity::class.java);
        intent.newTask()
        var bundle = Bundle()
        bundle.putParcelable("repository", item)
        intent.putExtras(bundle)

        App.getInstance().applicationContext.startActivity(intent)

    }

}
package com.devyk.kotlin_github.adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import com.devyk.common.ext.htmlText
import com.devyk.common.utils.githubTimeToDate
import com.devyk.common.utils.view
import com.devyk.kotlin_github.R
import com.devyk.kotlin_github.mvp.base.CommonListAdapter
import com.devyk.kotlin_github.mvp.m.entity.Issue
import kotlinx.android.synthetic.main.item_issue.view.*
import org.jetbrains.anko.imageResource

/**
 * <pre>
 *     author  : devyk on 2019-11-05 11:01
 *     blog    : https://juejin.im/user/578259398ac2470061f3a3fb/posts
 *     github  : https://github.com/yangkun19921001
 *     mailbox : yang1001yk@gmail.com
 *     desc    : This is IssueListAdapter
 * </pre>
 */
class IssueListAdapter : CommonListAdapter<Issue>(R.layout.item_issue) {
    override fun onBindData(viewHolder: RecyclerView.ViewHolder, issue: Issue) {
        viewHolder.itemView.run {
            iconView.imageResource = if (issue.state == "open") R.drawable.ic_issue_open else R.drawable.ic_issue_closed
            titleView.text = issue.title
            timeView.text = githubTimeToDate(issue.created_at).view()
            bodyView.htmlText = issue.body_html
            commentCount.text = issue.comments.toString()
        }

    }

    override fun onItemClicked(itemView: View, item: Issue) {

    }
}
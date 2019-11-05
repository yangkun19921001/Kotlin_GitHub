package com.devyk.kotlin_github.mvp.v.issue

import android.support.v4.app.Fragment
import com.devyk.kotlin_github.adapter.IssueListAdapter
import com.devyk.kotlin_github.mvp.base.CommonListAdapter
import com.devyk.kotlin_github.mvp.base.CommonListFragment
import com.devyk.kotlin_github.mvp.m.entity.Issue
import com.devyk.kotlin_github.mvp.p.MyIssuePresenter

class MyIssueListFragment : CommonListFragment<Issue, MyIssuePresenter>() {
    override val adapter = IssueListAdapter()

}
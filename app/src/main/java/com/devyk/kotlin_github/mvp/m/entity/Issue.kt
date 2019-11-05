package com.devyk.kotlin_github.mvp.m.entity

import android.os.Parcelable
import com.bennyhuo.github.network.entities.BasicUser
import com.devyk.common.anno.PoKo
import kotlinx.android.parcel.Parcelize


@PoKo
@Parcelize
data class Issue(var url: String,
                 var repository_url: String,
                 var labels_url: String,
                 var comments_url: String,
                 var events_url: String,
                 var html_url: String,
                 var id: Int,
                 var number: Int,
                 var title: String,
                 var user: BasicUser,
                 var state: String,
                 var locked: Boolean,
                 var comments: Int,
                 var created_at: String,
                 var updated_at: String,
                 var author_association: String,
                 var body: String,
                 var body_html: String,
                 var labels: List<Labels>,
                 var assignees: List<BasicUser>,
                 var repository: Repository): Parcelable {
    @PoKo
    @Parcelize
    data class Labels(var id: Int,
                      var url: String,
                      var name: String,
                      var color: String,
                      var default: Boolean): Parcelable
}
package com.devyk.kotlin_github.mvp.m.entity

import com.devyk.common.anno.PoKo
import com.devyk.kotlin_github.mvp.base.GitHubPaging


@PoKo
data class SearchRepositories(
    var total_count: Int,
    var incomplete_results: Boolean,
    var items: List<Repository>
) : PagingWrapper<Repository>() {





    override fun getElements() = items


}




package com.devyk.kotlin_github.mvp.v

import SubscriptionResponse
import android.app.Activity
import android.os.Bundle
import com.bennyhuo.common.log.logger
import com.devyk.common.ext.*
import com.devyk.common.utils.githubTimeToDate
import com.devyk.common.utils.view
import com.devyk.kotlin_github.R
import com.devyk.kotlin_github.api.ActivityService
import com.devyk.kotlin_github.api.RepositoryService
import com.devyk.kotlin_github.mvp.base.BaseDetailSwipeFinishableActivity
import com.devyk.kotlin_github.mvp.m.entity.Repository
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_repo_details.*
import kotlinx.android.synthetic.main.app_bar_details.*
import kotlinx.android.synthetic.main.item_repo.avatarView
import kotlinx.coroutines.*
import org.reactivestreams.Subscriber
import org.reactivestreams.Subscription
import retrofit2.Response
import java.lang.Exception
import java.util.*

/**
 * <pre>
 *     author  : devyk on 2019-11-05 14:29
 *     blog    : https://juejin.im/user/578259398ac2470061f3a3fb/posts
 *     github  : https://github.com/yangkun19921001
 *     mailbox : yang1001yk@gmail.com
 *     desc    : This is RepoDetailActivity
 * </pre>
 */
class RepoDetailActivity : BaseDetailSwipeFinishableActivity() {
    //获取上层传递过来的信息
    lateinit var repository: Repository


    override fun onCreate(savedInstanceState: Bundle?) {
        overridePendingTransition(R.anim.rignt_in, R.anim.left_out)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_repo_details)

        setSupportActionBar(toolBar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        repository = getRepositoryData()

        initDetails()

        reloadDetails()
    }

    private fun initDetails() {
        //加载头像
        avatarView.loadWithGlide(repository.owner.avatar_url, repository.owner.login.first())
        collapsingToolbar.title = repository.name

        descriptionView_appbar.markdownText = getString(
            R.string.repo_description_template,
            repository.owner.login,
            repository.owner.html_url,
            repository.name,
            repository.html_url,
            repository.owner.login,
            repository.owner.html_url,
            githubTimeToDate(repository.created_at).view()
        )

        bodyView.text = repository.description

        detailContainer.alpha = 0f

        stars.checkEvent = { isChecked ->
            if (isChecked) {
                ActivityService.unstar(repository.owner.login, repository.name)
                    .map { false }
            } else {
                ActivityService.star(repository.owner.login, repository.name)
                    .map { true }
            }.doOnNext { reloadDetails(true) }
        }

        watches.checkEvent = { isChecked ->
            if (isChecked) {
                ActivityService.unwatch(repository.owner.login, repository.name)
                    .map { false }
            } else {
                ActivityService.watch(repository.owner.login, repository.name)
                    .map { true }
            }.doOnNext { reloadDetails(true) }
        }

        ActivityService.isStarred(repository.owner.login, repository.name)
            .onErrorReturn {
                if (it is retrofit2.HttpException) {
                    it.response() as Response<Any>
                } else {
                    throw it
                }
            }
            .subscribeIgnoreError {
                stars.isChecked = it.isSuccessful
            }


        ActivityService.isWatchedDeferred(
            repository.owner.login,
            repository.name
        ).subscribe(object : Observer<SubscriptionResponse> {
            override fun onSubscribe(d: Disposable) {

            }

            override fun onError(e: Throwable) {
                logger.error("error:$e")
            }

            override fun onComplete() {

            }



            override fun onNext(subscriptionResponse: SubscriptionResponse) {
                watches.isChecked = subscriptionResponse.subscribed
            }
        })
    }

    private fun reloadDetails(forceNetwork: Boolean = false) {
        RepositoryService.getRepository(repository.owner.login, repository.name, forceNetwork)
            .subscribe(object : Observer<Repository> {
                override fun onComplete() {

                }

                override fun onSubscribe(d: Disposable) {
                    loadingView.animate().alpha(1f).start()
                }

                override fun onNext(t: Repository) {
                    repository = t

                    owner.content = repository.owner.login
                    stars.content = repository.stargazers_count.toString()
                    watches.content = repository.subscribers_count.toString()
                    forks.content = repository.forks_count.toString()
                    //issues.content = repository.open_issues_count.toString()

                    loadingView.animate().alpha(0f).start()
                    detailContainer.animate().alpha(1f).start()
                }

                override fun onError(e: Throwable) {
                    loadingView.animate().alpha(0f).start()
                    e.printStackTrace()
                }
            })
    }

    fun getRepositoryData(): Repository {
        val repos = this.intent.extras.getParcelable<Repository>("repository")
        logger.debug("repository:$repos")
        return repos


    }
}
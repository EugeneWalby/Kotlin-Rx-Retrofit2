package com.chisw.taskbyandrew.ui.activity.task2

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.chisw.taskbyandrew.R
import com.chisw.taskbyandrew.R.id.rvStoriesList
import com.chisw.taskbyandrew.network.AlgoliaApiService
import com.chisw.taskbyandrew.network.model.Model
import com.chisw.taskbyandrew.ui.activity.base.BaseActivity
import com.chisw.taskbyandrew.ui.adapter.AuthorsAdapter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Function
import io.reactivex.functions.Predicate
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_authors_with_karma.*

class AuthorsWithKarmaActivity : BaseActivity() {
    companion object {
        const val TAGS = "story"
        const val PAGE = 0
        const val KARMA_DEF_VALUE = 3000
    }

    private val algoliaApiService by lazy {
        AlgoliaApiService.create()
    }

    private val authorsInfo: ArrayList<Model.UserResponse> = ArrayList()

    override fun provideLayout(): Int {
        return R.layout.activity_authors_with_karma
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loadHistory(PAGE)
    }

    private fun loadHistory(page: Int) {
        pbProcessing.visibility = View.VISIBLE
        val disposable = algoliaApiService.getStoriesInfo(page, TAGS)
                .toObservable()
                .flatMapIterable { t -> t.hits }
                .flatMap { t -> algoliaApiService.getAuthorInfo(t.author).toObservable() }
                .filter { t -> t.karma > KARMA_DEF_VALUE }
                .toList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { result ->
                            for (stories in result) {
                                authorsInfo.add(stories)
                            }
                            fillRecyclerByAuthors()
                            pbProcessing.visibility = View.GONE
                        },
                        {
                            showToast(getString(R.string.error_loading_stories))
                            pbProcessing.visibility = View.GONE
                        }
                )
        addDisposable(disposable)
    }

    private fun fillRecyclerByAuthors() {
        rvStoriesList.layoutManager = LinearLayoutManager(this)
        rvStoriesList.adapter = AuthorsAdapter(authorsInfo, this)
    }
}
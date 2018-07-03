package com.chisw.taskbyandrew.ui.activity.task11

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.chisw.taskbyandrew.R
import com.chisw.taskbyandrew.network.AlgoliaApiService
import com.chisw.taskbyandrew.network.model.AuthorAndStory
import com.chisw.taskbyandrew.ui.activity.base.BaseActivity
import com.chisw.taskbyandrew.ui.adapter.AuthorAndStoryAdapter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_authors_with_karma.*

class NewModelCombiningActivity : BaseActivity() {
    companion object {
        const val TAGS = "story"
        const val PAGE = 0
        const val STORY_INDEX_TO_LOAD = 3
    }

    private val algoliaApiService by lazy {
        AlgoliaApiService.create()
    }

    private val authorsInfo: ArrayList<AuthorAndStory> = ArrayList()

    override fun provideLayout(): Int {
        return R.layout.activity_new_model_combining
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loadHistory(PAGE)
    }

    private fun loadHistory(page: Int) {
        pbProcessing.visibility = View.VISIBLE

        val disposable = algoliaApiService.getStoriesInfo(page, TAGS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { result ->
                            algoliaApiService.getAuthorInfo(result.hits[STORY_INDEX_TO_LOAD - 1].author)
                                    .subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe({ t ->
                                        authorsInfo.add(AuthorAndStory(t.username, t.karma, result.hits[STORY_INDEX_TO_LOAD - 1].title))
                                        fillRecyclerByAuthors()
                                    })
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
        rvStoriesList.adapter = AuthorAndStoryAdapter(authorsInfo, this)
    }
}
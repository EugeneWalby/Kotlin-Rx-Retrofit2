package com.chisw.taskbyandrew.ui.activity.task11

import android.os.Bundle
import android.view.View
import com.chisw.taskbyandrew.R
import com.chisw.taskbyandrew.network.AlgoliaApiService
import com.chisw.taskbyandrew.network.model.AuthorAndStory
import com.chisw.taskbyandrew.ui.activity.base.BaseActivity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_authors_with_karma.*

// +
class NewModelCombiningActivity : BaseActivity() {
    companion object {
        const val TAGS = "story"
        const val PAGE = 0
        const val STORY_INDEX_TO_LOAD = 3L
    }

    private val algoliaApiService by lazy {
        AlgoliaApiService.create()
    }

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
                .flattenAsObservable { t -> t.hits }
                .take(STORY_INDEX_TO_LOAD)
                .lastElement()
                .toObservable()
                .flatMap { t ->
                    algoliaApiService.getAuthorInfo(t.author)
                            .toObservable()
                            .map { a -> AuthorAndStory(a.username, a.karma, t.title) }
                }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { result ->
                            showToast("Author: " + result.username + '\n'
                                    + "Karma: " + result.karma + '\n'
                                    + "Story: "+ result.title)
                            pbProcessing.visibility = View.GONE
                        },
                        {
                            showToast(getString(R.string.error_loading_stories))
                            pbProcessing.visibility = View.GONE
                        }
                )
        addDisposable(disposable)
    }
}

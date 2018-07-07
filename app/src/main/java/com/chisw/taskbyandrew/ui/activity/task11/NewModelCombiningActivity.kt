package com.chisw.taskbyandrew.ui.activity.task11

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.Display
import android.view.View
import android.widget.Toast
import com.chisw.taskbyandrew.R
import com.chisw.taskbyandrew.network.AlgoliaApiService
import com.chisw.taskbyandrew.network.model.AuthorAndStory
import com.chisw.taskbyandrew.network.model.Model
import com.chisw.taskbyandrew.ui.activity.base.BaseActivity
import com.chisw.taskbyandrew.ui.adapter.AuthorAndStoryAdapter
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_authors_with_karma.*

class NewModelCombiningActivity : BaseActivity() {
    companion object {
        const val TAGS = "story"
        const val PAGE = 0
        const val STORY_INDEX_TO_LOAD = 3L
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
        val observable1 = Observable.range(1, 10)
        val observable2 = listOf("String 1", "String 2", "String 3",
                "String 4", "String 5", "String 6", "String 7", "String 8",
                "String 9", "String 10")

        observable1.zipWith(observable2, { e1: Int, e2: String ->
            "$e2 $e1"
        })//(1)
                .subscribe {
                    println("Received $it")
                }

        pbProcessing.visibility = View.VISIBLE
        val disposable = algoliaApiService.getStoriesInfo(page, TAGS)
                .flattenAsObservable { t -> t.hits }
                .take(STORY_INDEX_TO_LOAD)
                .lastElement()
                .toObservable()
                .flatMap { t ->
                    algoliaApiService.getAuthorInfo(t.author).toObservable()
                            .zipWith(listOf("", ""), { e1: Model.UserResponse, e2: String -> AuthorAndStory(e1.username, e1.karma, "") })
                }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { result ->
                            showToast(result.username + " " + result.karma)
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

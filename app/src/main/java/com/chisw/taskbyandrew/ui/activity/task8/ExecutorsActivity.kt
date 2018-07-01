package com.chisw.taskbyandrew.ui.activity.task8

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import com.chisw.taskbyandrew.R
import com.chisw.taskbyandrew.network.AlgoliaApiService
import com.chisw.taskbyandrew.network.model.Model
import com.chisw.taskbyandrew.ui.activity.base.BaseActivity
import com.chisw.taskbyandrew.ui.activity.task1.TitlesOfStoriesActivity
import com.chisw.taskbyandrew.ui.adapter.StoriesAdapter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_executors.*
import java.util.concurrent.Executors

class ExecutorsActivity : BaseActivity() {
    companion object {
        const val LOG_TAG = "LOG"
        const val TAGS = "story"
        const val PAGE = 0
    }

    override fun provideLayout(): Int {
        return R.layout.activity_executors
    }

    private val algoliaApiService by lazy {
        AlgoliaApiService.create()
    }
    private val storiesList: ArrayList<String> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        createExecutors()
    }

    private fun createExecutors() {
        pbProcessing.visibility = View.VISIBLE
        val executor1 = Executors.newSingleThreadExecutor()
        val executor2 = Executors.newSingleThreadExecutor()
        val executor3 = Executors.newSingleThreadExecutor()

        val disposable = algoliaApiService
                .getStoriesInfo(PAGE, TAGS)
                .doOnSubscribe {
                    Log.d(LOG_TAG + 1, Thread.currentThread().name)
                }
                .subscribeOn(Schedulers.from(executor1))
                .concatWith {
                    algoliaApiService.getStoriesInfo(PAGE + 1, TitlesOfStoriesActivity.TAGS)
                }
                .doOnSubscribe { Log.d(LOG_TAG + 2, Thread.currentThread().name) }
                .subscribeOn(Schedulers.from(executor2))
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { Log.d(LOG_TAG + 3, Thread.currentThread().name) }
                .subscribeOn(Schedulers.from(executor3))
                .subscribe(
                        { result ->
                            fillStoriesList(result)
                            fillRecyclerByStories()
                            pbProcessing.visibility = View.GONE
                        },
                        {
                            showToast(getString(R.string.error_loading_stories))
                            pbProcessing.visibility = View.GONE
                        })
        addDisposable(disposable)
    }

    private fun fillStoriesList(result: Model.HitsResponse) {
        for (i in 0 until result.hits.size) {
            storiesList.add(result.hits[i].title)
        }
    }

    private fun fillRecyclerByStories() {
        rvStoriesList.layoutManager = LinearLayoutManager(this)
        rvStoriesList.adapter = StoriesAdapter(storiesList, this)
    }
}
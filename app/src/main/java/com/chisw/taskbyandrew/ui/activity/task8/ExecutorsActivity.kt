package com.chisw.taskbyandrew.ui.activity.task8

import android.os.Bundle
import android.util.Log
import com.chisw.taskbyandrew.R
import com.chisw.taskbyandrew.network.AlgoliaApiService
import com.chisw.taskbyandrew.network.model.Model
import com.chisw.taskbyandrew.ui.activity.base.BaseActivity
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        createExecutors()
    }

    private fun createExecutors() {
        val executor1 = Executors.newSingleThreadExecutor()
        val executor2 = Executors.newSingleThreadExecutor()
        val executor3 = Executors.newSingleThreadExecutor()

        val disposable = Single.merge(algoliaApiService.getStoriesInfo(PAGE, TAGS)
                .doOnSubscribe { logCurrentThread(1) }
                .subscribeOn(Schedulers.from(executor1)),
                algoliaApiService.getStoriesInfo(PAGE + 1, TAGS))
                .doOnSubscribe { logCurrentThread(2) }
                .subscribeOn(Schedulers.from(executor2))
                .observeOn(Schedulers.from(executor3))
                .subscribe(
                        { result ->
                            logCurrentThread(3)
                            logTitlesOfStories(result)
                        })
        addDisposable(disposable)
    }

    private fun logCurrentThread(threadNumber: Int) {
        Log.d(LOG_TAG + threadNumber, Thread.currentThread().name)
    }

    private fun logTitlesOfStories(result: Model.HitsResponse) {
        for (i in 0 until result.hits.size) {
            Log.d(LOG_TAG, result.hits[i].title)
        }
    }
}
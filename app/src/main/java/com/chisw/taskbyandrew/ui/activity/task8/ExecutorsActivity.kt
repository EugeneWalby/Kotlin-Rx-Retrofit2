package com.chisw.taskbyandrew.ui.activity.task8

import android.os.Bundle
import android.util.Log
import com.chisw.taskbyandrew.R
import com.chisw.taskbyandrew.network.AlgoliaApiService
import com.chisw.taskbyandrew.network.model.Model
import com.chisw.taskbyandrew.ui.activity.base.BaseActivity
import io.reactivex.Single
import io.reactivex.functions.BiFunction
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

        val disposable = algoliaApiService.getStoriesInfo(PAGE, TAGS)
                .logThread()
                .subscribeOn(Schedulers.from(executor1))
                .zipWith(algoliaApiService.getStoriesInfo(PAGE + 1, TAGS).subscribeOn(Schedulers.from(executor2)),
                        BiFunction<Model.HitsResponse, Model.HitsResponse, List<Model.HitsStoryResponse>> { t1, t2 -> t1.hits.plus(t2.hits) })
                .logThread()
                .observeOn(Schedulers.from(executor3))
                .subscribe({ t ->
                    logTitlesOfStories(t)
                    Log.d(ExecutorsActivity.LOG_TAG, getString(R.string.log_current_thread) + Thread.currentThread().name)

                }, {
                    it.printStackTrace()
                })
        addDisposable{disposable}
    }

    private fun logTitlesOfStories(resultList: List<Model.HitsStoryResponse>) {
        for (story in resultList) {
            Log.d(LOG_TAG, story.title)
        }
    }

    private fun <T> Single<T>.logThread(): Single<T> {
        return this.map { t ->
            Log.d(ExecutorsActivity.LOG_TAG, getString(R.string.log_current_thread) + Thread.currentThread().name)
            t
        }
    }
}

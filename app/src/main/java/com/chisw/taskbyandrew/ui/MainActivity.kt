package com.chisw.taskbyandrew.ui

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.chisw.taskbyandrew.R
import com.chisw.taskbyandrew.network.AlgoliaApiService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private val algoliaApiService by lazy {
        AlgoliaApiService.create()
    }

    var disposable: Disposable? = null

    private fun loadHistory(page: Int) {
        disposable = algoliaApiService.getStoryTitles(page, "story")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ result -> tvHello.text = result.hits[0].title },
                        { error -> tvHello.text = error.message }
                )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        loadHistory(0)
    }

    override fun onPause() {
        super.onPause()
        disposable?.dispose()
    }
}

package com.chisw.taskbyandrew.ui

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.widget.Toast
import com.chisw.taskbyandrew.R
import com.chisw.taskbyandrew.network.AlgoliaApiService
import com.chisw.taskbyandrew.network.model.Model
import com.chisw.taskbyandrew.ui.adapter.StoriesAdapter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private val tags = "story"
    private val algoliaApiService by lazy {
        AlgoliaApiService.create()
    }
    private var disposable: Disposable? = null
    private val storiesList: ArrayList<String> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        loadHistory(0)
    }

    override fun onPause() {
        super.onPause()
        disposable?.dispose()
    }

    private fun loadHistory(page: Int) {
        disposable = algoliaApiService.getStoryTitles(page, tags)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ result ->
                    fillStoriesList(result)
                    fillRecyclerByStories()
                },
                        { Toast.makeText(applicationContext, getString(R.string.error_loading_stories), Toast.LENGTH_SHORT).show() }
                )
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

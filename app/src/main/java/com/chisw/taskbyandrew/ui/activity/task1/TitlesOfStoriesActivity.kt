package com.chisw.taskbyandrew.ui.activity.task1

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.Toast
import com.chisw.taskbyandrew.R
import com.chisw.taskbyandrew.network.AlgoliaApiService
import com.chisw.taskbyandrew.network.model.Model
import com.chisw.taskbyandrew.ui.activity.base.BaseActivity
import com.chisw.taskbyandrew.ui.adapter.StoriesAdapter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_titles_of_stories.*

class TitlesOfStoriesActivity : BaseActivity() {
    companion object {
        const val TAGS = "story"
        const val PAGE = 1
    }

    private val algoliaApiService by lazy {
        AlgoliaApiService.create()
    }
    private val storiesList: ArrayList<String> = ArrayList()
    private var disposable: Disposable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_titles_of_stories)
        loadHistory(PAGE)
    }

    override fun onPause() {
        super.onPause()
        disposable?.dispose()
    }

    private fun loadHistory(page: Int) {
        pbProcessing.visibility = View.VISIBLE
        disposable = algoliaApiService.getTitlesOfStories(page, TAGS)
                .concatWith(algoliaApiService.getTitlesOfStories(page + 1, TAGS))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ result ->
                    fillStoriesList(result)
                    fillRecyclerByStories()
                    pbProcessing.visibility = View.GONE
                },
                        {
                            Toast.makeText(applicationContext, getString(R.string.error_loading_stories), Toast.LENGTH_SHORT).show()
                            pbProcessing.visibility = View.GONE
                        }
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
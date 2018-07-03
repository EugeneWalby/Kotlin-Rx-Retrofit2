package com.chisw.taskbyandrew.ui.activity.task7

import android.os.Bundle
import android.view.View
import com.chisw.taskbyandrew.R
import com.chisw.taskbyandrew.ui.activity.base.BaseActivity
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_emitted_values_accumulation.*
import java.util.concurrent.TimeUnit

// +
class EmittedValuesAccumulationActivity : BaseActivity() {
    companion object {
        const val START_SEQUENCE_VALUE = 0L
        const val SEQUENCE_VALUES_COUNT = 10L
        const val EMITTING_INITIAL_DELAY = 0L
        const val EMITTING_PERIOD = 1L
        const val VALUES_TO_ACCUMULATE = 2
    }

    override fun provideLayout(): Int {
        return R.layout.activity_emitted_values_accumulation
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        accumulateEachTwoValues()
    }

    private fun accumulateEachTwoValues() {
        pbProcessing.visibility = View.VISIBLE
        val list = mutableListOf<Long>()
        val disposable = Observable.intervalRange(START_SEQUENCE_VALUE, SEQUENCE_VALUES_COUNT, EMITTING_INITIAL_DELAY, EMITTING_PERIOD, TimeUnit.SECONDS)
                .buffer(VALUES_TO_ACCUMULATE * 2)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        {
                            list.add(it[0])
                            list.add(it[1])
                        },
                        {
                            showToast(getString(R.string.msg_error))
                        },
                        {
                            showToast(list.sum().toString())
                            pbProcessing.visibility = View.GONE
                        })
        addDisposable(disposable)
    }
}
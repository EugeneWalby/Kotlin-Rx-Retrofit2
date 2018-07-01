package com.chisw.taskbyandrew.ui.activity.task9

import android.os.Bundle
import com.chisw.taskbyandrew.R
import com.chisw.taskbyandrew.ui.activity.base.BaseActivity
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.lang.Exception
import java.util.concurrent.TimeUnit

// done
class EmittedValuesSubscribingActivity : BaseActivity() {
    companion object {
        const val START_SEQUENCE_VALUE = 0L
        const val SEQUENCE_VALUES_COUNT = 10L
        const val EMITTING_INITIAL_DELAY = 0L
        const val EMITTING_PERIOD = 1L
    }

    override fun provideLayout(): Int {
        return R.layout.activity_emitted_values_subscribing
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        createObservable()
    }

    private fun createObservable() {
        val disposable = Observable.intervalRange(START_SEQUENCE_VALUE, SEQUENCE_VALUES_COUNT, EMITTING_INITIAL_DELAY, EMITTING_PERIOD, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { showToast(getString(R.string.msg_subscribed)) }
                .subscribe(
                        {
                            if (it == 7L) {
                                throw Exception(getString(R.string.msg_error))
                            }
                            showToast(it.toString())
                        },
                        {
                            showToast(it.message)
                        })
        addDisposable(disposable)
    }
}
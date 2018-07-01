package com.chisw.taskbyandrew.ui.activity.task7

import com.chisw.taskbyandrew.R
import com.chisw.taskbyandrew.ui.activity.base.BaseActivity
import io.reactivex.Observable
import java.util.concurrent.TimeUnit

class EmittedValuesAccumulationActivity : BaseActivity() {
    override fun provideLayout(): Int {
        return R.layout.activity_emitted_values_accumulation
    }

    private fun createObservable() {
        val disposable = Observable
                .intervalRange(0, 10, 0, 1, TimeUnit.SECONDS)
                .take(2)
                .subscribe()

    }
}
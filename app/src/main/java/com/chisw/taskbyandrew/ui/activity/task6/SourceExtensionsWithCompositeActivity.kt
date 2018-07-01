package com.chisw.taskbyandrew.ui.activity.task6

import android.os.Bundle
import android.util.Log
import com.chisw.taskbyandrew.R
import com.chisw.taskbyandrew.ui.activity.base.BaseActivity
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.Single

class SourceExtensionsWithCompositeActivity : BaseActivity() {
    companion object {
        const val LOG_TAG = "LOG"
    }

    override fun provideLayout(): Int {
        return R.layout.activity_source_extensions_with_composite
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        createSingle()
        createObservable()
        createMaybe()
    }

    private fun createSingle() {
        val disposable = Single.just("Single")
                .logThread()
                .divideByThreads()
                .subscribe({ t -> showToast(t) })
        addDisposable(disposable)
    }

    private fun createObservable() {
        val disposable = Observable.just("Observable")
                .divideByThreads()
                .subscribe(
                        {
                            showToast(it)
                        },
                        {
                            showToast(it.message)
                        }, {
                    showToast("Yeah!")
                }
                )
        addDisposable(disposable)
    }

    private fun createMaybe() {
        val disposable = Maybe.just("Maybe")
                .divideByThreads()
                .subscribe({ showToast(it) })
        addDisposable(disposable)
    }

    private fun Single<String>.logThread(): Single<String> {
        Log.d(LOG_TAG, getString(R.string.log_current_thread) + Thread.currentThread().name)
        return this
    }

    private fun Single<String>.divideByThreads(): Single<String> {
        return this.subscribeOn(io.reactivex.schedulers.Schedulers.io())
                .observeOn(io.reactivex.android.schedulers.AndroidSchedulers.mainThread())
    }

    private fun Observable<String>.divideByThreads(): Observable<String> {
        return this.subscribeOn(io.reactivex.schedulers.Schedulers.io())
                .observeOn(io.reactivex.android.schedulers.AndroidSchedulers.mainThread())
    }

    private fun Maybe<String>.divideByThreads(): Maybe<String> {
        return this.subscribeOn(io.reactivex.schedulers.Schedulers.io())
                .observeOn(io.reactivex.android.schedulers.AndroidSchedulers.mainThread())
    }
}
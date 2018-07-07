package com.chisw.taskbyandrew.ui.activity.task6

import android.os.Bundle
import android.util.Log
import com.chisw.taskbyandrew.R
import com.chisw.taskbyandrew.ui.activity.base.BaseActivity
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

// 1 +
// 2 +
// 3
// change with generics
class SourceExtensionsWithCompositeActivity : BaseActivity() {
    companion object {
        const val LOG_TAG = "LOG"
        const val SINGLE_MESSAGE = "Single"
        const val OBSERVABLE_MESSAGE = "Observable"
        const val MAYBE_MESSAGE = "Maybe"
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
        val disposable = Single.just(SINGLE_MESSAGE)
                .logThread()
                .divideByThreads()
                .logThread()
                .subscribe({ t -> showToast(t) })
        addDisposable(disposable)
    }

    private fun createObservable() {
        val disposable = Observable.just(OBSERVABLE_MESSAGE)
                .divideByThreads()
                .subscribe(
                        {
                            showToast(it)
                        })
        addDisposable(disposable)
    }

    private fun createMaybe() {
        val disposable = Maybe.just(MAYBE_MESSAGE)
                .divideByThreads()
                .subscribe({ showToast(it) })
        addDisposable(disposable)
    }

    private fun Single<String>.logThread(): Single<String> {
        return this.map { t ->
            Log.d(LOG_TAG, getString(R.string.log_current_thread) + Thread.currentThread().name)
            t
        }
    }
}

private fun Single<String>.divideByThreads(): Single<String> {
    return this.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
}

private fun Observable<String>.divideByThreads(): Observable<String> {
    return this.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
}

private fun Maybe<String>.divideByThreads(): Maybe<String> {
    return this.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
}
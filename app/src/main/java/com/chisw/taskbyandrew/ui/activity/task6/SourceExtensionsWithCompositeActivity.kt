package com.chisw.taskbyandrew.ui.activity.task6

import android.os.Bundle
import android.util.Log
import com.chisw.taskbyandrew.R
import com.chisw.taskbyandrew.ui.activity.base.BaseActivity
import io.reactivex.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

// 1 +
// 2 +
// 3 +
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
        addDisposable { disposable }
    }

    private fun createObservable() {
        val disposable = Observable.just(OBSERVABLE_MESSAGE)
                .divideByThreads()
                .subscribe(
                        {
                            showToast(it)
                        })
        addDisposable { disposable }
    }

    private fun createMaybe() {
        val disposable = Maybe.just(MAYBE_MESSAGE)
                .divideByThreads()
                .subscribe({ showToast(it) })
        addDisposable { disposable }
    }

    private fun <T> Single<T>.logThread(): Single<T> {
        return this.map { t ->
            Log.d(LOG_TAG, getString(R.string.log_current_thread) + Thread.currentThread().name)
            t
        }
    }
}

private fun <T> Single<T>.divideByThreads(): Single<T> {
    return this.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
}

private fun <T> Observable<T>.divideByThreads(): Observable<T> {
    return this.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
}

private fun <T> Maybe<T>.divideByThreads(): Maybe<T> {
    return this.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
}

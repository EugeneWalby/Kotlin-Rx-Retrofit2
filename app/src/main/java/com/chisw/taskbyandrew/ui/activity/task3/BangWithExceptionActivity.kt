package com.chisw.taskbyandrew.ui.activity.task3

import com.chisw.taskbyandrew.R
import com.chisw.taskbyandrew.ui.activity.base.BaseBangActivity
import io.reactivex.Maybe
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_bang_without_exception.*

// done
class BangWithExceptionActivity : BaseBangActivity() {
    override fun provideLayout(): Int {
        return R.layout.activity_bang_with_exception
    }

    override fun createButtonClickListener() {
        btnEmitValue.setOnClickListener {
            val disposable = Maybe.just("")
                    .map {
                        createSourceOrThrowException(getRandomValue())
                    }
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            {
                                it.map { showToast(it) }.subscribe()
                            },
                            {
                                showToast(it.message)
                            })
            addDisposable(disposable)
        }
    }

    private fun createSourceOrThrowException(action: Boolean): Maybe<String> {
        if (action) {
            return Maybe.just(getString(R.string.bang))
        }
        throw IllegalArgumentException(getString(R.string.msg_illegal_argument_exception))
    }
}
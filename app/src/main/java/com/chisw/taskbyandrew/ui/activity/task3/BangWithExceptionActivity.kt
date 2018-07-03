package com.chisw.taskbyandrew.ui.activity.task3

import com.chisw.taskbyandrew.R
import com.chisw.taskbyandrew.ui.activity.base.BaseBangActivity
import io.reactivex.Maybe
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_bang_without_exception.*

// +
class BangWithExceptionActivity : BaseBangActivity() {
    override fun provideLayout(): Int {
        return R.layout.activity_bang_with_exception
    }

    override fun createButtonClickListener() {
        btnEmitValue.setOnClickListener {
            val disposable = createSourceOrThrowException(getRandomValue())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            {
                                showToast(it)
                            },
                            {
                                showToast(it.message)
                            })
            addDisposable(disposable)
        }
    }

    private fun createSourceOrThrowException(action: Boolean): Maybe<String> {
        return Maybe.create<String> { e ->
            if (action) {
                e.onSuccess(getString(R.string.bang))
            } else {
                e.onError(IllegalArgumentException())
            }
        }
    }
}
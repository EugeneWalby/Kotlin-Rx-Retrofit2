package com.chisw.taskbyandrew.ui.activity.task3

import android.util.Log
import android.widget.Toast
import com.chisw.taskbyandrew.R
import com.chisw.taskbyandrew.ui.activity.base.BaseBangActivity
import io.reactivex.Maybe
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_bang_without_exception.*

class BangWithExceptionActivity : BaseBangActivity() {
    override fun provideLayout(): Int {
        return R.layout.activity_bang_with_exception
    }

    override fun createButtonClickListener() {
        btnEmitValue.setOnClickListener {
            disposable = Maybe.just("")
                    .map {
                        createSourceOrThrowException(getRandomValue())
                    }
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            {
                                it.map { Toast.makeText(this, it, Toast.LENGTH_SHORT).show() }.subscribe()
                            },
                            {
                                Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                            })
        }
    }

    private fun createSourceOrThrowException(action: Boolean): Maybe<String> {
        if (action) {
            throw IllegalArgumentException(getString(R.string.msg_illegal_argument_exception))
        }
        return Maybe.just(getString(R.string.bang))
    }
}
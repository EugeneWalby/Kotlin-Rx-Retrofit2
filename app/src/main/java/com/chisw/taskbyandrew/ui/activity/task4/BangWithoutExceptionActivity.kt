package com.chisw.taskbyandrew.ui.activity.task4

import android.widget.Toast
import com.chisw.taskbyandrew.R
import com.chisw.taskbyandrew.ui.activity.base.BaseBangActivity
import io.reactivex.Maybe
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_bang_without_exception.*

class BangWithoutExceptionActivity : BaseBangActivity() {
    override fun provideLayout(): Int {
        return R.layout.activity_bang_without_exception
    }

    override fun createButtonClickListener() {
        btnEmitValue.setOnClickListener {
            disposable = createSourceOrThrowException(getRandomValue())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            {
                                Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
                            })
        }
    }

    private fun createSourceOrThrowException(action: Boolean): Maybe<String> {
        if (action) {
            return Maybe.empty()
        }
        return Maybe.just(getString(R.string.bang))
    }
}
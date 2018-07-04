package com.chisw.taskbyandrew.ui.activity.task5

import com.chisw.taskbyandrew.R
import com.chisw.taskbyandrew.ui.activity.base.BaseBangActivity
import io.reactivex.Maybe
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_bang_without_exception.*

class MaybeToSingleActivity : BaseBangActivity() {
    override fun provideLayout(): Int {
        return R.layout.activity_maybe_to_single
    }

    override fun createButtonClickListener() {
        btnEmitValue.setOnClickListener {
            val disposable = createSourceOrEmpty(getRandomValue())
                    .toSingle(getString(R.string.msd_defaul_single))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({ t -> showToast(t) })
            addDisposable(disposable)
        }
    }

    private fun createSourceOrEmpty(action: Boolean): Maybe<String> {
        return Maybe.create { e ->
            if (action) {
                e.onSuccess(getString(R.string.bang))
            } else {
                e.onComplete()
            }
        }
    }
}
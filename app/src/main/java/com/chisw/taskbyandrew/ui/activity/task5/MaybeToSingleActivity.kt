package com.chisw.taskbyandrew.ui.activity.task5

import com.chisw.taskbyandrew.R
import com.chisw.taskbyandrew.ui.activity.base.BaseBangActivity
import io.reactivex.Maybe
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_bang_without_exception.*

// done but why empty interrupts the chain ?
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
        if (action) {
            return Maybe.just(getString(R.string.bang))
        }
        return Maybe.empty()
    }
}
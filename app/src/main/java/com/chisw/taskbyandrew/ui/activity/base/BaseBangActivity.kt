package com.chisw.taskbyandrew.ui.activity.base

import android.os.Bundle
import io.reactivex.disposables.Disposable
import java.util.*

abstract class BaseBangActivity : BaseActivity() {
    protected var disposable: Disposable? = null

    abstract fun createButtonClickListener()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        createButtonClickListener()
    }

    override fun onPause() {
        super.onPause()
        disposable?.dispose()
    }

    protected fun getRandomValue(): Boolean {
        return Random().nextBoolean()
    }
}
package com.chisw.taskbyandrew.ui.activity.base

import android.os.Bundle
import io.reactivex.disposables.Disposable
import java.util.*

abstract class BaseBangActivity : BaseActivity() {
    abstract fun createButtonClickListener()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        createButtonClickListener()
    }

    protected fun getRandomValue(): Boolean {
        return Random().nextBoolean()
    }
}
package com.chisw.taskbyandrew.ui.activity.base

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class BaseActivity : AppCompatActivity() {
    abstract fun provideLayout(): Int
    protected var compositeDisposable: CompositeDisposable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(provideLayout())
        compositeDisposable = CompositeDisposable()
    }

    override fun onPause() {
        super.onPause()
        compositeDisposable?.dispose()
    }

    protected fun addDisposable(disposable: Disposable) {
        compositeDisposable?.add(disposable)
    }

    protected fun moveToScreen(screenToMove: AppCompatActivity) {
        val intent = Intent(this, screenToMove::class.java)
        startActivity(intent)
    }
}
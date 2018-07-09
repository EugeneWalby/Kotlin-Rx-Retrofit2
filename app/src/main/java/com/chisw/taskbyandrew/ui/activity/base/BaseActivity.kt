package com.chisw.taskbyandrew.ui.activity.base

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class BaseActivity : AppCompatActivity() {
    private var compositeDisposable: CompositeDisposable? = null

    abstract fun provideLayout(): Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(provideLayout())
        compositeDisposable = CompositeDisposable()
    }

    fun addDisposable(disposable: () -> Disposable) {
        compositeDisposable?.add(disposable())
    }

    override fun onPause() {
        super.onPause()
        compositeDisposable?.dispose()
    }

    protected fun moveToScreen(screenToMove: AppCompatActivity) {
        val intent = Intent(this, screenToMove::class.java)
        startActivity(intent)
    }

    protected fun showToast(message: String?) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
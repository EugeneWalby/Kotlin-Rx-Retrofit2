package com.chisw.taskbyandrew.ui.activity.base

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity

abstract class BaseActivity : AppCompatActivity() {
    abstract fun provideLayout(): Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(provideLayout())
    }

    protected fun moveToScreen(screenToMove: AppCompatActivity) {
        val intent = Intent(this, screenToMove::class.java)
        startActivity(intent)
    }
}
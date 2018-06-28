package com.chisw.taskbyandrew.ui.activity.base

import android.content.Intent
import android.support.v7.app.AppCompatActivity

abstract class BaseActivity : AppCompatActivity() {
    protected fun moveToScreen(screenToMove: AppCompatActivity) {
        val intent = Intent(this, screenToMove::class.java)
        startActivity(intent)
    }
}
package com.chisw.taskbyandrew.ui.activity.task10

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.chisw.taskbyandrew.R
import com.chisw.taskbyandrew.ui.activity.base.BaseActivity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject

// +
class SubjectActivity : BaseActivity() {
    override fun provideLayout(): Int {
        return R.layout.activity_subject
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        createSubject()
    }

    private fun createSubject() {
        val list = mutableListOf<Int>()
        val behaviorSubject: BehaviorSubject<Int> = BehaviorSubject.create()
        behaviorSubject.onNext(1)
        behaviorSubject.onNext(2)
        behaviorSubject.onNext(3)


        val disposable1 = behaviorSubject
                .observeOn(Schedulers.io())
                .subscribe(
                        {
                            Log.d("---", it.toString())
                            list.add(it)
                        }
                )
        addDisposable { disposable1 }

        behaviorSubject.onNext(4)
        behaviorSubject.onNext(5)
        behaviorSubject.onNext(6)

        val disposable2 = behaviorSubject.observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        {
                            for (items in list) {
                                showToast(items.toString())
                            }
                        }
                )
        addDisposable { disposable2 }
    }
}
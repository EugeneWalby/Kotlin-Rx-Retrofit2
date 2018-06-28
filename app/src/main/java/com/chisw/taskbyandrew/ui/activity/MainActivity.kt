package com.chisw.taskbyandrew.ui.activity

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.chisw.taskbyandrew.R
import com.chisw.taskbyandrew.ui.activity.base.BaseActivity
import com.chisw.taskbyandrew.ui.activity.task1.TitlesOfStoriesActivity
import com.chisw.taskbyandrew.ui.activity.task10.SubjectActivity
import com.chisw.taskbyandrew.ui.activity.task11.NewModelCombiningActivity
import com.chisw.taskbyandrew.ui.activity.task2.AuthorsWithKarmaActivity
import com.chisw.taskbyandrew.ui.activity.task3.BangWithExceptionActivity
import com.chisw.taskbyandrew.ui.activity.task4.BangWithoutExceptionActivity
import com.chisw.taskbyandrew.ui.activity.task5.MaybeToSingleActivity
import com.chisw.taskbyandrew.ui.activity.task6.SourceExtensionsWithCompositeActivity
import com.chisw.taskbyandrew.ui.activity.task7.EmittedValuesAccumulationActivity
import com.chisw.taskbyandrew.ui.activity.task8.ExecutorsActivity
import com.chisw.taskbyandrew.ui.activity.task9.EmittedValuesSubscribingActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity(), AdapterView.OnItemSelectedListener {
    private var selectedSpinnerItem: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        createListOfTasks()
        createOnBtnOKClickListener()
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        selectedSpinnerItem = position
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        selectedSpinnerItem = null
    }

    private fun createListOfTasks() {
        spTasks?.onItemSelectedListener = this
        val adapter: ArrayAdapter<CharSequence> = ArrayAdapter.createFromResource(
                this,
                R.array.titles_of_tasks,
                android.R.layout.simple_spinner_item)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spTasks?.adapter = adapter
    }

    private fun createOnBtnOKClickListener() {
        btnOK.setOnClickListener {
            when (selectedSpinnerItem) {
                0 -> {
                    moveToScreen(TitlesOfStoriesActivity())
                }
                1 -> {
                    moveToScreen(AuthorsWithKarmaActivity())
                }
                2 -> {
                    moveToScreen(BangWithExceptionActivity())
                }
                3 -> {
                    moveToScreen(BangWithoutExceptionActivity())
                }
                4 -> {
                    moveToScreen(MaybeToSingleActivity())
                }
                5 -> {
                    moveToScreen(SourceExtensionsWithCompositeActivity())
                }
                6 -> {
                    moveToScreen(EmittedValuesAccumulationActivity())
                }
                7 -> {
                    moveToScreen(ExecutorsActivity())
                }
                8 -> {
                    moveToScreen(EmittedValuesSubscribingActivity())
                }
                9 -> {
                    moveToScreen(SubjectActivity())
                }
                10 -> {
                    moveToScreen(NewModelCombiningActivity())
                }
                else -> {

                }
            }
        }
    }
}

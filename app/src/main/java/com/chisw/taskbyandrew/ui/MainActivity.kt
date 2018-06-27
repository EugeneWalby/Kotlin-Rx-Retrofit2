package com.chisw.taskbyandrew.ui

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.chisw.taskbyandrew.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        createListOfTasks()
    }

    private fun createListOfTasks() {
        spTasks?.onItemSelectedListener = OnSpinnerItemSelectedListener()
        val adapter: ArrayAdapter<CharSequence> = ArrayAdapter.createFromResource(this, R.array.titles_of_tasks, android.R.layout.simple_spinner_item)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spTasks?.adapter = adapter
    }

    private class OnSpinnerItemSelectedListener: AdapterView.OnItemSelectedListener {
        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

        }

        override fun onNothingSelected(parent: AdapterView<*>?) {

        }
    }
}

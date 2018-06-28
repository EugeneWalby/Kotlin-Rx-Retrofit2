package com.chisw.taskbyandrew.ui

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.chisw.taskbyandrew.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {
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
                    val intent = Intent(this, TitlesOfStoriesActivity::class.java)
                    startActivity(intent)
                }
                1 -> {

                }
                2 -> {

                }
                3 -> {

                }
                4 -> {

                }
                5 -> {

                }
                6 -> {

                }
                7 -> {

                }
                8 -> {

                }
                9 -> {

                }
                10 -> {

                }
                else -> {

                }
            }
        }
    }
}

package com.chisw.taskbyandrew.ui.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.chisw.taskbyandrew.R
import kotlinx.android.synthetic.main.list_item_stories.view.*

class StoriesAdapter(private val storiesList: ArrayList<String>, private val context: Context)
    : RecyclerView.Adapter<StoriesAdapter.ViewHolder>() {
    override fun getItemCount(): Int {
        return storiesList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.list_item_stories, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tvStoryTitle.text = storiesList[position]
    }

    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val tvStoryTitle: TextView = view.tvStoryTitle
    }
}
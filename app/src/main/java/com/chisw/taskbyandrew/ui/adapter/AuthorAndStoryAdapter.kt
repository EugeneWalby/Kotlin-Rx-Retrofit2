package com.chisw.taskbyandrew.ui.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.chisw.taskbyandrew.R
import com.chisw.taskbyandrew.network.model.AuthorAndStory
import kotlinx.android.synthetic.main.list_item_author_and_story.view.*

class AuthorAndStoryAdapter(private val authorsList: List<AuthorAndStory>, private val context: Context)
    : RecyclerView.Adapter<AuthorAndStoryAdapter.ViewHolder>() {
    override fun getItemCount(): Int {
        return authorsList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.list_item_author_and_story, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tvAuthorUsername.text = authorsList[position].username
        holder.tvAuthorKarma.text = authorsList[position].karma.toString()
        holder.tvStoryTitle.text = authorsList[position].title
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvAuthorUsername: TextView = view.tvAuthorUsername
        val tvAuthorKarma: TextView = view.tvAuthorKarma
        val tvStoryTitle: TextView = view.tvStoryTitle
    }
}
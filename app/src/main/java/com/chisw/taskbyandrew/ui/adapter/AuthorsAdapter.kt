package com.chisw.taskbyandrew.ui.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.chisw.taskbyandrew.R
import com.chisw.taskbyandrew.network.model.Model
import kotlinx.android.synthetic.main.list_item_authors.view.*

class AuthorsAdapter(private val authorsList: List<Model.UserResponse>, private val context: Context)
    : RecyclerView.Adapter<AuthorsAdapter.ViewHolder>() {
    override fun getItemCount(): Int {
        return authorsList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.list_item_authors, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tvAuthorId.text = authorsList[position].id.toString()
        holder.tvAuthorUsername.text = authorsList[position].username
        holder.tvAuthorKarma.text = authorsList[position].karma.toString()
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvAuthorId: TextView = view.tvAuthorId
        val tvAuthorUsername: TextView = view.tvAuthorUsername
        val tvAuthorKarma: TextView = view.tvAuthorKarma
    }
}
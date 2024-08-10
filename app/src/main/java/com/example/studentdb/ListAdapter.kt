package com.example.studentdb

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ListAdapter(private var items: List<ListItem>) :
    RecyclerView.Adapter<ListAdapter.ListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item, parent, false)
        return ListViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val item = items[position]
        holder.titleTextView.text = item.title
        holder.subtitleTextView.text = item.subtitle

        // Load drawable resource by its name
        val context = holder.itemView.context
        val resourceId = context.resources.getIdentifier(item.imageUrl.split("/").last(), "drawable", context.packageName)
        if (resourceId != 0) {
            holder.thumbnailImageView.setImageResource(resourceId)
        } else {
            holder.thumbnailImageView.setImageResource(R.drawable.pg_1) // Optional placeholder
        }
    }



    override fun getItemCount(): Int = items.size

    fun updateList(newItems: List<ListItem>) {
        items = newItems
        notifyDataSetChanged()
    }

    fun filter(query: String) {
        val filteredItems = items.filter {
            it.title.contains(query, ignoreCase = true) || it.subtitle.contains(query, ignoreCase = true)
        }
        updateList(filteredItems)
    }

    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTextView: TextView = itemView.findViewById(R.id.titleTextView)
        val subtitleTextView: TextView = itemView.findViewById(R.id.subtitleTextView)
        val thumbnailImageView: ImageView = itemView.findViewById(R.id.thumbnailImageView)
    }
}

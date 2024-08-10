package com.example.studentdb

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView

class ImageAdapter(private val images: List<ImageItem>) :
    RecyclerView.Adapter<ImageAdapter.ImageViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.image_layout, parent, false)
        return ImageViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val item = images[position]

        val context = holder.itemView.context

        val resourceId = context.resources.getIdentifier(item.url.split("/").last(), "drawable", context.packageName)
        if (resourceId != 0) {
            holder.imageView.setImageResource(resourceId)
        } else {
            holder.imageView.setImageResource(R.drawable.pg_1) // Optional placeholder
        }
    }

//    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
//        val imageUrl = images[position]
//        // Load drawable resource by its name
//        val context = holder.itemView.context
//        val resourceId = context.resources.getIdentifier(imageUrl, "drawable", context.packageName)
//        if (resourceId != 0) {
//            holder.imageView.setImageResource(resourceId)
//        } else {
//            holder.imageView.setImageResource(R.drawable.pg_1) // Optional placeholder
//        }
//    }

//    override fun onBindViewHolder(holder: ListAdapter.ListViewHolder, position: Int) {
//        val item = images[position]
//
//        val context = holder.itemView.context
//        val resourceId = context.resources.getIdentifier(item.url.split("/").last(), "drawable", context.packageName)
//        if (resourceId != 0) {
//            holder.thumbnailImageView.setImageResource(resourceId)
//        } else {
//            holder.thumbnailImageView.setImageResource(R.drawable.pg_1) // Optional placeholder
//        }
//    }

    override fun getItemCount(): Int = images.size

    inner class ImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.image_view)
    }
}

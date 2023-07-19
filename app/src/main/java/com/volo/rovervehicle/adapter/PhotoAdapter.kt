package com.volo.rovervehicle.adapter


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.volo.rovervehicle.data.model.Photo
import com.volo.voloandroidtask.databinding.PhotoItemBinding

typealias OnItemClick = (photo: Photo) -> Unit

class PhotoAdapter( val onItemClick: OnItemClick) :
    PagingDataAdapter<Photo, PhotoAdapter.PhotoViewHolder>(ItemComparator) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        return PhotoViewHolder(PhotoItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        holder.bind(getItem(position))

    }

    object ItemComparator : DiffUtil.ItemCallback<Photo>() {
        override fun areItemsTheSame(oldItem: Photo, newItem: Photo): Boolean {
            // Check if the items have the same identifier (e.g., ID)
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Photo, newItem: Photo): Boolean {
            // Check if the contents of the items are the same
            return oldItem == newItem
        }
    }

    inner class PhotoViewHolder(private val binding: PhotoItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(photo: Photo?) {
            binding.photo = photo
            binding.root.setOnClickListener {
                photo?.let { it1 -> onItemClick.invoke(it1) }
            }
            binding.executePendingBindings()
        }
    }
}
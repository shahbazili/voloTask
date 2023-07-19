package com.volo.rovervehicle.adapter


import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.volo.rovervehicle.data.model.Photo


@BindingAdapter("loadImage")
fun loadImage(view: ImageView, url: String?) {
    if (!url.isNullOrEmpty()) {
        Glide.with(view.context).load(url).diskCacheStrategy(DiskCacheStrategy.AUTOMATIC).into(view)
    }
}
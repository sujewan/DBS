package com.sujewan.dbs.util

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions

@BindingAdapter("imageUrl")
fun setImageUrl(imageView: ImageView, url : String) {
    val glideLoadingOptions: RequestOptions = RequestOptions()
        .centerCrop()
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .priority(Priority.HIGH)

    if (!url.isNullOrEmpty()) {
        Glide.with(imageView.context)
                .load(url)
                .apply(glideLoadingOptions)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(imageView)
    }
}

@BindingAdapter("circularImageUrl")
fun setCircularImageUrl(imageView: ImageView, url : String) {
    val glideLoadingOptions: RequestOptions = RequestOptions()
        .circleCrop()
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .priority(Priority.HIGH)

    if (!url.isNullOrEmpty()) {
        Glide.with(imageView.context)
            .load(url)
            .apply(glideLoadingOptions)
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(imageView)
    }
}



package com.example.beije.utils

import android.widget.ImageView
import com.bumptech.glide.Glide

val <T> T.exhaustive: T
    get() = this

fun ImageView.loadImageFromUrl(imageUrl: String?) {
    imageUrl?.let {
        val httpsImageUrl = imageUrl.replace("http://", "https://")

        Glide.with(this.context)
            .load(httpsImageUrl)
            .into(this)
    }
}
